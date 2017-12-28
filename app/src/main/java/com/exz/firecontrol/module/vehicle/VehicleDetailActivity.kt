package com.exz.firecontrol.module.vehicle

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.amap.api.maps.model.LatLng
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleDetailAdapter
import com.exz.firecontrol.bean.TabEntity
import com.exz.firecontrol.bean.VehicleDetailBean
import com.exz.firecontrol.module.MapLocationActivity
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Lat
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_vehicle_detail.*
import kotlinx.android.synthetic.main.header_veicle_detail.view.*

/**
 * Created by pc on 2017/12/20.
 * 消防车辆
 */

class VehicleDetailActivity : BaseActivity(), Toolbar.OnMenuItemClickListener {


    private lateinit var mHeaderView:View
    private var mScrollY = 0
    private lateinit var mAdapter: VehicleDetailAdapter
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        mTitle.text = "车辆详情"
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_vehicle_detail
    }

    override fun init() {
        initRecycler()
        initView()
    }
    private val mTitles = arrayOf("底盘", "上装","器材信息")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher,   R.mipmap.ic_launcher,R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private fun initView() {
        buttonBarLayout.alpha = 0f
        blurView.alpha = 0f
        toolbar.inflateMenu(R.menu.menu_vehicle_detail)
        toolbar.setOnMenuItemClickListener(this)
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mHeaderView=View.inflate(mContext,R.layout.header_veicle_detail,null)
        mAdapter.addHeaderView(mHeaderView)
        mHeaderView.mainTabBar.setTabData(mTabEntities)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val h = DensityUtil.dp2px(170f)
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollY += dy
                if (mScrollY < h) {
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    blurView.alpha = 1f * mScrollY / h
                }

            }
        })
        mHeaderView.mainTabBar.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabReselect(position: Int) {
            }

            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> {
                        mAdapter.setNewData(data1)
                    }
                    1 -> {
                        mAdapter.setNewData(data2)
                    }
                    2 -> {
                        mAdapter.setNewData(data3)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.map -> {
                val intent = Intent(mContext, MapLocationActivity::class.java)
                val latLngList = ArrayList<LatLng>()
                latLngList.add(LatLng(lat.toDouble(), lon.toDouble()))
                intent.putExtra(Intent_Lat, latLngList).putExtra(MapLocationActivity.Intent_Class_Name,"车辆位置")
                startActivity(intent)
            }
        }
        return false
    }

    private var data1 = ArrayList<VehicleDetailBean>()
    private var data2 = ArrayList<VehicleDetailBean>()
    private var data3 = ArrayList<VehicleDetailBean>()
    private var lon=""
    private var lat=""
    private fun initRecycler() {
        DataCtrlClass.getFireCarById(this,intent.getStringExtra(Intent_VehicleDetail_Id)?:""){
            if (it!=null){
                val fireCarLocBean = it.fireCar?.get(0)
                lon=fireCarLocBean?.lon?:""
                lat=fireCarLocBean?.lat?:""
                mHeaderView.tv_is_online.delegate.backgroundColor=ContextCompat.getColor(mContext,if (fireCarLocBean?.IsOnline=="1")R.color.MaterialGreenA400 else R.color.MaterialGrey400)
                mHeaderView.tv_is_online.text=if (fireCarLocBean?.IsOnline=="1")getString(R.string.on_line) else getString(R.string.off_line)


                data1.add(VehicleDetailBean("车牌号", fireCarLocBean?.carNum?:""))
                data1.add(VehicleDetailBean("生产厂家",fireCarLocBean?.manufacturer?:""))
                data1.add(VehicleDetailBean("生产日期",fireCarLocBean?.manufactureDate?:""))
                data1.add(VehicleDetailBean("保养",fireCarLocBean?.maintainState?:""))
                data1.add(VehicleDetailBean("所属单位",fireCarLocBean?.RoleName?:""))
                data1.add(VehicleDetailBean("GPS时间",fireCarLocBean?.GPSDate?:""))
                data1.add(VehicleDetailBean("档位",fireCarLocBean?.carLevel?:""))
                data1.add(VehicleDetailBean("发动机转速",fireCarLocBean?.speed?:""))
                data1.add(VehicleDetailBean("气压",fireCarLocBean?.airPRESS?:""))
                data1.add(VehicleDetailBean("电瓶电压",fireCarLocBean?.voltage?:""))
                data1.add(VehicleDetailBean("速度",fireCarLocBean?.carspeed?:""))
                data1.add(VehicleDetailBean("水温",fireCarLocBean?.waterTemp?:""))
                data1.add(VehicleDetailBean("油量",fireCarLocBean?.oilmass?:""))
                data1.add(VehicleDetailBean("油压",fireCarLocBean?.enginePRESS?:""))
                data1.add(VehicleDetailBean("总里程",fireCarLocBean?.totalKM?:""))
                data1.add(VehicleDetailBean("剩余油量",fireCarLocBean?.overOilmass?:""))
                data1.add(VehicleDetailBean("驻车",fireCarLocBean?.inCar?:""))

                data2.add(VehicleDetailBean("水泵转速",fireCarLocBean?.pumpSpeed?:""))
                data2.add(VehicleDetailBean("水泵出口压力",fireCarLocBean?.pumpOutPressure?:""))
                data2.add(VehicleDetailBean("水泵进口压力",fireCarLocBean?.pumpInPressure?:""))
                data2.add(VehicleDetailBean("水位",fireCarLocBean?.waterLevel?:""))
                data2.add(VehicleDetailBean("a液位",fireCarLocBean?.aVolume?:""))
                data2.add(VehicleDetailBean("b液位",fireCarLocBean?.bVolume?:""))
                data2.add(VehicleDetailBean("取力器结合",fireCarLocBean?.PTO?:""))
                data2.add(VehicleDetailBean("水泵累计工作时间",fireCarLocBean?.pumpWorkTime?:""))

                data3.add(VehicleDetailBean("直流水枪",fireCarLocBean?.waterGun?:""))
                data3.add(VehicleDetailBean("导流式直流喷雾水枪",fireCarLocBean?.sprayGun?:""))
                data3.add(VehicleDetailBean("泡沫枪",fireCarLocBean?.foamGun?:""))
                data3.add(VehicleDetailBean("泡沫外吸管扳手",fireCarLocBean?.foamSpanner?:""))
                data3.add(VehicleDetailBean("集水器",fireCarLocBean?.catchMent?:""))
                data3.add(VehicleDetailBean("分水器",fireCarLocBean?.waterTrap?:""))
                data3.add(VehicleDetailBean("吸水管扳手",fireCarLocBean?.spanner?:""))
                data3.add(VehicleDetailBean("橡皮锤",fireCarLocBean?.rubberHammer?:""))
                data3.add(VehicleDetailBean("地上消火栓扳手",fireCarLocBean?.upSpanner?:""))
                data3.add(VehicleDetailBean("地下消火栓扳手",fireCarLocBean?.belowSpanner?:""))
                data3.add(VehicleDetailBean("消防梯",fireCarLocBean?.fireLadder?:""))
                data3.add(VehicleDetailBean("异径接口",fireCarLocBean?.diffPort?:""))
                data3.add(VehicleDetailBean("护带桥",fireCarLocBean?.protectBridge?:""))
                data3.add(VehicleDetailBean("水带包布",fireCarLocBean?.hoseBandage?:""))
                data3.add(VehicleDetailBean("水带挂钩",fireCarLocBean?.hoseHook?:""))
                data3.add(VehicleDetailBean("消防斧",fireCarLocBean?.fireAx?:""))
                data3.add(VehicleDetailBean("可充电式手提照明灯",fireCarLocBean?.lights?:""))
                data3.add(VehicleDetailBean("手抬泵",fireCarLocBean?.handPump?:""))
                data3.add(VehicleDetailBean("消防吸水管",fireCarLocBean?.suctionPipe?:""))
                data3.add(VehicleDetailBean("吸水管滤水器",fireCarLocBean?.waterFilter?:""))
                mAdapter.setNewData(data1)
            }
        }



        mAdapter = VehicleDetailAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            }
        })
    }
    companion object {
        var Intent_VehicleDetail_Id="Intent_VehicleDetail_Id"
    }

}