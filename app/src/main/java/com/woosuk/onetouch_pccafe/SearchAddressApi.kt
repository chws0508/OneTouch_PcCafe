package com.woosuk.onetouch_pccafe

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchAddressApi {

    @GET("/v2/local/search/keyword.json")
    suspend fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 API 인증키 [필수]
        @Query("query") query: String,// 검색을 원하는 질의어 [필수]
        @Query("x") x: String,
        @Query("y") y:String,
        @Query("sort") sort:String
        ): Response<ResultKeywordSearch>

    @GET("26093235")
    suspend fun getDetailInfo()
}