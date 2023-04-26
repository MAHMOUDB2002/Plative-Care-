package com.example.firbase.fragment_admin

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.example.firbase.view.LoginActivity
import com.example.firbase.R
import com.example.firbase.view.UserProfileActivity
import com.example.firbase.databinding.FragmentProfileBinding
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.example.firbase.utils.GlideLoader
import com.example.firbase.view.DashBoardAdminActivity
import com.example.firbase.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment()
//, TextWatcher
{
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserDetails: User
    private lateinit var mProgressDialog: Dialog
    private val mFireStore = FirebaseFirestore.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvEdit.setOnClickListener {
            val i = Intent(requireContext(), UserProfileActivity::class.java)
            i.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
            startActivity(i)

        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        getUserDetails()

//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                requireActivity().finish()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        super.onViewCreated(view, savedInstanceState)
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
        showProgressDialog("جاري التحميل...")
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                hideProgressDialog()
                if (document != null) {
                    val user = document.toObject(User::class.java)!!

                    val sharedPreferences = requireActivity().getSharedPreferences(
                        Constants.MYSHOP_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(Constants.LOGGED_IN_USERNAME, user.fullName)
                    editor.apply()
                    userDetailsSuccess(user)
                }
            }
            .addOnFailureListener {
                hideProgressDialog()
            }
    }

    fun userDetailsSuccess(user: User) {


        mUserDetails = user
        hideProgressDialog()
        GlideLoader(requireActivity()).loadUserPicture(
            user.image,
            iv_user_photoo
        )
        ////////////////////////////
        binding.address.text = user.address
        binding.birthOfDate.text = user.birthOfDate
        binding.tvEmail.text = user.email
        binding.tvName.text = user.fullName
        binding.tvGender.text = user.gender
        binding.tvMobileNumber.text = user.mobilePhone
        binding.txtUserType.text = user.userType

    }

    fun showProgressDialog(text: String) {

        mProgressDialog = Dialog(requireContext())
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
    }
}



