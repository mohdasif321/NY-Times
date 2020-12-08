package com.example.nytimes.repository

import androidx.lifecycle.MutableLiveData
import com.example.nytimes.listener.NetworkResponseCallback
import com.example.nytimes.model.Article
import com.example.nytimes.model.ArticleResponse
import com.example.nytimes.model.ResultsItem
import com.example.nytimes.network.ApiConstant
import com.example.nytimes.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesRepository private constructor() {

    companion object {
        private var mInstance: ArticlesRepository? = null
        fun getInstance(): ArticlesRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ArticlesRepository()
                }
            }
            return mInstance!!
        }
    }

    fun getArticles(callback: NetworkResponseCallback, fetchArticleLiveData: MutableLiveData<List<Article>>) {
        val mArticleCall: Call<ArticleResponse> =
            RestClient.getInstance().getApiService1(ApiConstant.BASE_URL)
                .getArticles(ApiConstant.PAGE_INDEX)!!
        mArticleCall.enqueue(CallbackImpl(null, callback, fetchArticleLiveData)) /*object : Callback<ArticleResponse> {

            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                val resultList : List<ResultsItem>?  = response.body()?.results;
                val articleList = arrayListOf<Article>()

                resultList?.let {
                    it.forEach {
                        val article = Article(it.title, it.byline, it.publishedDate,
                            it.media?.get(0)?.mediaMetadata?.get(2)?.url, it.url, "")
                        articleList.add(article)
                    }
                }
                fetchArticleLiveData.value = articleList
                callback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                fetchArticleLiveData.value = emptyList()
                callback.onNetworkFailure(t)
            }
        })*/
    }

    fun getArticleDetail(article: Article, callback: NetworkResponseCallback, fetchArticleDetailLiveData: MutableLiveData<Article>) {
        val mArticleCall: Call<String> = RestClient.getInstance().getApiService2(article.contentUrl).getArticleDetail()!!
        mArticleCall.enqueue(CallbackImpl(article, callback, fetchArticleDetailLiveData))/*object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val articleDetailString : String?  = response.body()
                article.content = articleDetailString
                fetchArticleDetailLiveData.value = article
                callback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<String>, throwable: Throwable) {
                fetchArticleDetailLiveData.value = null
                callback.onNetworkFailure(throwable)
            }
        })*/
    }

    private class CallbackImpl<T, J>(
       var article: Article?,
       val callback: NetworkResponseCallback,
       val liveData: MutableLiveData<J>
    ) : Callback<T> {

       /* var article: Article? = null
        var callback: NetworkResponseCallback? = null
        var liveData: MutableLiveData<J>? = null

        init {
            this.article = article
            this.callback = callback
            this.liveData = liveData
        }*/

        override fun onResponse(call: Call<T>, response: Response<T>) {
            when (response.body()) {
                is String -> {
                    article?.content = response.body() as? String
                    liveData.value  = article as J
                    callback.onNetworkSuccess()
                }
                is ArticleResponse -> {
                    val resultList: List<ResultsItem>? = (response.body() as ArticleResponse).results
                    val articleList = arrayListOf<Article>()

                    resultList?.let {
                        it.forEach {
                            val article = Article(
                                it.title, it.byline, it.publishedDate,
                                it.media?.get(0)?.mediaMetadata?.get(2)?.url, it.url, ""
                            )
                            articleList.add(article)
                        }
                    }
                    liveData.value = articleList as J
                    callback.onNetworkSuccess()
                }
            }
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            liveData.value = null
            callback.onNetworkFailure(throwable)
        }
    }
}