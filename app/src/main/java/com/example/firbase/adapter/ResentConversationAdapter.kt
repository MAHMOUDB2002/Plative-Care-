package com.example.firbase.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.adapter.ResentConversationAdapter.ConversionViewHolder
import com.example.firbase.databinding.ItemContanerRecentConvertionrBinding
import com.example.firbase.model.ChatMessage
import com.example.firbase.model.User
import com.example.firbase.listeners.ConversationListeners
import com.example.firbase.utils.GlideLoader


class ResentConversationAdapter(
    val context: Context,
    private val chatMessages: List<ChatMessage>,
    private val conversionListener: ConversationListeners
) : RecyclerView.Adapter<ConversionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        return ConversionViewHolder(
            ItemContanerRecentConvertionrBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        holder.setData(chatMessages[position])
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    inner class ConversionViewHolder(var binding: ItemContanerRecentConvertionrBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun setData(chatMessage: ChatMessage) {
//            binding.imageProfile.setImageBitmap(chatMessage.conversationImage?.let {
//                getConversionImage(
//                    it
//                )
//            })
            binding.textName.text = chatMessage.conversationName
            binding.textRecentMessage.text = chatMessage.message
            binding.textlastMsTime.text = chatMessage.dateTime?.substring(5) ?: "2:04 pm"
            binding.root.setOnClickListener {
                val user = User()
                user.id = chatMessage.conversationId.toString()
                user.fullName = chatMessage.conversationName.toString()
                user.image = chatMessage.conversationImage!!
                conversionListener.onConversionClicked(user)
                GlideLoader(context).loadUserPicture(
                    user.image,
                    binding!!.imageProfile
                )
            }
        }

        private fun getConversionImage(encodedImage: String): Bitmap {
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }
}