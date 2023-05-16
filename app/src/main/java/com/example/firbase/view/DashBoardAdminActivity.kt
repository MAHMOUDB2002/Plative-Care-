package com.example.firbase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.firbase.fragment_admin.NotificationsFragment
import com.example.firbase.fragment_admin.ChatAdminFragment
import com.example.firbase.R
import com.example.firbase.databinding.ActivityDashBoardAdminBinding
import com.example.firbase.fragment_admin.HomeAdminFragment
import com.example.firbase.fragment_admin.ProfileAdminFragment
import com.example.firbase.fragment_user.SearchFragment
import com.example.firbase.utils.BaseActivity

class DashBoardAdminActivity : BaseActivity()
// , ValueEventListener
{

    //private var clicked = false
    //private lateinit var databaseReference : DatabaseReference
    // arraylist to hold categories

    //adapter
    //private lateinit var firebaseAuth: FirebaseAuth
   // private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDashBoardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(HomeFragment())
       // preferences = getSharedPreferences("CATEG_INFO", Context.MODE_PRIVATE)
        // val category_Name = preferences.getString("CATEG_NAME", "")

        makeCurrentFragment(HomeAdminFragment())



        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> makeCurrentFragment(HomeAdminFragment())
                R.id.search_fragment -> makeCurrentFragment(SearchFragment())
                R.id.chat_admin_fragment -> makeCurrentFragment(ChatAdminFragment())
                R.id.profile_fragment -> makeCurrentFragment(ProfileAdminFragment())
                R.id.notifications_admin_fragment -> makeCurrentFragment(NotificationsFragment())

                else -> {

                }
            }
            true
        }
    }


    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment).addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }


}


//        btnback.setOnClickListener {
//            //onBackPressed()
//            val i = Intent(this, signin::class.java)
//            startActivity(i)
//        }



















