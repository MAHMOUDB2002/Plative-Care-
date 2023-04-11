package com.example.firbase

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.example.firbase.utils.GlideLoader
import com.example.shop.firestore.FireStoreClass
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {
    private lateinit var mUserDetails: User
    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()
        setUpActionBar()

        tv_edit.setOnClickListener {
            val i = Intent(this@SettingsActivity, UserProfileActivity::class.java)
            i.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
            startActivity(i)

        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
            finish()
        }
        getUserDetail()
    }

    private fun setUpActionBar() {

        setSupportActionBar(toolbar_settings_activity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_color_back_24dp)
        }
        toolbar_settings_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getUserDetail() {
        showProgressDialog("Please wait...")
        FireStoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User) {

        mUserDetails = user
        hideProgressDialog()
        GlideLoader(this@SettingsActivity).loadUserPicture(
            user.image,
            iv_user_photoo
        )
        ////////////////////////////
        address.text = user.address
        birthOfDate.text = user.birthOfDate
        tv_email.text = user.email
        tv_name.text = user.fullName
        tv_gender.text = user.gender
        tv_mobile_number.text = user.mobilePhone
        txtUserType.text = user.userType

    }


}