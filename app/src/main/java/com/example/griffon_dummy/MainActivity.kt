package com.example.griffon_dummy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.griffon_dummy.profile.ui.ProfileFragment
import com.example.griffon_dummy.profile.ui.ProfileLogout

class MainActivity : AppCompatActivity() {
    private val profileFragment =
        ProfileFragment()
    private val profileLogout = ProfileLogout()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {

            val sharedPref = getSharedPreferences("sp"
                , Context.MODE_PRIVATE)
            val accessToken = sharedPref.getString("token", null)
            if ( accessToken==null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.profileFragment, profileLogout)
                    .commit()

            }
            else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.profileFragment, profileFragment).commit()
            }
        }
    }
}
