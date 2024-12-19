package com.example.linkmemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkmemo.data.WordRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivityViewModel : ViewModel() {

    private val repo = WordRepository()

    fun getDefinition(word: String): String {
        var res: String = ""
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.searchWord(word)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }

    fun getDefinitionNew(uid : Int = 0 ,word: String): String {
        var res: String = ""
        viewModelScope.launch {
            runBlocking {
                try {
                    res = repo.findNew(uid,word).toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }
}