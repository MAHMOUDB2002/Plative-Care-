package com.example.firbase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.firbase.R.layout.activity_main
import com.example.firbase.databinding.ActivityMainBinding
import com.example.firbase.model.TabPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signin.*

class MainActivity : AppCompatActivity() {
    //lateinit var db : FirebaseFirestore// initialize
    private lateinit var firebaseAuth: FirebaseAuth
   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // db = Firebase.firestore// initialize





        setUpTabBar()

//        binding.btnpro.setOnClickListener {
//            val i = Intent(this,profileinfo::class.java)
//          startActivity(i)
//        }
//        binding.btnupdate.setOnClickListener {
//            val i = Intent(this,Update::class.java)
//            startActivity(i)
//        }
//
//        btnlogout.setOnClickListener {
//            Toast.makeText(this, "Loged Out", Toast.LENGTH_LONG).show()
//            logOut()
//            firebaseAuth.addAuthStateListener {
//                if (firebaseAuth.currentUser == null) {
//                    this.finish()
//                } else {
//
//                }
//            }
//        }// logout


//        btnAdd.setOnClickListener {
//            addUserToDB(txtusername.text.toString()
//                ,txtemail.text.toString()
//                ,txtpassword.text.toString().toInt()
//                ,txtlevel.text.toString().toInt())
//            // القيم اللي بدي اجيبهم من الداتا بيز واعرضهم بالواجهة
//        }
////        btngetallusers.setOnClickListener {
////            getAllusers()
////        }
//        btngetalluserslimitorder.setOnClickListener {
//            getAllusersByLimit()
//        }
//        btngetalluserswhere.setOnClickListener {
//              getAllusersByWhere()
//        }
//        btndeletuser.setOnClickListener {
//            DeleteUserById("PNmRFJasxPjbUwERn8oK")
//        }
//        btndeletuserfield.setOnClickListener {
//            DeleteUserField("WsfcxS2FxHq1RuAxPCwW")
//        }
//
//        btnupdate.setOnClickListener {
//            val i = Intent(this,Update::class.java)
//            startActivity(i)
//        }
////        btnlog.setOnClickListener {
////            Toast.makeText(this,"Loging Out ...",Toast.LENGTH_LONG).show()
////            logOut()
////            firebaseAuth.addAuthStateListener {
////                if (firebaseAuth.currentUser==null){
////                    this.finish()
////                }else{
////
////                }
////            }
//        }
////        btnchooseimage.setOnClickListener {
////            val i = Intent(this,Image::class.java)
////            startActivity(i)
////        }
//}
//
//}
////

//
//    private fun addUserToDB(name: String,email:String,password :Int,level :Int){
//
//        val user = hashMapOf("name" to name,"email" to email,"password" to password,"level" to level)
//        db.collection("users")// لو ما لقى collection  حيضيفوو
//            .add(user)// document
//            .addOnSuccessListener {
//              Log.e(TAG,"Added Successfully with user id ${it.id}")
//            }
//            .addOnFailureListener{ exception->
//                Log.e(TAG, exception.message.toString())
//            }
//    }
//
//    private fun getAllusers(){
//       db.collection("users").get()
//           .addOnSuccessListener {
//               //querSnapshot مصفوفة بتحتوي على كل البيانات تبعتي
//          for (document in it){
//              Log.e(TAG,"${document.id} => ${document.data}")// لكل البيانات
//              Log.e(TAG,"${document.id} => ${document.getString("name")}") //  في هاي الحالة بيجيب الاسم بس
//          }
//           }
//           .addOnFailureListener {
//             Log.e(TAG,it.message.toString())
//           }
//    }
//
//   private fun getAllusersByLimit(){
//
////       db.collection("users").limit(2)// document حيرجع بس 2
////           .orderBy("name")// بيرتب بناء على الاسم
//       db.collection("users").limit(2)// document حيرجع بس 2
//           .orderBy("name",Query.Direction.DESCENDING)// بيرتب بناء على الاسم
//           .get()
//
//           .addOnSuccessListener {querSnapshot->
//               //querSnapshot مصفوفة بتحتوي على كل البيانات تبعتي
//               for (document in querSnapshot){
//                   Log.e(TAG,"${document.id} => ${document.data}")// لكل البيانات
//                   Log.e(TAG,"${document.id} => ${document.getString("name")}") //  في هاي الحالة بيجيب الاسم بس
//               }
//           }
//           .addOnFailureListener {
//               Log.e(TAG,it.message.toString())
//           }
//   }
//
//    private fun getAllusersByWhere(){
//        db.collection("users").whereGreaterThan("level",3)// اكبر من 3 وال 3 لا تحسب
//                //whereGreaterThanOrEqualTo 3 حتبين
//            .get()
//            .addOnSuccessListener {querSnapshot->
//                //querSnapshot مصفوفة بتحتوي على كل البيانات تبعتي
//                for (document in querSnapshot){
//                    Log.e(TAG,"${document.id} => ${document.data}")// لكل البيانات
//                    Toast.makeText(this,"${document.id} => ${document.data}",Toast.LENGTH_LONG).show()
//                }
//            }
//            .addOnFailureListener {
//                Log.e(TAG,it.message.toString())
//            }
//    }
//
//    private fun DeleteUserById(id:String){
//    db.collection("users").document(id)// document  معين بناء على id
//        .delete()
//        .addOnSuccessListener {
//            Toast.makeText(this,"User deleted successfully",Toast.LENGTH_SHORT).show()
//        }
//        .addOnFailureListener {
//            Toast.makeText(this,"Unable to delete",Toast.LENGTH_SHORT).show()
//
//        }
//    }
//
//    private fun DeleteUserField(id:String){// email  لحذف حقل
//        db.collection("users").document(id)
//            .update("level",FieldValue.delete())//FieldValue.delete() القيمة الموجودة عندي اللي هيا email احزفها
//
//            .addOnSuccessListener {
//                Toast.makeText(this,"Field deleted successfully",Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this,"Unable to Delete",Toast.LENGTH_SHORT).show()
//
//            }
//    }
//

//        btnSign.setOnClickListener {
//            val i = Intent(this,signup::class.java)
//            startActivity(i)
        //   }
        firebaseAuth=FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
//        Handler().postDelayed({
//            if (user != null){
//                val Main = Intent(this,MainActivity::class.java)
//                startActivity(Main)
//                finish()
//            }else{
//                val Signup = Intent(this,signup::class.java)
//                startActivity(Signup)
//                finish()
//            }
//        },2000)
    }





//

    private fun setUpTabBar(){

       val adapter = TabPageAdapter (this ,tabLayout.tabCount)
    viewPager.adapter = adapter
      viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
      {
          override fun onPageSelected(position: Int) {
             tabLayout.selectTab(tabLayout.getTabAt(position))
          }
      })
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem  = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })



    }

//    private fun logOut(){
//        firebaseAuth.signOut()
//    }
}


