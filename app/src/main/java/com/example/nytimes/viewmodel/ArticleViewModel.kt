package com.example.nytimes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nytimes.listener.NetworkResponseCallback
import com.example.nytimes.model.Article
import com.example.nytimes.repository.ArticlesRepository

class ArticleViewModel : ViewModel() {
    private lateinit var articleListLiveData: MutableLiveData<List<Article>>
    val mShowProgressBar = MutableLiveData(true)
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = ArticlesRepository.getInstance()

    fun getFetchArticleLiveData() : MutableLiveData<List<Article>> {
        articleListLiveData =  MutableLiveData<List<Article>>()
        return articleListLiveData
    }

    fun fetchArticleListFromServer() {
        mShowProgressBar.value = true
        mRepository.getArticles(object : NetworkResponseCallback {
            override fun onNetworkFailure(throwable: Throwable) {
                mShowApiError.value = throwable.message
            }

            override fun onNetworkSuccess() {
                mShowProgressBar.value = false
                //articleListLiveData.value
            }
        }, articleListLiveData)
    }

    fun onRefreshClicked() {
        fetchArticleListFromServer()
    }
}