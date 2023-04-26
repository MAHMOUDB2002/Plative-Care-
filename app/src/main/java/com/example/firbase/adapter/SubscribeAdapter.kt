package com.example.firbase.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.databinding.LayoutSubscribeViewBinding
import com.example.firbase.databinding.LayoutViewBinding
import com.example.firbase.model.Category
import com.example.firbase.utils.Constants
import com.example.firbase.view.ArticlesActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.io.Serializable

class SubscribeAdapter(var activity: Activity, var data: ArrayList<Category>) :
    RecyclerView.Adapter<SubscribeAdapter.MyViewHolder>(), Serializable {
    class MyViewHolder(var binding: LayoutSubscribeViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutSubscribeViewBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subsc = data[position]
        Picasso.get().load(data[position].img).into(holder.binding.imgCategory)
        holder.binding.tvName.setText(data[position].name)
        holder.binding.tvDescription.setText(data[position].description)
        holder.binding.tvDoctorName.setText(data[position].doctorName)

        holder.binding.cardView.setOnClickListener {
            val sharedP = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val edit = sharedP!!.edit()
            edit.putString("idCategory", data[position].id)
            edit.apply()
            val i = Intent(activity, ArticlesActivity::class.java)
            activity.startActivity(i)
        }

        holder.binding.btnUnSubscribe.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("الغاء الاشتراك")
            builder.setMessage("هل انت متأكد من انك تريد الغاء الاشتراك؟؟")

            builder.setPositiveButton("تأكيد") { dialog, which ->
                FirebaseFirestore.getInstance().collection(Constants.SUBSCRIBE).document(subsc.id)
                    .delete().addOnSuccessListener {
                        data.removeAt(position)
                        notifyDataSetChanged()
                    }
            }
            builder.setNegativeButton("الغاء") { dialog, which ->
                dialog.cancel()
            }
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }




}