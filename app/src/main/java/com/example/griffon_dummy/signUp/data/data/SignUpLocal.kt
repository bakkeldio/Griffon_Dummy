package com.example.griffon_dummy.signUp.data.data

import android.content.SharedPreferences
import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.google.gson.Gson

class SignUpLocal(private val sharedPreferences: SharedPreferences) : SignUpLocalI{
    override fun getClientInfo():ClientInfo{
        return Gson().fromJson(sharedPreferences.getString("clientInfo",""), ClientInfo::class.java)
    }
}
interface SignUpLocalI{
    fun getClientInfo():ClientInfo
}