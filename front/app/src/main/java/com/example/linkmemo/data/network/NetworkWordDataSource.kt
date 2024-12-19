package com.example.linkmemo.data.network

import com.example.linkmemo.data.bean.Point
import com.example.linkmemo.data.bean.UserInfo
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkWordDataSource {

    // 新增 这里后台ip地址、端口
    companion object {
        val baseUrl: String
            get() = "http://101.227.236.139:8199/"
    }


    // 新增 新查询方法
    @GET("getPointInfo")
    suspend fun findNew(@Query("uid") uid: Int, @Query("english") word: String): JsonObject

    @GET("loginForApp")
    suspend fun login(@Query("name") name: String, @Query("pwd") pwd: String): NetworkResponseNew<UserInfo>

    @GET("registerForApp")
    suspend fun register(@Query("name") name: String, @Query("pwd") pwd: String, @Query("nickName") nickname: String): NetworkResponseNew<String>

    @GET("association/add")
    suspend fun addAssociation(
        @Query("uid") uid: Int, @Query("lid") lid: Int, @Query("type") type: Int
    ): NetworkResponseNew<String>

    @GET("association/addCustomer")
    suspend fun addCustomerAssociation(
        @Query("uid") uid: Int, @Query("mWid") mWid: Int, @Query("sWord") sWord: String, @Query("type") type: Int
    ): NetworkResponseNew<String>


    @GET("association/delete")
    suspend fun deleteAssociation(@Query("aid") aid: Int): NetworkResponseNew<String>

    @GET("find")
    suspend fun getDefinition(@Query("english") word: String): NetworkResponse

    @GET("getWordByFuzzy")
    suspend fun getWordByFuzzy(@Query("str") word: String): NetworkResponseNew<List<Point>>

}