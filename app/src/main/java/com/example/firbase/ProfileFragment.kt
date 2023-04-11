package com.example.firbase

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.firbase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment()
//, TextWatcher
{
    private lateinit var mUserDetails: User
    private lateinit var db: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var ImageUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        db = FirebaseFirestore.getInstance()// initialize
        firebaseAuth = FirebaseAuth.getInstance()
        //fetchData()
        //getUserDetails()


//        root.save.setOnClickListener {
//            uploadImage()
//        }

//        root.dUpdate.setOnClickListener {
//            val i = Intent(activity, Update::class.java)
//            startActivity(i)
//        }

//        root.selectImage.setOnClickListener {
//            selectImage()
//
//        }

//        root.logoutt.setOnClickListener {
//            Toast.makeText(activity, "Loged Out", Toast.LENGTH_LONG).show()
//            //logOut()
//
//            firebaseAuth.addAuthStateListener {
//                if (firebaseAuth.currentUser == null) {
//                    activity!!.finish()
//                }
//            }
//        }// logout

        //getUserDetails()

        return root

    }

//    fun getCurrentUserID(): String {
//
////        val currentUser = FirebaseAuth.getInstance().currentUser
////        var currentUserID = ""
////        if (currentUser != null) {
////            currentUserID = currentUser.uid
////        }
////        return currentUserID
//
//        val uid = firebaseAuth.uid
//        var currentUserID2 = ""
//        if (uid != null) {
//            currentUserID2 = uid
//        }
//        return currentUserID2
//    }
//
//    fun getUserDetails() {
//
//        FirebaseFirestore.getInstance().collection("UserCareInfo")
//            .document(getCurrentUserID())
//            .get()
//            .addOnSuccessListener { document ->
//                val user = document.toObject(User::class.java)!!
//                //loadUserImage(user)
//
//            }
//    }

//    private fun fetchData() {
//        val progress = ProgressDialog(activity)
//        progress.setMessage("Uploading File ... ")
//        progress.setCancelable(false)
//        progress.show()
//
//        val uid = firebaseAuth.uid
//        database = FirebaseDatabase.getInstance().getReference("Users")
//
//        database.child(uid!!).get().addOnSuccessListener {
//
//            if (it.exists()) {
//                val name = it.child("name").value
//                val address = it.child("address").value
//                val birthOfDate = it.child("birthOfDate").value
//                val doctor = it.child("doctor").value
//                val email = it.child("email").value
//                val phoneNumber = it.child("phoneNumber").value
//                val userType = it.child("userType").value
//
//                pName.setText(name.toString())
//                pAddress.setText(address.toString())
//                pBirthOfDate.setText(birthOfDate.toString())
//                pEmail.setText(email.toString())
//                pPhoneNumber.setText(phoneNumber.toString())
//                pType.setText(userType.toString())
//
////                pType.setText(userType.toString() + "/" + doctor.toString())
//
//                if (progress.isShowing) progress.dismiss()
//
//            } else {
//                Toast.makeText(activity, "User Dosnt Exsist", Toast.LENGTH_SHORT).show()
//                if (progress.isShowing) progress.dismiss()
//            }
//
//        }.addOnFailureListener {
//            Toast.makeText(activity, "Failed to featch User", Toast.LENGTH_SHORT).show()
//            if (progress.isShowing) progress.dismiss()
//        }
//
//    }
//
//    private fun logOut() {
//        firebaseAuth.signOut()
//    }
//
//    private fun selectImage() {
//        val intent = Intent()
//        intent.type = "images/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//
//        startActivityForResult(intent, 100)
//    }
//
//    private fun uploadImage() {
//        val progress = ProgressDialog(activity)
//        progress.setMessage("Uploading File ... ")
//        progress.setCancelable(false)
//        progress.show()
//
//        val formaterr = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val fileName = formaterr.format(now)
//        val storageReferences = FirebaseStorage.getInstance().getReference("Images/$fileName")
//        storageReferences.putFile(ImageUri)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    image.setImageURI(ImageUri)
//                    storageReferences.downloadUrl.addOnSuccessListener { task ->
//                      //  uploadImageInfo(task.toString())
//                        Toast.makeText(activity, "Uploaded Image Successfully ", Toast.LENGTH_LONG).show()
//                        if (progress.isShowing) progress.dismiss()
//                    }
//                }else{
//                    if (progress.isShowing) progress.dismiss()
//                    Toast.makeText(activity, "Failed to Upload Image ", Toast.LENGTH_LONG).show()
//                }
//            }
//
////            .addOnSuccessListener {
////                image.setImageURI(ImageUri)
////                Toast.makeText(activity, "Uploaded Image Successfully ", Toast.LENGTH_LONG).show()
////                if (progress.isShowing) progress.dismiss()
////
////                //val uid = firebaseAuth.uid
////
////                //val hashMap = HashMap<String, Any>()
////                // hashMap["profileImage"] = ImageUri.toString()
////
//////                val ref = FirebaseDatabase.getInstance().getReference("Users")
//////                ref.child(uid!!).setValue(hashMap)
////
////                val userInfo = hashMapOf(
////                    "profileImage" to ImageUri.toString(),
////                )
//////                db.collection("UserCareInfo").document(getCurrentUserID()).set(userInfo)
//////                    //add(userInfo)
//////                    .addOnSuccessListener {
//////                        if (progress.isShowing) progress.dismiss()
//////                    }
////
////            }
////            .addOnFailureListener {
////                if (progress.isShowing) progress.dismiss()
////                Toast.makeText(activity, "Failed to Upload Image ", Toast.LENGTH_LONG).show()
////            }
//    }

