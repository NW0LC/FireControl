package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.FirefightingAdapter
import com.exz.firecontrol.bean.OrganizationBean
import com.exz.firecontrol.module.firefighting.FireBrigadeActivity.Companion.Intent_Class_Name
import com.exz.firecontrol.module.firefighting.FireBrigadeActivity.Companion.Intent_Lower_oid
import com.exz.firecontrol.module.firefighting.FireDepartmentDetailActivity.Companion.Intent_getOrgDetailById_Id
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_fire_department.*

/**
 * Created by pc on 2017/12/19.
 * 消防机构
 */

class FireDepartmentActivity : BaseActivity(), OnRefreshListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: FirefightingAdapter<OrganizationBean>
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
        toolbar.inflateMenu(R.menu.menu_person)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "微型消防站"
        actionView.movementMethod = ScrollingMovementMethod.getInstance()
        actionView.setOnClickListener {
           startActivity(intent.setClass(mContext, MFSActivity::class.java))
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_fire_department
    }

    override fun init() {
        initRecycler()
        refreshLayout.autoRefresh()
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
                        startActivity(Intent(mContext, FireDepartmentDetailActivity::class.java).putExtra(Intent_Class_Name, mAdapter.data[position].cname).putExtra(Intent_getOrgDetailById_Id,mAdapter.data[position].id.toString()))
                    }
                    R.id.tv_more -> {
                        startActivity(Intent(mContext, FireBrigadeActivity::class.java).putExtra(Intent_Class_Name, mAdapter.data[position].cname).putExtra(Intent_Lower_oid,mAdapter.data[position].id))
                    }
                }
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        bt_search.setOnClickListener(this)
    }


    override fun onClick(p0: View) {
        when (p0) {
            bt_search -> {//搜索
                startActivity(Intent(mContext, SearchFireBrigadeActivity::class.java))
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 0
        refreshState = Constants.RefreshState.STATE_REFRESH
        iniData()
    }


    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.findLowerLevel(this, "1", currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.lowerOrgList)
                } else {
                    mAdapter.addData(it.lowerOrgList ?: ArrayList())
                }
                if (it.lowerOrgList?.isNotEmpty() == true) {
                    mAdapter.loadMoreComplete()
                    currentPage=mAdapter.data.size
                } else {
                    mAdapter.loadMoreEnd()
                }
            } else {
                mAdapter.loadMoreFail()
            }
        }
    }
}
