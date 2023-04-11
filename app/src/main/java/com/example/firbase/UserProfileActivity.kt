package com.example.firbase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.example.firbase.utils.GlideLoader
import com.example.shop.firestore.FireStoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageUri: String = ""
    private val mFireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        supportActionBar?.hide()

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            //Get the user dwtails from intent as a ParcelableExtra
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        full_namee.setText(mUserDetails.fullName)
        birthOfDatee.setText(mUserDetails.birthOfDate)
        til_mobile_number.setText(mUserDetails.mobilePhone)

        et_email.isEnabled = false
        et_email.setText(mUserDetails.email)


        if (mUserDetails.profileCompleted == 0) {
            txtTitle.text = "COMPLETE PROFILE"
            full_namee.isEnabled = false
            birthOfDatee.isEnabled = false
            til_mobile_number.isEnabled = false

        } else {
            setUpActionBar()
            txtTitle.text = "EDIT PROFILE"
            GlideLoader(this@UserProfileActivity).loadUserPicture(mUserDetails.image, iv_user_photo)
            if (mUserDetails.address != "") {
                til_Address.setText(mUserDetails.address)
            }
            if (mUserDetails.gender == Constants.MALE) {
                rb_male.isChecked = true
            } else {
                rb_Female.isChecked = true
            }

            if (mUserDetails.userType == Constants.USERTYPE) {
                rb_Sick.isChecked = true
            } else {
                rb_Doctor.isChecked = true
            }
        }
        full_namee.isEnabled = false
        full_namee.setText(mUserDetails.fullName)

        birthOfDatee.isEnabled = false
        birthOfDatee.setText(mUserDetails.birthOfDate)

        til_mobile_number.isEnabled = false
        til_mobile_number.setText(mUserDetails.mobilePhone)

        et_email.isEnabled = false
        et_email.setText(mUserDetails.email)



        iv_user_photo.setOnClickListener(this@UserProfileActivity)
        btn_save.setOnClickListener(this@UserProfileActivity)

    }

    private fun setUpActionBar() {

        setSupportActionBar(toolbar_user_profile_activity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_color_back_24dp)
        }
        toolbar_user_profile_activity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_user_photo -> {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_save -> {

                    if (validateUserProfileDetails()) {
                        showProgressDialog("Please wait...")

                        if (mSelectedImageFileUri != null) {
                            FireStoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)

                        } else {
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this)

            } else {
                showErrorSnackBar(
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    true
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    mSelectedImageFileUri = data.data!!
                    iv_user_photo.setImageURI(mSelectedImageFileUri)
                    GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!, iv_user_photo)

                } catch (e: IOException) {
                    e.printStackTrace()
                    showErrorSnackBar("Image selected failed!", true)
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            showErrorSnackBar("Image selection cancelled!", true)

        }
    }

    fun updateUserProfileDetails() {

        val userHashMap = HashMap<String, Any>()

        val fullName = full_namee.text.toString().trim { it <= ' ' }
        if (fullName != mUserDetails.fullName) {
            userHashMap[Constants.FULL_NAME] = fullName
        }

        val birthOfDate = birthOfDatee.text.toString().trim { it <= ' ' }
        if (birthOfDate != mUserDetails.birthOfDate) {
            userHashMap[Constants.BIRTHOFDATE] = birthOfDate
        }

        val mobileNumber = til_mobile_number.text.toString().trim { it <= ' ' }
        if (mobileNumber != mUserDetails.mobilePhone) {
            userHashMap[Constants.MOBILE] = mobileNumber
        }


        val address = til_Address.text.toString().trim { it <= ' ' }

        val gender = if (rb_male.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        val userType = if (rb_Sick.isChecked) {
            Constants.SICK
        } else {
            Constants.DOCTOR
        }

        if (mUserProfileImageUri.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageUri
        }

        if (address.isNotEmpty() && address != mUserDetails.address.toString()) {
            userHashMap[Constants.ADDRESS] = address
            //.toLong()
        }

        if (gender.isNotEmpty() && gender != mUserDetails.gender) {
            userHashMap[Constants.GENDER] = gender
        }
        userHashMap[Constants.GENDER] = gender


        if (userType.isNotEmpty() && userType != mUserDetails.userType) {
            userHashMap[Constants.USERTYPE] = userType
        }
        userHashMap[Constants.USERTYPE] = userType


        userHashMap[Constants.PROFILE_COMPLETED] = 1

        FireStoreClass().updateUserProfileData(this, userHashMap)

    }


    fun getCurrentUserID(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun userProfileUpdateSuccess() {/////////////////////hereeeeeeeeeeee

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                val user = document.toObject(User::class.java)!!

                val sharedPreferences = this.getSharedPreferences(
                    Constants.MYSHOP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(Constants.LOGGED_IN_USERNAME2, user.fullName)
                editor.apply()

                if (user.userType == "Doctor") {
                    val i = Intent(this@UserProfileActivity, DashBoardAdminActivity::class.java)
                    i.putExtra(Constants.EXTRA_USER_DETAILS, user) // for adding data
                    startActivity(i)
                } else if (user.userType == "Sick") {
                    val i = Intent(this@UserProfileActivity, DashBoardUserActivity::class.java)
                    i.putExtra(Constants.EXTRA_USER_DETAILS2, user) // for adding data
                    startActivity(i)
                } else {
                    startActivity(Intent(this@UserProfileActivity, DashBoardUserActivity::class.java))
                    finish()
                }

                hideProgressDialog()
                Toast.makeText(
                    this,
                    "Your profile updated successfully.",
                    Toast.LENGTH_SHORT
                ).show()

            }

//        val user = User()
//        mUserDetails = user


//        startActivity(Intent(this@UserProfileActivity, DashBoardAdminActivity::class.java))
//        finish()
    }


    private fun validateUserProfileDetails(): Boolean {
        val moNumber = til_mobile_number.text.toString().trim { it <= ' ' }
        return when {
            moNumber.isEmpty() -> {
                showErrorSnackBar("Please enter mobile number.", true)
                false
            }
            else -> {
                true
            }
        }
    }


    fun imageUploadSuccess(imageURL: String) {
        mUserProfileImageUri = imageURL
        updateUserProfileDetails()

    }

}