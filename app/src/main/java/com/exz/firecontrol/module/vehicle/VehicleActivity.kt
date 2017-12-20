package com.exz.firecontrol.module.vehicle

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.VehicleAdapter
import com.exz.firecontrol.bean.StairBean
import com.exz.firecontrol.bean.VehicleBean
import com.exz.firecontrol.pop.StairPop
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_vehicle.*

/**
 * Created by pc on 2017/12/20.
 * 消防车辆
 */

class VehicleActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private lateinit var mPopCarState: StairPop//车辆类型
    private lateinit var mPopCarType: StairPop//车辆状态
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: VehicleAdapter
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        edTitle.hint = "搜索车辆编号"
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_vehicle
    }

    override fun init() {
        super.init()
        initView()
        initRecycler()
    }
    var dataType = ArrayList<StairBean>()
    private fun initView() {
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        mPopCarState = StairPop(mContext, {})
        mPopCarType = StairPop(mContext, {})
        mPopCarType.data=dataType
        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
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
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        refreshLayout.autoRefresh()
    }

    override fun onClick(p0: View) {
        when (p0) {
            rb1 -> {

            }
            rb2 -> {
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        refreshLayout?.finishRefresh()
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH

    }

    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
    }
}
