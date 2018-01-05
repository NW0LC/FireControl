package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.RepositoryAdapter
import com.exz.firecontrol.bean.KnowledgeCategoryListBean
import com.exz.firecontrol.module.firefighting.RepositoryCategoryActivity.Companion.Intent_RepositoryCategoryActivity_id
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

class RepositoryActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private var searchContent=""
    private lateinit var mAdapter: RepositoryAdapter<KnowledgeCategoryListBean.CategoryListBean>
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
        SZWUtils.setRefreshAndHeaderCtrl(this,header,refreshLayout)
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
        bt_search.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // do something
                searchContent = bt_search.text.toString().trim { it <= ' ' }
                onRefresh(refreshLayout)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initRecycler() {
        mAdapter = RepositoryAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, RepositoryCategoryActivity::class.java).putExtra(RepositoryCategoryActivity.Intent_Class_Name,mAdapter.data[position].categoryName).putExtra(Intent_RepositoryCategoryActivity_id,mAdapter.data[position].id.toString()))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        refreshLayout.autoRefresh()
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
        DataCtrlClass.getKnowledgeCategoryList(this, searchContent,currentPage= currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.categoryList)
                } else {
                    mAdapter.addData(it.categoryList ?: ArrayList())
                }
                if (it.categoryList?.isNotEmpty() == true) {
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
