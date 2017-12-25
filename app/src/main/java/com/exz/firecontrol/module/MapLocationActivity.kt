package com.exz.firecontrol.module

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdate
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_map_location.*


/**
 * Created by pc on 2017/12/19.
 */

class MapLocationActivity : BaseActivity(), AMap.OnMyLocationChangeListener {


    private lateinit var aMap: AMap
    private lateinit var markerOption: MarkerOptions
    private lateinit var marker: Marker
    override fun initToolbar(): Boolean {
        mTitle.text =intent.getStringExtra(Intent_Class_Name)

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        //状态栏透明和间距处理
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_map_location
    }


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState) // 此方法必须重写
         initView()

     }

    private fun initView() {
            aMap = mapView.map

//设置缩放级别
        var lat: LatLng
        if(intent.hasExtra(Intent_Latitude)&&intent.hasExtra(Intent_Longitude)){
            lat= LatLng(intent.getDoubleExtra(Intent_Latitude,0.0),intent.getDoubleExtra(Intent_Longitude,0.0))
        }else{
            lat= LatLng(34.253505, 117.155179)
        }
        var  icLction = "" //宝藏地图页数据
        when (intent.getStringExtra(Intent_Class_Name)) {
            "地址" -> {
                icLction="icon_pin.png"
            }
            "消防栓" -> {
                icLction="icon_firefighting1.png"
            }
            "消防水池" -> {
                icLction="icon_firefighting2.png"
            }
            "消防水罐" -> {
                icLction="icon_firefighting3.png"
            }
            "车辆位置" -> {
                icLction="icon_firefighting4.png"
            }
            "人员位置" -> {
                icLction="icon_person_locaiton.png"
            }
        }
        markerOption = MarkerOptions().icon(BitmapDescriptorFactory.fromAsset(icLction))
                .position(lat)
                .draggable(true)
        marker = aMap.addMarker(markerOption)

        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this)
      var   myLocationStyle = MyLocationStyle()
        aMap.uiSettings.isMyLocationButtonEnabled = true// 设置默认定位按钮是否显示
        aMap.isMyLocationEnabled = true// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.myLocationStyle = myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        changeCamera(CameraUpdateFactory.zoomIn(), null);
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private fun changeCamera(update: CameraUpdate, callback: AMap.CancelableCallback?) {
            aMap.animateCamera(update, 1000, callback)
            aMap.moveCamera(update)
    }

    override fun onMyLocationChange(location: Location) {
        // 定位回调监听
        if (location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude())
            val bundle = location.getExtras()
            if (bundle != null) {
                val errorCode = bundle!!.getInt(MyLocationStyle.ERROR_CODE)
                val errorInfo = bundle!!.getString(MyLocationStyle.ERROR_INFO)
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                val locationType = bundle!!.getInt(MyLocationStyle.LOCATION_TYPE)

                /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: $errorCode errorInfo: $errorInfo locationType: $locationType")
            } else {
                Log.e("amap", "定位信息， bundle is null ")

            }

        } else {
            Log.e("amap", "定位失败")
        }
    }

    /**
     * 方法必须重写
    */

     override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    /**
     * 方法必须重写
     */

     override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    /**
     * 方法必须重写
     */

     override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }


    companion object {
        var Intent_Class_Name="className"
        var Intent_Latitude="latitude"
        var Intent_Longitude="longitude"
    }
}
