package com.exz.firecontrol.module.person

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleDetailAdapter
import com.exz.firecontrol.bean.VehicleDetailBean
import com.exz.firecontrol.module.MapLocationActivity
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Latitude
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Longitude
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.header_person_detail.view.*

/**
 * Created by pc on 2017/12/20.
 * 人员详情
 */

class PersonDetailActivity : BaseActivity(), Toolbar.OnMenuItemClickListener {


    private lateinit var mHeaderView: View
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
        mTitle.text = "人员详情"

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_person_detail
    }

    override fun init() {
        super.init()
        initRecycler()
        initView()
    }

    private fun initView() {
        buttonBarLayout.alpha = 0f
        blurView.alpha = 0f
        toolbar.inflateMenu(R.menu.menu_vehicle_detail)
        toolbar.setOnMenuItemClickListener(this)
        mHeaderView = View.inflate(mContext, R.layout.header_person_detail, null)
        mAdapter.addHeaderView(mHeaderView)
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
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.map -> {
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(Intent_Longitude, lon).putExtra(Intent_Latitude, lat).putExtra(MapLocationActivity.Intent_Class_Name, "人员位置"))
            }
        }
        return false
    }

    private var data1 = ArrayList<VehicleDetailBean>()
    private var lon = ""
    private var lat = ""
    private fun initRecycler() {
        DataCtrlClass.getFireManById(this, intent.getStringExtra(Intent_PersonDetail_Id)?:"") {
            if (it != null) {
                mHeaderView.img_head.setImageURI(it.FireManInfo?.userHead ?: "")
                mHeaderView.tv_name.text = it.FireManInfo?.Name ?: ""
                mHeaderView.tv_info.text = String.format(getString(R.string.police_officer), (it.FireManInfo?.PCNumber ?: "")+(it.FireManInfo?.RoleName ?: ""))
                mHeaderView.tv_is_online.delegate.backgroundColor = ContextCompat.getColor(mContext, if (it.FireManInfo?.IsOnline == 1) R.color.MaterialGreenA400 else R.color.MaterialGrey400)
                mHeaderView.tv_is_online.text = if (it.FireManInfo?.IsOnline == 1) getString(R.string.on_line) else getString(R.string.off_line)
//                data1.add(VehicleDetailBean("队伍", it.FireManInfo?.RoleName?:""))
                data1.add(VehicleDetailBean("设备编号", it.FireManInfo?.deviceCode ?: ""))
                data1.add(VehicleDetailBean("电话", it.FireManInfo?.telephone ?: ""))
                data1.add(VehicleDetailBean("GPS时间", it.FireManInfo?.GPSDate ?: ""))
                data1.add(VehicleDetailBean("朝向", it.FireManInfo?.direction ?: ""))
                data1.add(VehicleDetailBean("行走速度", it.FireManInfo?.speed ?: ""))
                data1.add(VehicleDetailBean("高温报警", it.FireManInfo?.Temp ?: ""))
                data1.add(VehicleDetailBean("倒地报警", it.FireManInfo?.falldwon ?: ""))
                data1.add(VehicleDetailBean("静止报警", it.FireManInfo?.staticflag ?: ""))
                data1.add(VehicleDetailBean("运动状态", it.FireManInfo?.flag ?: ""))
                data1.add(VehicleDetailBean("手机电量", it.FireManInfo?.Battery ?: ""))
                data1.add(VehicleDetailBean("经度", it.FireManInfo?.lon ?: ""))
                data1.add(VehicleDetailBean("纬度", it.FireManInfo?.lat ?: ""))
                data1.add(VehicleDetailBean("高度", it.FireManInfo?.height ?: ""))
                mAdapter.setNewData(data1)
            }

        }


        mAdapter = VehicleDetailAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext!!, R.color.app_bg)))
    }

    companion object {
        var Intent_PersonDetail_Id = "Intent_PersonDetail_Id"
    }

}