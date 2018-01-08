package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.FirefightingAdapter
import com.exz.firecontrol.bean.OrganizationBean
import com.exz.firecontrol.module.firefighting.FireBrigadeActivity.Companion.Intent_Class_Name
import com.exz.firecontrol.module.firefighting.FireDepartmentDetailActivity.Companion.Intent_getOrgDetailById_Id
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_search_frie_brigade.*

/**
 * Created by pc on 2017/12/19.
 * 搜索大队
 */

class SearchFireBrigadeActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: FirefightingAdapter<OrganizationBean>
    private var searchContent=""
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
        SZWUtils.setRefreshAndHeaderCtrl(this,header,refreshLayout)
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_search_frie_brigade
    }

    override fun init() {
        super.init()
        initView()
        initRecycler()
    }

    private fun initView() {
        edTitle.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // do something
                searchContent = edTitle.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(searchContent)) {
                    onRefresh(refreshLayout)
                }
                return@OnEditorActionListener true
            }
            false
        })
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
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.getOrgListByPage(this,  searchContent, currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.OrganizationList)
                } else {
                    mAdapter.addData(it.OrganizationList ?: ArrayList())
                }
                if (it.OrganizationList?.isNotEmpty() == true) {
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