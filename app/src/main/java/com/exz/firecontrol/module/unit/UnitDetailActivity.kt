package com.exz.firecontrol.module.unit

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.AppUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.UnitDetailAdapter
import com.exz.firecontrol.bean.UnitDetailBean
import com.exz.firecontrol.module.unit.DrawingsActivity.Companion.Intent_getDrawFileList_id
import com.exz.firecontrol.module.unit.EnterPriseDataActivity.Companion.Intent_EnterPriseDataActivity_id
import com.exz.firecontrol.module.unit.FirewaterSupplyActivity.Companion.Intent_FireWater_comId
import com.exz.firecontrol.module.unit.FirewaterSupplyActivity.Companion.Intent_FireWater_lat
import com.exz.firecontrol.module.unit.FirewaterSupplyActivity.Companion.Intent_FireWater_lon
import com.exz.firecontrol.module.unit.InfoActivity.Companion.Intent_getEnterPrise_id
import com.exz.firecontrol.pop.SlideFromBottomPopupMap
import com.exz.firecontrol.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.GPSUtil
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_unit_detail.*
import java.net.URISyntaxException

/**
 * Created by pc on 2017/12/19.
 */

class UnitDetailActivity : BaseActivity() {
    private lateinit var mAdapter: UnitDetailAdapter
    override fun initToolbar(): Boolean {
        mTitle.text = "单位详情"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 10f)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_unit_detail
    }

    var lon=0.toDouble()
    var lat=0.toDouble()
    override fun init() {
        lon=intent.getDoubleExtra(Intent_UnitDetailActivity_lon,0.toDouble())
        lat=intent.getDoubleExtra(Intent_UnitDetailActivity_lat,0.toDouble())
        initView()
        initRecycler()
    }

    private fun initView() {
        popupMap = SlideFromBottomPopupMap(this)
        popupMap.setOnClickListener({ v ->
            var intent = Intent()
            when (v.id) {
                R.id.tx_1 -> if (AppUtils.isInstallApp("com.baidu.BaiduMap")) {
                    try {
                        val gcj02_to_bd09 = GPSUtil.gcj02_To_Bd09(lon.toDouble(), lat.toDouble())
                        intent = Intent.parseUri("intent://map/direction?" +
                                //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                                "destination=latlng:" + gcj02_to_bd09[0] + "," +
                                gcj02_to_bd09[1] + "|name:我的目的地" +        //终点

                                "&mode=driving&" +          //导航路线方式

                                "region=" +           //

                                "&src=Name|AppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0)
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }

                    startActivity(intent)
                } else {
                    //market为路径，id为包名
                    //显示手机上所有的market商店
                    Toast.makeText(mContext, "您尚未安装百度地图", Toast.LENGTH_LONG).show()
                    val uri = Uri.parse("market://details?id=com.baidu.BaiduMap")
                    intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                R.id.tx_2 -> if (AppUtils.isInstallApp("com.autonavi.minimap")) {
                    try {
                        intent.data = Uri
                                .parse("androidamap://route?" +
                                        "sourceApplication=softname" +
                                        //                                                "&slat=" + (locationEntity == null ? "" : locationEntity.getLatitude()) +
                                        //                                                "&slon=" + (locationEntity == null ? "" : locationEntity.getLongitude()) +
                                        "&dlat=" + lat +
                                        "&dlon=" + lon +
                                        //                                            "&dname=" + mStore.getAddress() +
                                        "&dev=0" +
                                        "&m=0" +
                                        "&t=2")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    startActivity(intent)
                } else {
                    Toast.makeText(mContext, "您尚未安装高德地图", Toast.LENGTH_LONG).show()
                    val uri = Uri.parse("market://details?id=com.autonavi.minimap")
                    intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            popupMap.dismiss()
        })
    }

    private var data = ArrayList<UnitDetailBean>()
    private fun initRecycler() {
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_1), "单位基本信息"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_2), "消防基本信息"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_3), "图纸资料"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_4), "救援预案"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_5), "消防水源"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_6), "地图导航"))
        mAdapter = UnitDetailAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                    when (mAdapter.data[position].name) {
                        "单位基本信息" -> {
                            startActivity(Intent(mContext, InfoActivity::class.java).putExtra(Intent_getEnterPrise_id,intent.getStringExtra(Intent_getEnterPrise_id)?:"").putExtra(InfoActivity.Intent_Class_Name, mAdapter.data[position].name))
                        }
                        "消防基本信息" -> {
                            startActivity(Intent(mContext, InfoActivity::class.java).putExtra(Intent_getEnterPrise_id,intent.getStringExtra(Intent_getEnterPrise_id)?:"").putExtra(InfoActivity.Intent_Class_Name, mAdapter.data[position].name))
                        }
                        "图纸资料" -> {
                            startActivity(Intent(mContext, DrawingsActivity::class.java).putExtra(Intent_getDrawFileList_id,intent.getStringExtra(Intent_getEnterPrise_id)?:""))
                        }
                        "救援预案" -> {
                            startActivity(Intent(mContext, EnterPriseDataActivity::class.java).putExtra(Intent_EnterPriseDataActivity_id,intent.getStringExtra(Intent_getEnterPrise_id)?:""))
                        }
                        "消防水源" -> {
                            startActivity(Intent(mContext, FirewaterSupplyActivity::class.java).
                                    putExtra(Intent_FireWater_comId,intent.getStringExtra(Intent_getEnterPrise_id)?:"").putExtra(Intent_FireWater_lon,lon).putExtra(Intent_FireWater_lat,lat))
                        }
                        "地图导航" -> {
                            popupMap.showPopupWindow()
                        }
                    }

            }
        })
    }

    private lateinit var popupMap: SlideFromBottomPopupMap
    companion object {

        var Intent_UnitDetailActivity_lon = "Intent_UnitDetailActivity_lon"
        var Intent_UnitDetailActivity_lat = "Intent_UnitDetailActivity_lat"
    }
}
