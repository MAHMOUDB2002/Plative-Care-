package com.example.firbase.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.databinding.LayoutViewAdminBinding
import com.example.firbase.view.ArticlesActivity
import com.example.firbase.view.DashBoardAdminActivity
import com.example.firbase.fragment_admin.EditCategoryFragment
import com.example.firbase.model.Category
import com.squareup.picasso.Picasso
import java.io.Serializable

class CategoryAdminAdapter(var activity: Activity, var data: ArrayList<Category>):
    RecyclerView.Adapter<CategoryAdminAdapter.MyViewHolder>(), Serializable {
    class MyViewHolder(var binding: LayoutViewAdminBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutViewAdminBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(data[position].img).into(holder.binding.imgCategory)
        holder.binding.tvName.setText(data[position].name)
        holder.binding.tvDescription.setText(data[position].description)
        holder.binding.tvDoctorName.setText(" دكتور : ${data[position].doctorName}")

        holder.binding.btnEdit.setOnClickListener {
            val sharedP=activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val edit=sharedP!!.edit()
            edit.putString("idCategory",data[position].id)
            edit.apply()
            (activity as DashBoardAdminActivity).makeCurrentFragment(EditCategoryFragment())
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
        return  data.size
    }


}