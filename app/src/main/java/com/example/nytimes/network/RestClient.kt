package com.example.nytimes.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class RestClient private constructor() {
    companion object {
        private lateinit var okHttpClient1: OkHttpClient
        private lateinit var okHttpClient2: OkHttpClient
        private var mInstance: RestClient? = null
        fun getInstance(): RestClient {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RestClient()
                }
            }
            return mInstance!!
        }
    }

    init {
        okHttpClient1 = OkHttpClient()
            .newBuilder()
            .addInterceptor(HeaderInterceptor1())
            .connectTimeout(ApiConstant.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(ApiConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()

        okHttpClient2 = OkHttpClient()
            .newBuilder()
            .addInterceptor(HeaderInterceptor2())
            .connectTimeout(ApiConstant.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(ApiConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    fun getApiService1(baseUrl: String): ApiServices {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiServices::class.java)
    }

    fun getApiService2(baseUrl: String?): ApiServices {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl + "/")
            .client(okHttpClient2)
            .addConverterFactory(HtmlConverterFactory.factory)
            .build()
        return retrofit.create(ApiServices::class.java)
    }
}

private class HtmlConverterFactory : Converter<ResponseBody, String> {
    companion object {
        var factory: Converter.Factory = object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<Annotation>,
                retrofit: Retrofit
            ): HtmlConverterFactory? {
                return if (type === String::class.java) HtmlConverterFactory() else null
            }
        }
    }

    override fun convert(responseBody: ResponseBody): String {
        val document: Document = Jsoup.parse(responseBody.string())
        val content: String = document.select("p").text()
        return content
    }
}


private class HeaderInterceptor1 : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(
                    ApiConstant.HEADER_NAME_CONTENT_TYPE,
                    ApiConstant.HEADER_VALUE_CONTENT_TYPE_APPLICATION_JSON
                )
                .url(
                    chain.request().url().newBuilder().addQueryParameter(
                        ApiConstant.QUERY_PARAM_NAME_API_KEY,
                        ApiConstant.QUERY_PARAM_VALUE_API_KEY
                    ).build()
                )
                .build()
        )
    }
}

private class HeaderInterceptor2 : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(
                    ApiConstant.HEADER_NAME_CONTENT_TYPE,
                    ApiConstant.HEADER_VALUE_CONTENT_TYPE_TEXT_HTML
                )
                .build()
        )
    }
}