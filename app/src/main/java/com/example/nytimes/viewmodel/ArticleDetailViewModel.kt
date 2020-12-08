package com.example.nytimes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nytimes.listener.NetworkResponseCallback
import com.example.nytimes.model.Article
import com.example.nytimes.repository.ArticlesRepository

class ArticleDetailViewModel: ViewModel() {
    private lateinit var articleDetailLiveData: MutableLiveData<Article>
    val mShowProgressBar = MutableLiveData(true)
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = ArticlesRepository.getInstance()

    fun getFetchArticleDetailLiveData() : MutableLiveData<Article> {
        articleDetailLiveData =  MutableLiveData<Article>()
        return articleDetailLiveData
    }

    fun fetchArticleDetailFromServer(article: Article) {
        mShowProgressBar.value = true
        mRepository.getArticleDetail(article, object : NetworkResponseCallback {
            override fun onNetworkFailure(throwable: Throwable) {
                mShowApiError.value = throwable.message
            }

            override fun onNetworkSuccess() {
                mShowProgressBar.value = false
                //articleDetailLiveData.value
            }
        }, articleDetailLiveData)
    }
}