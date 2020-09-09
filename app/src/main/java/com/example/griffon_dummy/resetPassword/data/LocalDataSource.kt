package com.example.griffon_dummy.resetPassword.data

import android.content.SharedPreferences
import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.google.gson.Gson

class LocalDataSource(private val sharedPreferences: SharedPreferences) : LocalDataI {
    override fun getSP(): ClientInfo {
        return Gson().fromJson(sharedPreferences.getString("clientInfo",""), ClientInfo::class.java)
    }

}
interface LocalDataI{
    fun getSP(): ClientInfo
}