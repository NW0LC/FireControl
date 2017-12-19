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
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.FirefightingAdapter
import com.exz.firecontrol.bean.FirefightingBean
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_search_frie_brigade.*
import java.util.*

/**
 * Created by pc on 2017/12/19.
 * 搜索大队
 */

class SearchFireBrigadeActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: FirefightingAdapter
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
                val searchContent = edTitle.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(searchContent)) {

                }
                return@OnEditorActionListener true
            }
            false
        })
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
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                when (view.id) {
                    R.id.tv_see_details -> {
                        startActivity(Intent(mContext, FireDepartmentDetailActivity::class.java).putExtra(FireDepartmentDetailActivity.Intent_Type, "1"))
                    }
                }
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH

    }

    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
    }

}