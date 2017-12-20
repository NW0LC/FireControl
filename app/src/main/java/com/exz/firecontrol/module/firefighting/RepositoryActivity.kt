package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.RepositoryAdapter
import com.exz.firecontrol.bean.RepositoryBean
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_repository.*

/**
 * Created by pc on 2017/12/20.
 */

class RepositoryActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {


    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: RepositoryAdapter
    override fun initToolbar(): Boolean {
        mTitle.text = "消防知识库"
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
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_repository
    }

    override fun init() {
        super.init()
        initView()
        initRecycler()
    }

    private fun initView() {
        bt_search.setOnClickListener(this)
    }

    private var data = ArrayList<RepositoryBean>()
    private fun initRecycler() {
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        data.add(RepositoryBean())
        mAdapter = RepositoryAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, RepositoryCategoryActivity::class.java).putExtra(RepositoryCategoryActivity.Intent_Class_Name,"类别一"))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        refreshLayout.autoRefresh()
    }
    override fun onClick(p0: View?) {
        startActivity(Intent(mContext, RepositoryCategoryActivity::class.java).putExtra(RepositoryCategoryActivity.Intent_Class_Name,"消防知识库"))
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
