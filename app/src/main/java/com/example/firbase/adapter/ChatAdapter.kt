package com.example.firbase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.databinding.ItemContainerRecevedMessageBinding
import com.example.firbase.databinding.ItemContanerSendMessageBinding
import com.example.firbase.model.ChatMessage
import com.example.firbase.utils.GlideLoader


class ChatAdapter(
    val context: Context,
    private val chatMessages: List<ChatMessage>,
    //private val receiverProfileImage: Bitmap,
//    private val receiverProfileImage: Any,

    private val senderId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            SentMessageViewHolder(
                ItemContanerSendMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ReceivedMessageViewHolder(
                ItemContainerRecevedMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            (holder as SentMessageViewHolder).setData(chatMessages[position])
        } else {

            (holder as ReceivedMessageViewHolder).setData(
                chatMessages[position],
                //receiverProfileImage
            )
        }
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position].senderId == senderId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    internal class SentMessageViewHolder(private val binding: ItemContanerSendMessageBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun setData(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.dateTime
        }
    }

    inner class ReceivedMessageViewHolder(private val binding: ItemContainerRecevedMessageBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun setData(
            chatMessage: ChatMessage
            //, receiverProfileImage: String?
            //,user: User
        ) {
            binding.textMessage.text = chatMessage.message
            binding.textDateTime.text = chatMessage.dateTime
//            binding.imageProfile.setImageBitmap(receiverProfileImage)
            //  Picasso.get().load(chatMessages[position].img).into(binding.imageProfile)

//            GlideLoader(context).loadUserPicture(
//                chatMessage.conversationImage.toString(),
//                binding.imageProfile
//            )


        }
    }

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }
}