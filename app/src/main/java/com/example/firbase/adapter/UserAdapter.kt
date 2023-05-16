package com.example.firbase.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.adapter.UserAdapter.UserViewHolder
import com.example.firbase.databinding.ItemContanerUserBinding
import com.example.firbase.listeners.UserListeners
import com.example.firbase.model.User
import com.example.firbase.utils.GlideLoader

class UserAdapter(
    val context: Context,
    private val users: List<User>,
    private val userLesteners: UserListeners
) :
    RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemContainerUserBinding =
            ItemContanerUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemContainerUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setUserData(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(var binding: ItemContanerUserBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun setUserData(user: User) {
            binding.textName.text = user.fullName
            binding.textEmail.text = user.email
//
//            binding.imageProfile.setImageBitmap(user.image?.let {
//                getUserImage(it) })

            GlideLoader(context).loadUserPicture(
                user.image,
                binding!!.imageProfile
            )
            binding.root.setOnClickListener { v: View? -> userLesteners.onUserClicked(user) }
        }

        private fun getUserImage(encodedImage: String): Bitmap {
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }


}