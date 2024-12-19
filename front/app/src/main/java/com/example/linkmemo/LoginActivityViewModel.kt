package com.example.linkmemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkmemo.data.UserRepository
import com.example.linkmemo.data.bean.UserInfo
import com.example.linkmemo.data.network.NetworkResponseNew
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivityViewModel : ViewModel() {

    private val repo = UserRepository()

    fun login(userName: String, password: String): NetworkResponseNew<UserInfo>? {
        var res: NetworkResponseNew<UserInfo>? = null
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.login(userName, password)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

}