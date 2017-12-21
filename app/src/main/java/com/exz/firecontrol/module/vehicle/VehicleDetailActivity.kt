package com.exz.firecontrol.module.vehicle

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleDetailAdapter
import com.exz.firecontrol.bean.TabEntity
import com.exz.firecontrol.bean.VehicleDetailBean
import com.exz.firecontrol.module.MapLocationActivity
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
        StatusBarUtil.immersive(this,ContextCompat.getColor(mContext,R.color.colorPrimary_trans))
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
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
        super.init()
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
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(MapLocationActivity.Intent_Class_Name,"车辆位置"))
            }
        }
        return false
    }

    private var data1 = ArrayList<VehicleDetailBean>()
    private var data2 = ArrayList<VehicleDetailBean>()
    private var data3 = ArrayList<VehicleDetailBean>()
    private fun initRecycler() {
        data1.add(VehicleDetailBean("车辆编号","MSKLJKSSKLG"))
        data1.add(VehicleDetailBean("所属单位","徐州亿网"))
        data1.add(VehicleDetailBean("GPS时间","2017-8-12 16:50"))
        data1.add(VehicleDetailBean("档位","1"))
        data1.add(VehicleDetailBean("发动机转速",""))
        data1.add(VehicleDetailBean("气压",""))
        data1.add(VehicleDetailBean("电瓶气压",""))
        data1.add(VehicleDetailBean("速度",""))
        data1.add(VehicleDetailBean("水温",""))
        data1.add(VehicleDetailBean("油量",""))
        data1.add(VehicleDetailBean("油压",""))
        data1.add(VehicleDetailBean("总里程",""))
        data1.add(VehicleDetailBean("剩余油量",""))
        data1.add(VehicleDetailBean("驻车",""))

        data2.add(VehicleDetailBean("水泵转速","0.0rpm"))
        data2.add(VehicleDetailBean("水泵出口压力","0.0MPa"))
        data2.add(VehicleDetailBean("水泵进口压力","0.0Mpa"))
        data2.add(VehicleDetailBean("水位","0.0%"))
        data2.add(VehicleDetailBean("a液位","1.0%"))
        data2.add(VehicleDetailBean("b液位","0.0%"))
        data2.add(VehicleDetailBean("取力器结合","否"))
        data2.add(VehicleDetailBean("水泵累计工作时间",""))

        data3.add(VehicleDetailBean("直流水枪","0次"))
        data3.add(VehicleDetailBean("导流式直流喷雾水枪","0次"))
        data3.add(VehicleDetailBean("泡沫枪","0次"))
        data3.add(VehicleDetailBean("泡沫外吸管扳手","0次"))
        data3.add(VehicleDetailBean("集水器","0次"))
        data3.add(VehicleDetailBean("分水器","0次"))
        data3.add(VehicleDetailBean("吸水管扳手","0次"))
        data3.add(VehicleDetailBean("橡皮锤","0次"))
        data3.add(VehicleDetailBean("地上消火栓扳手","0次"))
        data3.add(VehicleDetailBean("地下消火栓扳手","0次"))
        data3.add(VehicleDetailBean("消防梯","0次"))
        data3.add(VehicleDetailBean("异径接口","0次"))
        data3.add(VehicleDetailBean("护带桥","0次"))
        data3.add(VehicleDetailBean("水带包布","0次"))
        data3.add(VehicleDetailBean("水带挂钩","0次"))
        data3.add(VehicleDetailBean("消防斧","0次"))
        data3.add(VehicleDetailBean("可充电式手提照明灯","0次"))
        data3.add(VehicleDetailBean("手抬泵","0次"))
        data3.add(VehicleDetailBean("消防吸水管","0次"))
        data3.add(VehicleDetailBean("吸水管滤水器","0次"))


        mAdapter = VehicleDetailAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data1)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            }
        })
    }


}