package com.woosuk.onetouch_pccafe

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultKeywordSearch(
    @field:Json(name="documents") val documents:List<Place>
)






