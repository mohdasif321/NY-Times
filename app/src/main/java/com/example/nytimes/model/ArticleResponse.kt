package com.example.nytimes.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(@SerializedName("copyright")
                           val copyright: String? = "",
                           @SerializedName("results")
                           val results: List<ResultsItem>?,
                           @SerializedName("num_results")
                           val numResults: Int? = 0,
                           @SerializedName("status")
                           val status: String? = "")

data class MediaMetadataItem(@SerializedName("format")
                             val format: String? = "",
                             @SerializedName("width")
                             val width: Int? = 0,
                             @SerializedName("url")
                             val url: String? = "",
                             @SerializedName("height")
                             val height: Int? = 0)

data class ResultsItem(@SerializedName("per_facet")
                       val perFacet: List<String>?,
                       @SerializedName("eta_id")
                       val etaId: Int? = 0,
                       @SerializedName("subsection")
                       val subsection: String? = "",
                       @SerializedName("nytdsection")
                       val nytdsection: String? = "",
                       @SerializedName("column")
                       val column: String? = null,
                       @SerializedName("section")
                       val section: String? = "",
                       @SerializedName("asset_id")
                       val assetId: Long? = 0,
                       @SerializedName("source")
                       val source: String? = "",
                       @SerializedName("abstract")
                       val abstract: String? = "",
                       @SerializedName("media")
                       val media: List<MediaItem>?,
                       @SerializedName("type")
                       val type: String? = "",
                       @SerializedName("title")
                       val title: String? = "",
                       @SerializedName("des_facet")
                       val desFacet: List<String>?,
                       @SerializedName("uri")
                       val uri: String? = "",
                       @SerializedName("url")
                       val url: String? = "",
                       @SerializedName("adx_keywords")
                       val adxKeywords: String? = "",
                       @SerializedName("geo_facet")
                       val geoFacet: List<String>?,
                       @SerializedName("id")
                       val id: Long? = 0,
                       @SerializedName("published_date")
                       val publishedDate: String? = "",
                       @SerializedName("updated")
                       val updated: String? = "",
                       @SerializedName("byline")
                       val byline: String? = "")

data class MediaItem(@SerializedName("copyright")
                     val copyright: String? = "",
                     @SerializedName("media-metadata")
                     val mediaMetadata: List<MediaMetadataItem>?,
                     @SerializedName("subtype")
                     val subtype: String? = "",
                     @SerializedName("caption")
                     val caption: String? = "",
                     @SerializedName("type")
                     val type: String? = "",
                     @SerializedName("approved_for_syndication")
                     val approvedForSyndication: Int? = 0)