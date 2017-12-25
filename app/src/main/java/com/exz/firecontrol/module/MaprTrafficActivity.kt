package com.exz.firecontrol.module

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.amap.api.maps.AMap
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.model.MyTrafficStyle
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_map_location.*

/**
 * Created by pc on 2017/12/22.
 */

class MaprTrafficActivity : BaseActivity(), AMap.OnMyLocationChangeListener {

    private lateinit var aMap: AMap
    override fun initToolbar(): Boolean {
        mTitle.text ="实时交通"

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

        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this)
        var   myLocationStyle = MyLocationStyle()
        aMap.uiSettings.isMyLocationButtonEnabled = true// 设置默认定位按钮是否显示
        aMap.isMyLocationEnabled = true// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.myLocationStyle = myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        //自定义实时交通信息的颜色样式
        val myTrafficStyle = MyTrafficStyle()
        myTrafficStyle.seriousCongestedColor = -0x6dfff6
        myTrafficStyle.congestedColor = -0x15fcee
        myTrafficStyle.slowColor = -0x8af8
        myTrafficStyle.smoothColor = -0xff5df7
        aMap.myTrafficStyle = myTrafficStyle
        aMap.isTrafficEnabled = true//实时交通
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


}
