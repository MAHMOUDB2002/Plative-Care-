package com.example.firbase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_subsecribe.*
import kotlinx.android.synthetic.main.fragment_subsecribe.view.*


class SubsecribeFragment : Fragment() {

    private var clicked = false
    private lateinit var databaseReference : DatabaseReference

    private lateinit var fireebaseAuth: FirebaseAuth

    // arraylist to hold categories
//    private lateinit var categoryArrayList:ArrayList<UserCare>

    //private lateinit var userArrayList:ArrayList<ModelCategory>

    //adapter
    //private lateinit var adapterCategory: AdapterCategory

    private val rotateOpen: Animation by lazy  { AnimationUtils.loadAnimation(activity,R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy{ AnimationUtils.loadAnimation(activity,R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy{ AnimationUtils.loadAnimation(activity,R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy{ AnimationUtils.loadAnimation(activity,R.anim.to_bottom_anim)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  root = inflater.inflate(R.layout.fragment_subsecribe, container, false)

        root.btnaddd.setOnClickListener {
            onAddButtonClicked()
        }
        root.btnedit.setOnClickListener {
            Toast.makeText(activity,"Edit Button Clicked", Toast.LENGTH_SHORT).show()

        }
        root.btnaddcategory.setOnClickListener {
//            val i = Intent(activity, AddCategory::class.java)
         //   startActivity(i)

        }

        return root

    }

    private fun setAnimation(clicked:Boolean) {
        if (!clicked){
            btnedit.visibility = View.VISIBLE
            btnaddcategory.visibility = View.VISIBLE
        }else{
            btnedit.visibility = View.INVISIBLE
            btnaddcategory.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean){
        if (!clicked){
            btnedit.isClickable = true
            btnaddcategory.isClickable = true
        }else{
            btnedit.isClickable =false
            btnaddcategory.isClickable = false
        }
    }

    private fun setVisibility(clicked:Boolean) {
        if (!clicked) {
            btnedit.startAnimation(fromBottom)
            btnaddcategory.startAnimation(fromBottom)
            btnaddd.startAnimation(rotateOpen)
        } else {
            btnedit.startAnimation(toBottom)
            btnaddcategory.startAnimation(toBottom)
            btnaddd.startAnimation(rotateClose)
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked // ==         if (!clicked) clicked = true else clicked = false
    }


}