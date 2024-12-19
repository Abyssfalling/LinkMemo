package com.example.linkmemo.data

import com.example.linkmemo.data.bean.UserInfo
import com.example.linkmemo.data.network.NetworkResponseNew
import com.example.linkmemo.data.network.NetworkWordDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

class UserRepository {

    private val networkWordDataSource: NetworkWordDataSource

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(NetworkWordDataSource.baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        networkWordDataSource = retrofit.create(NetworkWordDataSource::class.java)
    }

    suspend fun login(userName: String, password: String): NetworkResponseNew<UserInfo> = networkWordDataSource.login(userName, password)

    suspend fun register(userName: String, password: String, nickname: String): NetworkResponseNew<String> =
        networkWordDataSource.register(userName, password, nickname);

}