package com.example.firbase.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.R
import com.example.firbase.databinding.LayoutSubscribeViewBinding
import com.example.firbase.databinding.LayoutViewNotificationBinding
import com.example.firbase.model.Category
import com.example.firbase.model.Subscriber
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.example.firbase.view.ArticlesActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.io.Serializable

class NotificationsAdapter(var activity: Activity, var data: ArrayList<Subscriber>) :
    RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>(), Serializable {
    class MyViewHolder(var binding: LayoutViewNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutViewNotificationBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subsc = data[position]
        Picasso.get().load(data[position].img).into(holder.binding.imgCategory)
//        holder.binding.tvUserName.setText("${data[position].fullName}تم الاشتراك بواسطة : ")
        holder.binding.tvDiseaseName.setText("بمرض : ${data[position].name}")
        holder.binding.tvUserName.setText("تم الأشتراك بواسطة : ${data[position].userName}")

    }

    override fun getItemCount(): Int {
        return data.size
    }




}