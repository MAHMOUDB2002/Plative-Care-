package com.example.firbase.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.databinding.LayoutSearchViewBinding
import com.example.firbase.model.Category
import com.example.firbase.utils.Constants
import com.example.firbase.view.ArticlesActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class SearchAdapter(var activity: Activity, var data: ArrayList<Category>) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    private var initialData = data

    class MyViewHolder(var binding: LayoutSearchViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.MyViewHolder {
        val binding =
            LayoutSearchViewBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
        val db = FirebaseFirestore.getInstance()
        db.collection(Constants.CATEGORY).document(data[position].name)
            .get()
            .addOnSuccessListener {
                Picasso.get().load(data[position].img).into(holder.binding.imgCategory)
//                holder.binding.tvName.setText(it.getString("name"))
                holder.binding.tvName.setText(data[position].name)
                holder.binding.tvDescription.setText(data[position].description)
                holder.binding.tvDoctorName.setText(" دكتور : ${data[position].doctorName}")
            }
        holder.binding.cardView.setOnClickListener {
            val sharedP=activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val edit=sharedP!!.edit()
            edit.putString("idCategory",data[position].id)
            edit.apply()
            val i= Intent(activity, ArticlesActivity::class.java)
            activity.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun search(text : String){
        val newArray = initialData.filter { Task ->
            Task.name!!.contains(text) || Task.name!!.startsWith(text)
        }
        data = newArray as ArrayList<Category>
        notifyDataSetChanged()
    }
}