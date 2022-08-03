package com.woosuk.onetouch_pccafe


import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.woosuk.onetouch_pccafe.databinding.ActivityMapBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.*

@AndroidEntryPoint
class MapActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMapBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        mapView=binding.mapView
        startTracking()
        viewModel.placeList.observe(this){
            for(item in it){
                val pcPosition = MapPoint.mapPointWithGeoCoord(item.y.toDouble(), item.x.toDouble())
                val marker = MapPOIItem()
                marker.itemName = item.place_name
                marker.mapPoint = pcPosition
                marker.markerType = MapPOIItem.MarkerType.RedPin
                marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin
                mapView.addPOIItem(marker)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading  //이 부분

        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        //위도 , 경도
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        viewModel.getAddress(uLongitude.toString(),uLatitude.toString())
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)

        // 현 위치에 마커 찍기
        val marker = MapPOIItem()
        marker.itemName = "현 위치"
        marker.mapPoint = uNowPosition
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)
        mapView.setZoomLevel(4, true);
    }

}