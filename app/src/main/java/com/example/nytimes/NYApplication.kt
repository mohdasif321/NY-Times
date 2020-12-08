package com.example.nytimes

import android.app.Application

class NYApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        lateinit var instance: NYApplication
    }
}