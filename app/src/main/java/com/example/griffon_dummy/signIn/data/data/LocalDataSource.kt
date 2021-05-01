package com.example.griffon_dummy.signIn.data.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.google.gson.Gson

class LocalDataSource(private val storage: SharedPreferences) :
    LocalDataI {
    override fun saveClient(client: ClientInfo) {
        storage.edit().putString("clientInfo",Gson().toJson(client)).apply()
    }

}
interface LocalDataI{
    fun saveClient(client : ClientInfo)
}