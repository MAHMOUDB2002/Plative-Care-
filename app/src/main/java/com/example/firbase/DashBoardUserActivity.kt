package com.example.firbase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firbase.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dash_board_admin.*
import kotlinx.android.synthetic.main.activity_dash_board_user.*

class DashBoardUserActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_user)

        //firebaseAuth = FirebaseAuth.getInstance()
        //chechUser()

        val sharedPreferences =
            getSharedPreferences(Constants.MYSHOP_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME2, "")


//        btnlogoutuser.setOnClickListener {
//           firebaseAuth.signOut()
//            val i = Intent(this, signin::class.java)
//            startActivity(i)
//            finish()
//        }
    }

    private fun chechUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null ){
           subTitleTv.text = "Not Logged In "
        }else{
            val email = firebaseUser.email
            subTitleTv.text = email
        }
    }
}
