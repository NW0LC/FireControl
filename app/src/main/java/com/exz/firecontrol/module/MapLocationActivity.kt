package com.exz.firecontrol.module

import android.util.Log
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.map.geolocation.TencentLocationRequest
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory
import com.tencent.mapsdk.raster.model.LatLng
import com.tencent.mapsdk.raster.model.Marker
import com.tencent.mapsdk.raster.model.MarkerOptions
import com.tencent.tencentmap.mapsdk.map.TencentMap
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_map_location.*

/**
 * Created by pc on 2017/12/19.
 */

class MapLocationActivity : BaseActivity(), TencentLocationListener, TencentMap.OnMarkerClickListener {

    private var myLocation: Marker? = null
    private lateinit var locationManager: TencentLocationManager
    private lateinit var locationRequest: TencentLocationRequest
    private lateinit var tencentMap: TencentMap
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
        super.onDestroy()
        locationManager.removeUpdates(this)
    }
    private fun initView() {
        //获取TencentMap实例
        tencentMap = mapview.getMap()
        //注册定位
        locationManager = TencentLocationManager.getInstance(this)
        locationRequest = TencentLocationRequest.create()

        locationManager.requestLocationUpdates(
                locationRequest, this)



//设置缩放级别
        tencentMap.setZoom(13)
        var lat:LatLng
        if(intent.hasExtra(Intent_Latitude)&&intent.hasExtra(Intent_Longitude)){
            lat= LatLng(intent.getDoubleExtra(Intent_Latitude,0.0),intent.getDoubleExtra(Intent_Longitude,0.0))
        }else{
            lat= LatLng(34.253505, 117.155179)
        }

        //标记点击事件
        tencentMap.setOnMarkerClickListener(this)
        tencentMap.addMarker(MarkerOptions()
                .position(lat))
//                .icon(BitmapDescriptorFactory.fromAsset(icLction))
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    override fun onLocationChanged(arg0: TencentLocation, arg1: Int, arg2: String) {
        if (arg1 == TencentLocation.ERROR_OK) {
            val latLng = LatLng(arg0.latitude, arg0.longitude)
            //设置地图中心点
            tencentMap.setCenter(latLng)
            myLocation = if (myLocation != null) {
                myLocation?.remove()
                tencentMap.addMarker(MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_red_location)).anchor(0.5f, 0.5f))
            } else {
                tencentMap.addMarker(MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_red_location)).anchor(0.5f, 0.5f))
            }
            myLocation?.position = latLng
            myLocation?.rotation = arg0.bearing //仅当定位来源于gps有效，或者使用方向传感器

        } else {
            Log.e("location", "location failed:" + arg2)
        }
    }

    override fun onStatusUpdate(arg0: String, arg1: Int, arg2: String) {
        var desc = ""
        when (arg1) {
            TencentLocationListener.STATUS_DENIED -> desc = "权限被禁止"
            TencentLocationListener.STATUS_DISABLED -> desc = "模块关闭"
            TencentLocationListener.STATUS_ENABLED -> desc = "模块开启"
            TencentLocationListener.STATUS_GPS_AVAILABLE -> desc = "GPS可用，代表GPS开关打开，且搜星定位成功"
            TencentLocationListener.STATUS_GPS_UNAVAILABLE -> desc = "GPS不可用，可能 gps 权限被禁止或无法成功搜星"
            TencentLocationListener.STATUS_LOCATION_SWITCH_OFF -> desc = "位置信息开关关闭，在android M系统中，此时禁止进行wifi扫描"
            TencentLocationListener.STATUS_UNKNOWN -> {
            }
        }
        Log.e("location", "location status:$arg0, $arg2 $desc")
    }

    companion object {
        var Intent_Class_Name="className"
        var Intent_Latitude="latitude"
        var Intent_Longitude="longitude"
    }
}
