package com.example.firbase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
 var id : String = "",
 var fullName : String = "",
 val birthOfDate : String = "",
 val mobilePhone : String = "",
 var email : String = "",
 var image : String = "",
 val profileCompleted : Int = 0,
 val address : String = "",
 var doctorSpecialization : String = "",
 val gender : String = "",
 val userType : String = "",

 ) : Parcelable

// val userType : String = "",

//@Parcelize
//data class User(
   // val id : String  = "",
   // val fullName : String = "",
//    val lastName : String = "",
//    val email : String = "",
  //  val profileImage : String ="",
//    val mobile : Long = 0,
//    val gender : String = "",
//    val profileCompleted : Int = 0,
//    val newUser : Int = 0

//)
    //:Parcelable



//data class User(
// var uid: String?,
// var profileImage:String
//// var email:String,
//// var name:String,
//// var birthOfDate:String,
//// var phoneNumber:String,
//// var address:String,
////     //var doctor:String,
//// var userType:String,
//// var timeStamp:String,
//
// )