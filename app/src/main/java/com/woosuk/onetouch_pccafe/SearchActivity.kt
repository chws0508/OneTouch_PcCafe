package com.woosuk.onetouch_pccafe

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.woosuk.onetouch_pccafe.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity: AppCompatActivity(){
    private lateinit var binding:ActivitySearchBinding
    private val vm: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_search)

        vm.getAddress(getCurrentLocation().first,getCurrentLocation().second)
        val adapter=PcCafeListAdapter(getCurrentLocation(),this)
        binding.recyclerView.adapter=adapter
        vm.placeList.observe(this){
            adapter.submitList(it)
        }

//        vm.getDetailInfo()

    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation():Pair<String,String>{
        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude

        val pair=Pair(uLongitude.toString(),uLatitude.toString())

        return pair
    }

}