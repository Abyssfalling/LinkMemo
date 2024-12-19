package com.example.linkmemo.data.network

data class NetworkResponseNew<T>(var code: Int, var message: String, var data: T)