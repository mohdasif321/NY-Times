package com.example.nytimes.listener

interface NetworkResponseCallback {
    fun onNetworkSuccess()
    fun onNetworkFailure(throwable : Throwable)
}