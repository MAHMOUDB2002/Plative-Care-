package com.example.firbase.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.firbase.R
import java.io.IOException

class GlideLoader (val context: Context) {

    fun loadUserPicture(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.userplaceh)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
























//
//fun loadCategoryPicture(image: Any, imageView: ImageView){
//    try {
//        Glide
//            .with(context)
//            .load(image)
//            .centerCrop()
//            .placeholder(R.drawable.userplaceh)
//            .into(imageView)
//    }catch (e :IOException){
//        e.printStackTrace()
//    }
//}