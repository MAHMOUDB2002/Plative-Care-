package com.example.firbase.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firbase.R
import com.example.firbase.view.ArticlesActivity
import com.example.firbase.view.DashBoardAdminActivity
import com.example.firbase.databinding.LayoutViewBinding
import com.example.firbase.databinding.LayoutViewUserBinding
import com.example.firbase.fragment_admin.EditCategoryFragment
import com.example.firbase.model.Category
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.io.Serializable

class CategoryUserAdapter(var activity: Activity, var data: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryUserAdapter.MyViewHolder>(), Serializable {
    private val mFireStore = FirebaseFirestore.getInstance()
    var flag = 0
    lateinit var data2: ArrayList<User>

    class MyViewHolder(var binding: LayoutViewUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutViewUserBinding.inflate(activity.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("MutatingSharedPrefs", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(data[position].img).into(holder.binding.imgCategory)
        holder.binding.tvName.setText(data[position].name)
        holder.binding.tvDescription.setText(data[position].description)
        holder.binding.doctorName.setText(data[position].doctorName)

        holder.binding.cardView.setOnClickListener {
            val i = Intent(activity, ArticlesActivity::class.java)
            activity.startActivity(i)
        }


        // إضافة مستمع النقر لزر المفضلة
        if (flag == 0){
            holder.binding.btnSubscribe.setOnClickListener {
                flag = 1
                val subsc = data[position]
//            var subsc2 = data2[position]

                val catHashMap = HashMap<String, Any>()
                val img = subsc.imgName
                val img2 = subsc.img
                val name = subsc.name
                val desc = subsc.description
                val docName = subsc.doctorName

                //val userName = subsc2.fullName
                val isSubscribe = true.also { subsc.isSubscribe = it }

                if (name.isNotEmpty()) {
                    catHashMap["name"] = name
                }
                if (desc.isNotEmpty()) {
                    catHashMap["description"] = desc
                }
                if (img.isNotEmpty()) {
                    catHashMap["imgName"] = img
                }
                if (img2.isNotEmpty()) {
                    catHashMap["img"] = img2
                }
                if (docName!!.isNotEmpty()) {
                    catHashMap["doctorName"] = docName
                }
//            if (userName.isNotEmpty()) {
//                catHashMap["userName"] = userName
//            }
                if (isSubscribe) {
                    catHashMap["isSubscribe"] = isSubscribe
                }


                mFireStore.collection(Constants.SUBSCRIBE)
                    .document(subsc.id)
                    .set(catHashMap)
                    .addOnSuccessListener {
                        if (flag >= 1 && isSubscribe) {
                            holder.binding.btnSubscribe.visibility = View.INVISIBLE
                            holder.binding.btnUnSubscribe.visibility = View.VISIBLE
//                            holder.binding.btnSubscribe.setImageResource(R.drawable.subscribe2)
                            Toast.makeText(activity, "تم الأشتراك بنجاح....", Toast.LENGTH_SHORT).show()
                            val sharedP = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                            val edit = sharedP!!.edit()
                            edit.putString("idSubscribeCategory", data[position].id)
                            edit.apply()
                        }
                    }
                    .addOnFailureListener {
                        if (flag < 1 && !isSubscribe) {
                            holder.binding.btnSubscribe.setImageResource(R.drawable.subscribe1)
                        }

                    }
            }
        }else {
            val subsc = data[position]
                FirebaseFirestore.getInstance().collection(Constants.SUBSCRIBE).document(subsc.id)
                    .delete().addOnSuccessListener {
                        data.removeAt(position)
                        notifyDataSetChanged()
                        holder.binding.btnSubscribe.visibility = View.VISIBLE
                        holder.binding.btnUnSubscribe.visibility = View.INVISIBLE
                        Toast.makeText(activity, "تم الغاء الاشتراك", Toast.LENGTH_SHORT).show()
                    }
            }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    var onSubscribeClickListener: AdapterView.OnItemClickListener? = null

    interface onItemClickListener {
        fun onItemClicked(position: Int, model: Category)
    }


}