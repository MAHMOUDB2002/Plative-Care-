package com.example.firbase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.firbase.fragment_user.ChatUserFragment
import com.example.firbase.R
import com.example.firbase.databinding.ActivityDashBoardUserBinding
import com.example.firbase.fragment_admin.ProfileAdminFragment
import com.example.firbase.fragment_user.HomeUserFragment
import com.example.firbase.fragment_user.ProfileUserFragment
import com.example.firbase.fragment_user.SearchFragment
import com.example.firbase.fragment_user.SubsecribeFragment
import com.example.firbase.utils.BaseActivity

class DashBoardUserActivity : BaseActivity() {

    private lateinit var binding: ActivityDashBoardUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //makeCurrentFragment(HomeUserFragment())

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment_user -> makeCurrentFragment(HomeUserFragment())
                R.id.search_fragment_user -> makeCurrentFragment(SearchFragment())
                R.id.subsecribe_fragment_user -> makeCurrentFragment(SubsecribeFragment())
                R.id.profile_fragment_user -> makeCurrentFragment(ProfileUserFragment())
                R.id.chat_user_fragment -> makeCurrentFragment(ChatUserFragment())

                else -> {

                }
            }
            true
        }

    }
    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_user, fragment).addToBackStack(null)
            commit()
        }
    }


    override fun onBackPressed() {
        doubleBackToExit()
    }


}



