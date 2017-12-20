package com.exz.firecontrol.module.vehicle

import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleAdapter
import com.exz.firecontrol.bean.TabEntity
import com.exz.firecontrol.bean.VehicleBean
import com.flyco.tablayout.listener.CustomTabEntity
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_vehicle_detail.*

/**
 * Created by pc on 2017/12/20.
 * 消防车辆
 */

class VehicleDetailActivity : BaseActivity() {
    private var mScrollY = 0
    private lateinit var mAdapter: VehicleAdapter
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mScrollView)
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
        initView()
        initRecycler()
    }
    private val mTitles = arrayOf("底盘", "上装","器材信息")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher,   R.mipmap.ic_launcher,R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private fun initView() {
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mainTabBar.setTabData(mTabEntities)

        mScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = DensityUtil.dp2px(170f)
            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                var scrollNewY = scrollY
                if (lastScrollY < h) {
                    scrollNewY = Math.min(h, scrollNewY)
                    mScrollY = if (scrollNewY > h) h else scrollNewY
                    blurView.alpha = 1f * mScrollY / h
                    buttonBarLayout.alpha = 1f * mScrollY / h
                }
                lastScrollY = scrollNewY

            }
        })
        buttonBarLayout.alpha = 0f
        blurView.alpha = 0f
    }


    private var data = ArrayList<VehicleBean>()
    private fun initRecycler() {
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        data.add(VehicleBean())
        mAdapter = VehicleAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            }
        })
    }


}