package com.exz.firecontrol.module

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.DisasterAdapter
import com.exz.firecontrol.bean.DisasterBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnRefreshListener {

    private lateinit var mAdapter:DisasterAdapter
    private lateinit var headerView: View
    override fun setInflateId(): Int {
     return R.layout.activity_main
    }

    override fun initToolbar(): Boolean {
        mTitle.text = "首页"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        toolbar.setNavigationIcon(ContextCompat.getDrawable(mContext,R.mipmap.icon_uerinfo))
        //状态栏透明和间距处理
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
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
    }

}
