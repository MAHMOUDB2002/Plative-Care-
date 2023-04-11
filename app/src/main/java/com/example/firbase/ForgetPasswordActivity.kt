package com.example.firbase

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password2.*

class ForgetPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password2)


        supportActionBar?.hide()

        btnsubmitF.setOnClickListener {

            val email :String = txtemailid.text.toString().trim{it <= ' '}

            if (email.isEmpty()){
                showErrorSnackBar("Please enter email address !!",true)
            }else{
                //لاسترجاع الباسورد
                showProgressDialog("Please wait...")
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)

                    .addOnSuccessListener {
                        hideProgressDialog()
                        //showErrorSnackBar("Email sent successfully to rest your password...",true)
                        Toast.makeText(this,"Email sent successfully to rest your password!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener {
                        hideProgressDialog()
                        showErrorSnackBar("{${it.message}}",true)
                    }

            }
        }
    }
}