package com.example.nytimes.network

class ApiConstant private constructor() {
    companion object {
        const val BASE_URL = "https://api.nytimes.com/"
        const val CONNECT_TIMEOUT: Long = 30000
        const val READ_TIMEOUT: Long = 30000
        const val WRITE_TIMEOUT: Long = 30000
        const val QUERY_PARAM_NAME_API_KEY = "api-key"
        const val QUERY_PARAM_VALUE_API_KEY = "klsMIZIMStEzK6s4aFh2btG04Mz0g68Y"
        const val HEADER_NAME_CONTENT_TYPE = "Content-Type"
        const val HEADER_VALUE_CONTENT_TYPE_APPLICATION_JSON = "application/json"
        const val HEADER_VALUE_CONTENT_TYPE_TEXT_HTML = "text/html"
        const val PAGE_INDEX = 7
    }
}