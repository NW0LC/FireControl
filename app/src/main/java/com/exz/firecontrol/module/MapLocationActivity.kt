package com.exz.firecontrol.module

import android.content.Context
import android.location.Location
import android.util.Log
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.map.geolocation.TencentLocationRequest
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory
import com.tencent.tencentmap.mapsdk.maps.LocationSource
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment
import com.tencent.tencentmap.mapsdk.maps.TencentMap
import com.tencent.tencentmap.mapsdk.maps.model.*
import kotlinx.android.synthetic.main.action_bar_custom.*



/**
 * Created by pc on 2017/12/19.
 */

class MapLocationActivity : BaseActivity(), TencentMap.OnMarkerClickListener {

    private var myLocation: Marker? = null
    private lateinit var locationManager: TencentLocationManager
    private lateinit var locationRequest: TencentLocationRequest
    private lateinit var tencentMap: TencentMap
    private lateinit var locationSource: DemoLocationSource
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

    override fun init() {
        initView()

    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()
        //这里调用了腾讯定位sdk,如果不再需要定位功能，必须关闭定位。
        if (tencentMap.isMyLocationEnabled) {
            tencentMap.isMyLocationEnabled = false
        }
    }

    private fun initView() {
        val fm = supportFragmentManager
        //获取TencentMap实例
        tencentMap = (fm.findFragmentById(R.id.frag_map) as SupportMapFragment).getMap()
        tencentMap.uiSettings.isZoomControlsEnabled = false

        locationSource = DemoLocationSource(this)
        tencentMap.setLocationSource(locationSource)
        tencentMap.isMyLocationEnabled = true

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


        //标记点击事件
        tencentMap.setOnMarkerClickListener(this)
        tencentMap.addMarker(MarkerOptions()
                .position(lat).icon(BitmapDescriptorFactory.fromAsset(icLction)))

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    internal inner class DemoLocationSource(private var mContext: Context?) : LocationSource, TencentLocationListener {
        private var mChangedListener: LocationSource.OnLocationChangedListener? = null
        private var locationManager: TencentLocationManager? = null
        private var locationRequest: TencentLocationRequest? = null

        init {
            locationManager = TencentLocationManager.getInstance(mContext)
            locationRequest = TencentLocationRequest.create()
            locationRequest!!.interval = 2000
        }// TODO Auto-generated constructor stub

        override fun onLocationChanged(arg0: TencentLocation, arg1: Int,
                                       arg2: String) {
            // TODO Auto-generated method stub
            if (arg1 == TencentLocation.ERROR_OK && mChangedListener != null) {
                Log.e("maplocation", "location: " + arg0.city + " " + arg0.provider)
                val location = Location(arg0.provider)
                location.latitude = arg0.latitude
                location.longitude = arg0.longitude
                location.accuracy = arg0.accuracy

                tencentMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(location.latitude,  location.longitude), 3f, 0f, 0f)))


                mChangedListener!!.onLocationChanged(location)
            }
        }

        override fun onStatusUpdate(arg0: String, arg1: Int, arg2: String) {

        }

        override fun activate(arg0: LocationSource.OnLocationChangedListener) {
            // TODO Auto-generated method stub
            mChangedListener = arg0
            val err = locationManager!!.requestLocationUpdates(locationRequest!!, this)
            when (err) {
                1 -> title = "设备缺少使用腾讯定位服务需要的基本条件"
                2 -> title = "manifest 中配置的 key 不正确"
                3 -> title = "自动加载libtencentloc.so失败"

                else -> {
                }
            }
        }

        override fun deactivate() {
            // TODO Auto-generated method stub
            locationManager!!.removeUpdates(this)
            mContext = null
            locationManager = null
            locationRequest = null
            mChangedListener = null
        }

        fun onPause() {
            locationManager!!.removeUpdates(this)
        }

        fun onResume() {
            locationManager!!.requestLocationUpdates(locationRequest!!, this)
        }

    }


//    override fun onLocationChanged(arg0: TencentLocation, arg1: Int, arg2: String) {
//        if (arg1 == TencentLocation.ERROR_OK) {
//            val latLng = LatLng(arg0.latitude, arg0.longitude)
//            tencentMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, 3f, 0f, 0f)))
//            //设置地图中心点
//            myLocation = if (myLocation != null) {
//                myLocation?.remove()
//                tencentMap.addMarker(MarkerOptions().position(latLng).anchor(0.5f, 0.5f))
//            } else {
//                tencentMap.addMarker(MarkerOptions().position(latLng).anchor(0.5f, 0.5f))
//            }
//            myLocation?.position = latLng
//            myLocation?.rotation = arg0.bearing //仅当定位来源于gps有效，或者使用方向传感器
//
//        } else {
//            Log.e("location", "location failed:" + arg2)
//        }
//    }


    companion object {
        var Intent_Class_Name="className"
        var Intent_Latitude="latitude"
        var Intent_Longitude="longitude"
    }
}
