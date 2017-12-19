package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.FirefightingAdapter
import com.exz.firecontrol.bean.FirefightingBean
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_fire_department.*
import kotlinx.android.synthetic.main.header_firefighting.view.*
import kotlinx.android.synthetic.main.lay_firefighting.view.*

/**
 * Created by pc on 2017/12/19.
 * 消防机构
 */

class FireDepartmentActivity : BaseActivity(), OnRefreshListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: FirefightingAdapter
    private lateinit var headerView: View
    override fun initToolbar(): Boolean {
        mTitle.text = "消防机构"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_fire_department
    }

    override fun init() {
        super.init()
        initRecycler()
        initHeader()
    }


    private var data = ArrayList<FirefightingBean>()
    private fun initRecycler() {
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        data.add(FirefightingBean())
        mAdapter = FirefightingAdapter(1)
        headerView = View.inflate(mContext, R.layout.header_firefighting, null)
        mAdapter.addHeaderView(headerView)
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object :OnItemChildClickListener(){
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                when (view.id) {
                    R.id.tv_see_details  -> {
                         startActivity(Intent(mContext, FireDepartmentDetailActivity::class.java).putExtra(FireDepartmentDetailActivity.Intent_Type, "2"))
                     }
                }
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        bt_search.setOnClickListener(this)
    }

    private fun initHeader() {
        headerView.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_detachment)
        headerView.rlLay.setOnClickListener(this)
        headerView.tv_more.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            bt_search->{//搜索
                startActivity(Intent(mContext,SearchFireBrigadeActivity::class.java))
            }
            headerView.rlLay -> {//机构详情
                startActivity(Intent(mContext, FireDepartmentDetailActivity::class.java).putExtra(FireDepartmentDetailActivity.Intent_Type, "1"))
            }
            headerView.tv_more -> {//消防队
                startActivity(Intent(mContext, FireBrigadeActivity::class.java).putExtra(FireBrigadeActivity.Intent_Class_Name, "消防大队"))
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH

    }

    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
    }
}
