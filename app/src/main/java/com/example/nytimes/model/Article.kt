package com.example.nytimes.model

import java.io.Serializable

data class Article(val title : String?, val byLine: String?, val publishedDate: String?, val imageUrl: String?, val contentUrl: String?, var content: String?) : Serializable