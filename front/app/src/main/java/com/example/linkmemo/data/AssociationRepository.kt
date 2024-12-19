package com.example.linkmemo.data

import com.example.linkmemo.data.bean.Point
import com.example.linkmemo.data.network.NetworkResponseNew
import com.example.linkmemo.data.network.NetworkWordDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AssociationRepository {

    private val networkWordDataSource: NetworkWordDataSource

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(NetworkWordDataSource.baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        networkWordDataSource = retrofit.create(NetworkWordDataSource::class.java)
    }

    /**
     * 新增关系
     */
    suspend fun add(uid: Int, lid: Int, type: Int): NetworkResponseNew<String> = networkWordDataSource.addAssociation(uid, lid, type)

    /**
     * 取消关系
     */
    suspend fun delete(aid: Int): NetworkResponseNew<String> = networkWordDataSource.deleteAssociation(aid)


    /**
     * 模糊查询单词
     */
    suspend fun getWordByFuzzy(str: String): NetworkResponseNew<List<Point>> = networkWordDataSource.getWordByFuzzy(str)

    /**
     * 新增用户关系
     */
    suspend fun addCustomerAssociation(uid: Int, mWid: Int, sWord: String, type: Int): NetworkResponseNew<String> =
        networkWordDataSource.addCustomerAssociation(uid, mWid, sWord, type)

}