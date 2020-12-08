package com.example.nytimes.network

import com.example.nytimes.model.ArticleResponse
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url


interface ApiServices {
    @GET("svc/mostpopular/v2/mostviewed/all-sections/{index}.json")
    fun getArticles(@Path("index") index: Int): Call<ArticleResponse>?

    @GET(".")
    fun getArticleDetail(): Call<String>?
}