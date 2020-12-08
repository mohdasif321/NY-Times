package com.example.nytimes.listener

import com.example.nytimes.model.Article

interface ImplItemClick {
    fun onItemClick(article: Article)
}