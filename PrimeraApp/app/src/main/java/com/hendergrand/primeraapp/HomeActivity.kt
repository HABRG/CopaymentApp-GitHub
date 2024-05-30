package com.hendergrand.primeraapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hendergrand.primeraapp.fragment.ActivityFragment
import com.hendergrand.primeraapp.fragment.HomeFragment
import com.hendergrand.primeraapp.fragment.MyCardFragment
import com.hendergrand.primeraapp.fragment.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottomNavigationV)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_Home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_Card -> {
                    replaceFragment(MyCardFragment())
                    true
                }
                R.id.bottom_Activity -> {
                    replaceFragment(ActivityFragment())
                    true
                }
                R.id.bottom_Profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment())

    }

    private fun replaceFragment(fragment: Fragment){

       supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }

}

