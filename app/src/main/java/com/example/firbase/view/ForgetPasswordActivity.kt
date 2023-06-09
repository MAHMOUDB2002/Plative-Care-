package com.example.firbase.view

import android.os.Bundle
import android.widget.Toast
import com.example.firbase.R
import com.example.firbase.utils.BaseActivity
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
                showErrorSnackBar("الرجاء إدخال عنوان البريد الإلكتروني !!",true)
            }else{
                //لاسترجاع الباسورد
                showProgressDialog("جاري التحميل...")
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)

                    .addOnSuccessListener {
                        hideProgressDialog()
                        //showErrorSnackBar("Email sent successfully to rest your password...",true)
                        Toast.makeText(this,"تم إرسال البريد الإلكتروني بنجاح لراحة كلمة المرور الخاصة بك!", Toast.LENGTH_LONG).show()
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