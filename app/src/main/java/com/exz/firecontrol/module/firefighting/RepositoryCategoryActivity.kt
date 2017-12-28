package com.exz.firecontrol.module.firefighting

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.CategoryAdapter
import com.exz.firecontrol.bean.KnowledgeInfoListBean
import com.exz.firecontrol.pop.SchemePop
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
 * 知识库类别
 */

class RepositoryCategoryActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mPop: SchemePop
    private lateinit var mAdapter: CategoryAdapter<KnowledgeInfoListBean.KnowledgeListBean>
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
        return R.layout.activity_repository_category
    }

    override fun init() {
        initView()
        initRecycler()
        mPop = SchemePop(mContext)

    }

    private fun initView() {
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        refreshLayout.autoRefresh()
    }

    private fun initRecycler() {
        mAdapter = CategoryAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                mPop.data=mAdapter.data[position].content?:""
                mPop.title=mAdapter.data[position].title?:""
                mPop.showPopupWindow()
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
        currentPage = mAdapter.data.size
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.getKnowledgeInfoList(this, intent.getStringExtra(Intent_RepositoryCategoryActivity_id)?:"",currentPage= currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.knowledgeList)
                } else {
                    mAdapter.addData(it.knowledgeList ?: ArrayList())
                }
                if (it.knowledgeList?.isNotEmpty() == true) {
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
        var Intent_Class_Name = "ClassName"
        var Intent_RepositoryCategoryActivity_id = "Intent_RepositoryCategoryActivity_id"
    }
}
