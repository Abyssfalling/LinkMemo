package com.example.linkmemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkmemo.data.AssociationRepository
import com.example.linkmemo.data.bean.Point
import com.example.linkmemo.data.network.NetworkResponseNew
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AssociationViewModel : ViewModel() {

    private val repo = AssociationRepository()

    fun addAssociation(uid: Int, lid: Int, type: Int): NetworkResponseNew<String>? {
        var res: NetworkResponseNew<String>? = null
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.add(uid, lid, type)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

    fun deleteAssociation(aid: Int): NetworkResponseNew<String>? {
        var res: NetworkResponseNew<String>? = null
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.delete(aid)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

    /**
     * 获取模糊查询
     */
    fun getWordByFuzzy(str: String): NetworkResponseNew<List<Point>>? {
        var res: NetworkResponseNew<List<Point>>? = null
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.getWordByFuzzy(str)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

    /**
     * 新增用户关联关系
     */
    fun addCustomerAssociation(uid: Int, mWid: Int, sWord: String, type: Int): NetworkResponseNew<String>? {
        var res: NetworkResponseNew<String>? = null
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.addCustomerAssociation(uid, mWid, sWord, type)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

}