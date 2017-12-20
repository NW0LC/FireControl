package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.SearchUnitAdapter
import com.exz.firecontrol.bean.InfoBean
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
 * Created by pc on 2017/12/20.
 * 搜索单位
 */

class SearchUnitActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter:SearchUnitAdapter
    override fun initToolbar(): Boolean {
        edTitle.setHint("搜索单位名称")
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
        return R.layout.activity_serach_unit
    }

    override fun init() {
        super.init()
        initRecycler()
        
    }
    private var data = ArrayList<InfoBean>()
    private fun initRecycler() {
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
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        data.add(InfoBean("中国石化徐州分公司",""))
        mAdapter = SearchUnitAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, InfoActivity::class.java).putExtra(InfoActivity.Intent_Class_Name, "单位基本信息"))

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
