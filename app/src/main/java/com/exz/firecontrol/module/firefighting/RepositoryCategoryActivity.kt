package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.CategoryAdapter
import com.exz.firecontrol.bean.CategoryBean
import com.exz.firecontrol.utils.SZWUtils
import com.exz.firecontrol.widget.MyWebActivity
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
    private var currentPage = 1

    private lateinit var mAdapter: CategoryAdapter
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
        if (intent.getStringExtra(Intent_Class_Name).equals("消防知识库")) {
            mTitle.visibility = View.GONE
            edTitle.visibility = View.VISIBLE
            val lp = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0,0,40,0)
            buttonBarLayout.layoutParams=lp
            edTitle.setHint("搜索知识库")
        } else {
            mTitle.visibility = View.VISIBLE
            edTitle.visibility = View.GONE

        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_repository_category
    }

    override fun init() {
        super.init()
        initView()

        initRecycler()
    }

    private fun initView() {
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        refreshLayout.autoRefresh()
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

    private var data = ArrayList<CategoryBean>()
    private fun initRecycler() {
        data.add(CategoryBean())
        data.add(CategoryBean())
        data.add(CategoryBean())
        data.add(CategoryBean())
        data.add(CategoryBean())
        data.add(CategoryBean())
        data.add(CategoryBean())
        data.add(CategoryBean())
        mAdapter = CategoryAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, MyWebActivity::class.java).putExtra(MyWebActivity.Intent_Title, "类别一").putExtra(MyWebActivity.Intent_Url, "http://www.baidu.com"))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        refreshLayout?.finishRefresh()
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH

    }

    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
    }

    companion object {
        var Intent_Class_Name = "ClassName"
    }
}
