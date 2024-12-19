package com.example.linkmemo.data

import com.example.linkmemo.data.network.NetworkWordDataSource
import com.example.linkmemo.data.network.NetworkWordDataSource.Companion.baseUrl
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordRepository {

    private val networkWordDataSource: NetworkWordDataSource

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        networkWordDataSource = retrofit.create(NetworkWordDataSource::class.java)
    }

    suspend fun searchWord(english: String): String = networkWordDataSource.getDefinition(english).str

    // 新增 查询新的方法
    suspend fun findNew(uid: Int, english: String): JsonObject = networkWordDataSource.findNew(uid, english)

}