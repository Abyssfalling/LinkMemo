package com.example.linkmemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkmemo.data.UserRepository
import com.example.linkmemo.data.network.NetworkResponseNew
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterActivityViewModel : ViewModel() {

    private val repo = UserRepository()

    fun register(userName: String, password: String, nickName: String): NetworkResponseNew<String>? {
        var res: NetworkResponseNew<String>? = null
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.register(userName, password, nickName)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

}