package com.woosuk.onetouch_pccafe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAddressApi: SearchAddressApi,
    private val okHttpClient: OkHttpClient
):ViewModel() {

    private val _placeList = MutableLiveData<MutableList<Place>>()
    val placeList: LiveData<MutableList<Place>> get() = _placeList

    fun getAddress(x:String,y:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response=searchAddressApi.getSearchKeyword(restApiKey,"PC방",x,y,"distance").body()
                response?.documents?.map {
                    it.distance=it.distance+"m"
                }
                _placeList.postValue(response!!.documents.toMutableList())
            }catch (exception:Exception){
                Log.d("Error",exception.toString())
            }
        }

    }

//     fun getDetailInfo(){
//        viewModelScope.launch(Dispatchers.IO) {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("http://place.map.kakao.com/")
//                .client(okHttpClient)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build()
//
//            val api = retrofit.create(SearchAddressApi::class.java)
//            api.getDetailInfo()
//        }
//    }

    companion object{
        const val restApiKey="KakaoAK 0e99ed03009dc5834ba42e5c4f25463a"
    }
}