//    private fun uploadImageInfo(imageUrl: String) {
//        val user = User(firebaseAuth.uid.toString(), imageUrl)
//        val ref = FirebaseDatabase.getInstance().getReference("Users")
//        ref.child(firebaseAuth.uid.toString()).setValue(user).addOnSuccessListener {
//            Toast.makeText(activity, "Image add successfully", Toast.LENGTH_SHORT).show()
//        }
//    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            image.setImageURI(ImageUri)

        }
    }


//    fun loadUserImage(user: User) {///user: com.example.shop.models.User
//        //val imageData = ArrayList<User>()
//
//        mUserDetails = user
//        try {
//            Glide.with(requireActivity())
//                .load(user.profileImage)
//                .centerCrop()
//                .placeholder(R.drawable.image)
//                .into(image)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }


    private fun AddData(firstName: String, lastName: String, bio: String, age: String) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Uploading File ... ")
        progress.setCancelable(false)
        progress.show()

        val userMap =
            hashMapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "bio" to bio,
                "age" to age
            )
        db.collection("user")// لو ما لقى collection  حيضيفوو
            //.add(userMap)// document
            .document().set(userMap)

            .addOnSuccessListener {
//                Toast.makeText(activity, "Added Successfully ", Toast.LENGTH_LONG).show()
//                firstname.text.toString()
//                lastname.text.toString()
//                agee.text.toString()
//                bioo.text.toString()
                if (progress.isShowing) progress.dismiss()

            }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed to Add ", Toast.LENGTH_LONG).show()
                if (progress.isShowing) progress.dismiss()

            }

    }

//    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//    }

//    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////        var fN = firstname.text.toString()
////        var lN = lastname.text.toString()
////        var A= agee.text.toString()
////        var B = bioo.text.toString()
//        var SI = selectImage.text.toString()
//
////        if (fN.trim().isNotEmpty() && lN.trim().isNotEmpty()&& A.trim().isNotEmpty() && B.trim().isNotEmpty() && SI.trim().isNotEmpty() ){
////            save.isEnabled = true
////        }else{
////            save.isEnabled = false
////
////        }
//    }

//    override fun afterTextChanged(p0: Editable?) {
//    }
}


// لاخفاء الزر واظهاره
//        root.firstname.addTextChangedListener (this)
//        root.lastname.addTextChangedListener ( this )
//        root.agee.addTextChangedListener ( this )
//        root.bioo.addTextChangedListener ( this )
// root.selectImage.addTextChangedListener(this)


//root.save.setOnClickListener {
//            if (firstname == null && lastname == null && agee == null && bioo == null) {
//                Toast.makeText(activity, "Please fill all these data !!", Toast.LENGTH_LONG).show()
//
//            } else {
//                AddData(
//                    firstname.text.toString(),
//                    lastname.text.toString(),
//                    agee.text.toString(),
//                    bioo.text.toString()
//                )
//        selectImage.visibility = View.INVISIBLE
// save.visibility = View.INVISIBLE
//
//            } else {
//               // btnupdate.visibility = View.VISIBLE
//                Toast.makeText(activity, "Please fill all these data !!", Toast.LENGTH_LONG).show()
//
//            }
//
//     }

//        if (save.isVisible){
//            save.visibility = View.INVISIBLE
//        }

//        root.selectImage.setOnClickListener {
//            val img = Image
//            if (img != null){
//                save.isEnabled = true
//            }else{
//                save.isEnabled = false
//
//            }
//        }



