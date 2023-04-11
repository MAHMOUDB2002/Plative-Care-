package com.example.firbase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.firbase.model.User
import com.example.shop.firestore.FireStoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        btnlo.setOnClickListener {
            val l = Intent(this, LoginActivity::class.java)
            startActivity(l)
        }
        btnregdata.setOnClickListener {
            registerUser()
        }
    }

    private var fullName = ""
    private var birthOfDate = ""
    private var mobilePhone = ""
    private var email = ""
    private var pass = ""

    private fun registerUser() {

        email = txtemail.text.toString().trim { it <= ' ' }
        fullName = txtfullName.text.toString().trim { it <= ' ' }
        birthOfDate = txtBirthOfDate.text.toString().trim { it <= ' ' }
        mobilePhone = txtPhoneNumbers.text.toString().trim { it <= ' ' }
        pass = txtpassword.text.toString().trim { it <= ' ' }
        val confirmpass = txtconfirmpassword.text.toString().trim { it <= ' ' }

        if (fullName.isEmpty()) {
            showErrorSnackBar("Enter full name...", true)

        } else if (birthOfDate.isEmpty()) {
            showErrorSnackBar("Enter birth of date...", true)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorSnackBar("Invalid Email Pattern...", true)
        } else if (mobilePhone.isEmpty()) {
            showErrorSnackBar("Enter phone Number...", true)
        } else if (pass.isEmpty()) {
            showErrorSnackBar("Enter password...", true)

        } else if (confirmpass.isEmpty()) {
            showErrorSnackBar("Confirm password...", true)

        } else if (pass != confirmpass) {
            showErrorSnackBar("password does not match...", true)
        } else if (!checkBox.isChecked) {
            showErrorSnackBar("Please accept the privicy & policy...", true)
        } else {
            createUserAccount()
            //createAdminAccount()
        }

    }

    private fun createUserAccount() {
        val email: String = txtemail.text.toString().trim { it <= ' ' }
        val password: String = txtpassword.text.toString().trim { it <= ' ' }

        showProgressDialog("Please wait...")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)

            .addOnSuccessListener {
                val firebaseUser: FirebaseUser = it.user!!
                val user = User(
                    firebaseUser.uid,
                    txtfullName.text.toString().trim { it <= ' ' },
                    txtBirthOfDate.text.toString().trim { it <= ' ' },
                    txtPhoneNumbers.text.toString().trim { it <= ' ' },
                    txtemail.text.toString().trim { it <= ' ' }

                )
                FireStoreClass().registerUser(this@RegisterActivity, user)
            }.addOnFailureListener {
                hideProgressDialog()
                showErrorSnackBar(it.message.toString(), true)

            }
    }

    fun userRegisterationSuccess() {
        hideProgressDialog()
        showErrorSnackBar("You are registered successfully...", false)

    }
}