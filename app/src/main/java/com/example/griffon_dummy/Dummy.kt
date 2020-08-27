package com.example.griffon_dummy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Dummy : AppCompatActivity() {

    private val signIn = SignIn()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_griffon)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signIn)
                .commit()
        }
    }

}
