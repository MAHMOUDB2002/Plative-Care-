package com.example.shop.firestore

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.firbase.view.LoginActivity
import com.example.firbase.view.RegisterActivity
import com.example.firbase.view.UserProfileActivity
import com.example.firbase.model.User
import com.example.firbase.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.txtfullName

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()
    private lateinit var mProgressDialog: Dialog

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisterationSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
                Toast.makeText(
                    activity.applicationContext,
                    "Error while registering the user !!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun getCurrentUserID(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity, readCategoryList: Boolean = false) {

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                if (document != null) {
                    Log.i(activity.javaClass.simpleName, document.toString())
                    val user = document.toObject(User::class.java)!!

                    val sharedPreferences = activity.getSharedPreferences(
                        Constants.MYSHOP_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(Constants.LOGGED_IN_USERNAME, user.fullName)
                    editor.apply()

                    when (activity) {

                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                    }
                }

            }
            .addOnFailureListener {

                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                }
            }

    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.userProfileUpdateSuccess()//  هادي الدالة اللي عملتها بصفحة البروفايل
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, "Error while updating the user details.", e)
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileUri: Uri?) {

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtension(
                activity,
                imageFileUri
            )
        )
//        val sharedPreferences =
//            activity.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
//
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//        editor.putString(
//            Constants.KEY_IMAGE,
//            imageFileUri!!.toString()
//        )
//        editor.apply()

        sRef.putFile(imageFileUri!!)

            .addOnSuccessListener { taskSnapshot ->
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )
                taskSnapshot.metadata!!.reference!!.downloadUrl

                    .addOnSuccessListener { uri ->
                        Log.e("Download Image URL", uri.toString())
                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        when (activity) {
                            is UserProfileActivity -> {
                                activity.hideProgressDialog()
                            }
                        }
                        Log.e(activity.javaClass.simpleName, e.message, e)
                    }
            }
    }

}
