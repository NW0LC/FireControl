package com.exz.firecontrol.module

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_map_location.*



class MapLocationActivity : BaseActivity(), AMap.OnMyLocationChangeListener{


    private lateinit var aMap: AMap
    override fun initToolbar(): Boolean {
        mTitle.text = intent.getStringExtra(Intent_Class_Name)

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
        var latLngList = ArrayList<LatLng>()
        if (intent.hasExtra(Intent_Lat)) {
            latLngList = intent.getParcelableArrayListExtra(Intent_Lat)
            if (latLngList.size==0){
                DialogUtils.WarningWithListener(this,"当前暂无水源信息", View.OnClickListener {
                        finish()
                })
            }
        } else {
            latLngList.add(LatLng(34.253505, 117.155179))
        }
        var icLction = "" //宝藏地图页数据
        when (intent.getStringExtra(Intent_Class_Name)) {
            "地址" -> {
                icLction = "icon_pin.png"
            }
            "消火栓" -> {
                icLction = "icon_firefighting1.png"
            }
            "消防水池" -> {
                icLction = "icon_firefighting2.png"
            }
            "消防水罐" -> {
                icLction = "icon_firefighting3.png"
            }
            "车辆位置" -> {
                icLction = "icon_firefighting4.png"
            }
            "人员位置" -> {
                icLction = "icon_person_locaiton.png"
            }
            else->{
            icLction = "icon_pin.png"
        }
        }
        val latLngBound = LatLngBounds.builder()
        val markerOptions=ArrayList<MarkerOptions>()
        for (latLng in latLngList) {
            latLngBound.include(latLng)
            val markerOption = MarkerOptions().icon(BitmapDescriptorFactory.fromAsset(icLction))
                    .position(latLng)
                    .draggable(true)
            markerOptions.add(markerOption)
        }
        aMap.setOnMyLocationChangeListener(this)
        aMap.uiSettings.isScaleControlsEnabled=true
        aMap.uiSettings.isMyLocationButtonEnabled = true //显示默认的定位按钮
        aMap.isMyLocationEnabled = true// 可触发定位并显示当前位置
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW)
        aMap.myLocationStyle = myLocationStyle//设置定位蓝点的Style

        aMap.addMarkers(markerOptions,false)

        val newLatLngBounds = CameraUpdateFactory.newLatLngBounds(latLngBound.build(),250)
        aMap.animateCamera(newLatLngBounds)
        //设置希望展示的地图缩放级别
        val  mCameraUpdate = CameraUpdateFactory.zoomTo(14f)
        aMap.animateCamera(mCameraUpdate)
    }

    override fun onMyLocationChange(location: Location) {
        // 定位回调监听
        Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.latitude + " lon: " + location.longitude)
        val bundle = location.extras
        if (bundle != null) {
            val errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE)
            val errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO)
            // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
            val locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE)

            /*
            errorCode
            errorInfo
            locationType
            */
            Log.e("amap", "定位信息， code: $errorCode errorInfo: $errorInfo locationType: $locationType")
        } else {
            Log.e("amap", "定位信息， bundle is null ")

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
        var Intent_Class_Name = "className"
        var Intent_Lat = "Intent_Lat"
    }
}
