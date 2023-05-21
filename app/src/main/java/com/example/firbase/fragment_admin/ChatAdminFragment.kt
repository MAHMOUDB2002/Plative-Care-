package com.example.firbase.fragment_admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firbase.view.ChatActivity
import com.example.firbase.view.UsersActivity
import com.example.firbase.adapter.ResentConversationAdapter
import com.example.firbase.databinding.FragmentChatAdminBinding
import com.example.firbase.listeners.ConversationListeners
import com.example.firbase.model.ChatMessage
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.example.firbase.utils.GlideLoader
import com.example.firbase.utils.PreferanceManeger
import com.example.firbase.view.DashBoardAdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ChatAdminFragment : Fragment(), ConversationListeners {

    private var _binding: FragmentChatAdminBinding? = null
    private val binding get() = _binding!!
    private var preferanceManeger: PreferanceManeger? = null
    private var conversations: MutableList<ChatMessage>? = null
    private var conversationsAdapter: ResentConversationAdapter? = null
    private var database: FirebaseFirestore? = null
    private lateinit var mUserDetails: User
    private val mFireStore = FirebaseFirestore.getInstance()
    lateinit var d: Activity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferanceManeger = PreferanceManeger(requireContext())
        init()
        //loadUserDetails()
        //token
        getUserDetails()
        setListeners()
        listenConversations()
        d = (activity as DashBoardAdminActivity)
        binding.imageProfile.setOnClickListener {
            (d as DashBoardAdminActivity).makeCurrentFragment(ProfileAdminFragment())
        }
    }


    private fun init() {
        conversations = ArrayList()
        conversationsAdapter =
            ResentConversationAdapter(
                requireContext(),
                conversations as ArrayList<ChatMessage>,
                this
            )
        binding!!.conversationsRecyclerView.adapter = conversationsAdapter
        database = FirebaseFirestore.getInstance()
    }

    private fun setListeners() {
//        binding!!.imageSignOut.setOnClickListener { v: View? -> signOut() }
        binding!!.fabNewChat.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    requireContext(), UsersActivity::class.java
                )
            )
        }
    }

    private fun listenConversations() {
        database!!.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .whereEqualTo(
                Constants.KEY_SENDER_ID,
                preferanceManeger!!.getString(Constants.KEY_USER_ID)
            ).addSnapshotListener(eventListener)
        database!!.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .whereEqualTo(
                Constants.KEY_RECEIVER_ID,
                preferanceManeger!!.getString(Constants.KEY_USER_ID)
            ).addSnapshotListener(eventListener)
    }

    fun getCurrentUserID(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    private fun getUserDetails() {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)!!
                    userDetailsSuccess(user)
                }
            }
    }

    fun userDetailsSuccess(user: User) {
        mUserDetails = user
        GlideLoader(requireActivity()).loadUserPicture(
            user.image,
            binding.imageProfile
        )

    }

//    private fun updateToken(token: String) {
//        val database = FirebaseFirestore.getInstance()
//        val documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
//            preferanceManeger!!.getString(Constants.KEY_USER_ID)!!
//        )
//        documentReference
//            .update(
//                Constants.KEY_FCM_TOKEN,
//                token
//            ) //                .addOnSuccessListener(unused -> showToast("Token updated successfully"))
//            .addOnFailureListener { e: Exception? -> showToast("Unable to update token") }
//    }

    private val eventListener =
        EventListener { value: QuerySnapshot?, error: FirebaseFirestoreException? ->
            if (error != null) {
                return@EventListener
            }
            if (value != null) {
                for (documentChange in value.documentChanges) {
                    if (documentChange.type == DocumentChange.Type.ADDED) {
                        val senderId = documentChange.document.getString(Constants.KEY_SENDER_ID)
                        val receiverId =
                            documentChange.document.getString(Constants.KEY_RECEIVER_ID)
                        val chatMessage = ChatMessage()
                        chatMessage.senderId = senderId
                        chatMessage.receiverId = receiverId
                        if (preferanceManeger!!.getString(Constants.KEY_USER_ID) == senderId) {
                            chatMessage.conversationImage =
                                documentChange.document.getString(Constants.KEY_RECEIVER_IMAGE)
                            chatMessage.conversationName =
                                documentChange.document.getString(Constants.KEY_RECEIVER_NAME)
                            chatMessage.conversationId =
                                documentChange.document.getString(Constants.KEY_RECEIVER_ID)
                        } else {
                            chatMessage.conversationImage =
                                documentChange.document.getString(Constants.KEY_SENDER_IMAGE)
                            chatMessage.conversationName =
                                documentChange.document.getString(Constants.KEY_SENDER_NAME)
                            chatMessage.conversationId =
                                documentChange.document.getString(Constants.KEY_SENDER_ID)
                        }
                        chatMessage.message =
                            documentChange.document.getString(Constants.KEY_LAST_MESSAGE)
                        chatMessage.dateObject =
                            documentChange.document.getDate(Constants.KEY_TIMESTAMP)
                        conversations!!.add(chatMessage)
                    } else if (documentChange.type == DocumentChange.Type.MODIFIED) {
                        var i = 0
                        while (i < conversations!!.size) {
                            val senderId =
                                documentChange.document.getString(Constants.KEY_SENDER_ID)
                            val receiverId =
                                documentChange.document.getString(Constants.KEY_RECEIVER_ID)
                            if (conversations!![i].senderId == senderId && conversations!![i].receiverId == receiverId) {
                                conversations!![i].message =
                                    documentChange.document.getString(Constants.KEY_LAST_MESSAGE)
                                conversations!![i].dateObject =
                                    documentChange.document.getDate(Constants.KEY_TIMESTAMP)
                                break
                            }
                            i++
                        }
                    }
                }
                conversations!!.sortWith(Comparator { obj1: ChatMessage, obj2: ChatMessage ->
                    obj2.dateObject!!.compareTo(
                        obj1.dateObject
                    )
                })
                conversationsAdapter!!.notifyDataSetChanged()
                binding!!.conversationsRecyclerView.smoothScrollToPosition(0)
                binding!!.conversationsRecyclerView.visibility = View.VISIBLE
                binding!!.progressBar.visibility = View.GONE
            }
        }
//    private val token: Unit
//        private get() {
//            FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
//                updateToken(
//                    token
//                )
//            }
//        }

//    private fun loadUserDetails() {
//        binding!!.textName.text =
//            preferanceManeger?.getString(Constants.KEY_NAME)
//        val bytes =
//            Base64.decode(
//                preferanceManeger?.getString(Constants.KEY_IMAGE) ?: "no ",
//                Base64.DEFAULT
//            )
//        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//        binding!!.imageProfile.setImageBitmap(bitmap)
//    }

    override fun onConversionClicked(user: User?) {
        val intent = Intent(requireContext(), ChatActivity::class.java)
        intent.putExtra(Constants.KEY_USER, user)
        startActivity(intent)
    }


}