package com.exz.firecontrol.module

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.DisasterAdapter
import com.exz.firecontrol.bean.DisasterBean
import com.exz.firecontrol.module.firefighting.FireDepartmentActivity
import com.exz.firecontrol.module.unit.KeyUnitActivity
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_main.view.*

class MainActivity : BaseActivity(), OnRefreshListener, View.OnClickListener {

    private lateinit var mAdapter: DisasterAdapter
    private lateinit var headerView: View
    override fun setInflateId(): Int {
        return R.layout.activity_main
    }

    override fun initToolbar(): Boolean {
        mTitle.text = "首页"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        toolbar.navigationIcon = ContextCompat.getDrawable(mContext, R.mipmap.icon_uerinfo)
        //状态栏透明和间距处理
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {

        }
        return false
    }

    override fun init() {
        super.init()
        initRecycler()
        initHeader()
    }


    private var data = ArrayList<DisasterBean>()
    private fun initRecycler() {
        data.add(DisasterBean())
        data.add(DisasterBean())
        data.add(DisasterBean())
        data.add(DisasterBean())
        data.add(DisasterBean())
        data.add(DisasterBean())
        data.add(DisasterBean())
        data.add(DisasterBean())
        mAdapter = DisasterAdapter()
        headerView = View.inflate(mContext, R.layout.header_main, null)
        mAdapter.addHeaderView(headerView)
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
    }

    private fun initHeader() {

        headerView.bt_tab_1.setOnClickListener(this)
        headerView.bt_tab_2.setOnClickListener(this)
        headerView.bt_tab_3.setOnClickListener(this)
        headerView.bt_tab_4.setOnClickListener(this)
        headerView.bt_tab_5.setOnClickListener(this)
        headerView.bt_tab_6.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            headerView.bt_tab_1 -> {
                startActivity(Intent(mContext, FireDepartmentActivity::class.java))
            }
            headerView.bt_tab_2 -> {
                startActivity(Intent(mContext, KeyUnitActivity::class.java))
            }
            headerView.bt_tab_3 -> {
            }
            headerView.bt_tab_4 -> {
            }
            headerView.bt_tab_5 -> {
            }
            headerView.bt_tab_6 -> {
            }
        }
    }


    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
    }

}
