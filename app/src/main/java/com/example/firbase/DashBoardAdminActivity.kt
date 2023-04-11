package com.example.firbase

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dash_board_admin.*

class DashBoardAdminActivity : BaseActivity()
// , ValueEventListener
{

    //private var clicked = false
    //private lateinit var databaseReference : DatabaseReference
    // arraylist to hold categories

    //adapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_admin)
        replaceFragment(HomeFragment())
        preferences = getSharedPreferences("CATEG_INFO", Context.MODE_PRIVATE)
        val category_Name = preferences.getString("CATEG_NAME", "")
        //txtresult.text = category_Name


        //firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()


//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.home_fragment,
//                R.id.search_fragment,
//                R.id.subsecribe_fragment,
//                R.id.profile_fragment
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        nav_view.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> replaceFragment(HomeFragment())
                R.id.search_fragment -> replaceFragment(SearchFragment())
                R.id.subsecribe_fragment -> replaceFragment(SubsecribeFragment())
                R.id.profile_fragment -> replaceFragment(ProfileFragment())

                else -> {

                }
            }
            true
        }




        // loadCategories()
        //******************************************//

        // search

//        searchEt.addTextChangedListener(object : TextWatcher {
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //called as and when user type anythings
//                try {
//                    adapterCategory.filter.filter(s)
//                } catch (e: Exception) {
//                }
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })

//        btnlogout.setOnClickListener {
//            firebaseAuth.signOut()
//            checkUser()
//        }
//        addcategory.setOnClickListener {
//            val i = Intent(this, AddCategory::class.java)
//            startActivity(i)
//        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransactions = fragmentManager.beginTransaction()
        fragmentTransactions.replace(R.id.frame_layout, fragment)
        fragmentTransactions.commit()
    }

//    private fun checkUser() {
//        val fUser = firebaseAuth.currentUser
//        if (fUser == null) {
//            val i = Intent(this, signin::class.java)
//            startActivity(i)
//            finish()
//        } else {
//            val email = fUser.email
//            //subTitleTv.text = email
//        }
//    }
//
//    private fun loadCategories() {
//        //init arraylist
//        categoryArrayList = ArrayList()
//
////        // get all categories from firebase data base ... Firebase DB > categories
//        val ref = FirebaseDatabase.getInstance().getReference("Categories")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //clear list before starting adding data into it
//                categoryArrayList.clear()
//
//                if (snapshot.exists()) {
//                    for (ds in snapshot.children) {
//                        //get data as model
//                        val model = ds.getValue(UserCare::class.java)
//                        //add to arraylist
//                        categoryArrayList.add(model!!)
//                    }
//                    //setup adapter
//                    adapterCategory =
//                        AdapterCategory(this@DashBoardAdminActivity, categoryArrayList)
//
//                    //setup adapter to recyclerview
//                    // categoriesRv.adapter = adapterCategory
//                }
//                // adapterCategory.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }

    override fun onBackPressed() {
        doubleBackToExit()
    }



//    override fun onDataChange(snapshot: DataSnapshot) {
//
//        // myAdapter.notifyDataSetChanged()
//    }
//
//        override fun onCancelled(error: DatabaseError) {
//        }
}


//        btnback.setOnClickListener {
//            //onBackPressed()
//            val i = Intent(this, signin::class.java)
//            startActivity(i)
//        }















