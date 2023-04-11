package com.example.firbase.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {

    const val USERS: String = "User_Care_App"


    const val MYSHOP_PREFERENCES: String = "MyShopPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val LOGGED_IN_USERNAME2: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val EXTRA_USER_DETAILS2: String = "extra_user_details2"


    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1


    const val MALE: String = "Male"
    const val FEMALE: String = "Female"


    const val SICK: String = "Sick"
    const val DOCTOR: String = "Doctor"


    const val FULL_NAME: String = "fullName"
    const val BIRTHOFDATE: String = "birthOfDate"
    const val ADDRESS: String = "address"


    const val MOBILE: String = "mobilePhone"
    const val GENDER: String = "gender"
    const val IMAGE: String = "image"
    const val USERTYPE: String = "userType"


    const val PROFILE_COMPLETED: String = "profileCompleted"
    const val USER_PROFILE_IMAGE = "User_Profile_Image_Care"


    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }


    fun getFileExtension(activity: Activity,uri: Uri?) : String? {
        //c:/user/download/homer.jpg
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))

    }
}