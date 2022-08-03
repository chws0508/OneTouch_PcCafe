package com.woosuk.onetouch_pccafe

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(
    @field:Json(name="place_name") var place_name: String, // 장소명, 업체명
    @field:Json(name="category_name") var category_name:String,
    @field:Json(name="road_address_name") var road_address_name:String,
    @field:Json(name="distance") var distance:String,
    @field:Json(name="category_group_name") var category_group_name:String,
    @field:Json(name="x") var x:String,
    @field:Json(name="y") var y:String,
    @field:Json(name="place_url") var place_url:String
)