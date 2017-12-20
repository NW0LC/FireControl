package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.FirefightingAdapter
import com.exz.firecontrol.bean.OrganizationBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by pc on 2017/12/19.
 * 消防大队
 */

class FireBrigadeActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: FirefightingAdapter<OrganizationBean>
    override fun initToolbar(): Boolean {
        mTitle.text = intent.getStringExtra(Intent_Class_Name)
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

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_frie_brigade
    }

    override fun init() {
        initView()
        refreshLayout.autoRefresh()
    }

    private fun initView() {
        initRecycler()
    }
    private fun initRecycler() {
        mAdapter = FirefightingAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                when (view.id) {
                    R.id.tv_see_details -> {
                        startActivity(Intent(mContext, FireDepartmentDetailActivity::class.java).putExtra(FireDepartmentDetailActivity.Intent_Type, "1"))
                    }
                    R.id.tv_more->{
                        startActivity(Intent(mContext, FireBrigadeActivity::class.java).putExtra(Intent_Class_Name, mAdapter.data[position].cname).putExtra(Intent_Lower_oid,mAdapter.data[position].id))
                    }
                }
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
    }
    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 0
        refreshState = Constants.RefreshState.STATE_REFRESH
        iniData()
    }


    override fun onLoadMoreRequested() {
        currentPage = mAdapter.data.size-1
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.findLowerLevel(this, intent.getStringExtra(Intent_Lower_oid)?:"", currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.lowerOrgList)
                } else {
                    mAdapter.addData(it.lowerOrgList ?: ArrayList())
                }
                if (it.lowerOrgList?.isNotEmpty() == true) {
                    mAdapter.loadMoreComplete()
                    currentPage++
                } else {
                    mAdapter.loadMoreEnd()
                }
            } else {
                mAdapter.loadMoreFail()
            }
        }
    }
    companion object {
        var Intent_Lower_oid="Intent_Lower_oid"
        var Intent_Class_Name="ClassName"
    }
}
