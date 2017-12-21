package com.exz.firecontrol.module.person

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
import com.exz.firecontrol.bean.VehicleDetailBean
import com.exz.firecontrol.module.MapLocationActivity
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_person_detail.*

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
        StatusBarUtil.immersive(this, ContextCompat.getColor(mContext, R.color.colorPrimary_trans))
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
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
        mHeaderView= View.inflate(mContext, R.layout.header_person_detail, null)
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
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(MapLocationActivity.Intent_Class_Name,"人员位置"))
            }
        }
        return false
    }

    private var data1 = ArrayList<VehicleDetailBean>()
    private fun initRecycler() {
        data1.add(VehicleDetailBean("警员号", "MSKLJKSSKLG"))
        data1.add(VehicleDetailBean("队伍", "用桥大队"))
        data1.add(VehicleDetailBean("设备编号", "154646313"))
        data1.add(VehicleDetailBean("电话", "1"))
        data1.add(VehicleDetailBean("GPS时间", "2017-6-12 12:30"))
        data1.add(VehicleDetailBean("朝向", "2093.00"))
        data1.add(VehicleDetailBean("行走速度", "-1"))
        data1.add(VehicleDetailBean("高温报警", "0"))
        data1.add(VehicleDetailBean("倒地报警", "255"))
        data1.add(VehicleDetailBean("静止报警", "255"))
        data1.add(VehicleDetailBean("运动状态", "255"))
        data1.add(VehicleDetailBean("手机电量", "85.000"))
        data1.add(VehicleDetailBean("经度", "117.01654"))
        data1.add(VehicleDetailBean("纬度", "34.04154"))
        data1.add(VehicleDetailBean("高度", "0.000000"))

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