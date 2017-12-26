package com.exz.firecontrol.module.live

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.LiveListAdapter
import com.exz.firecontrol.bean.LiveList
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_live.*

/**
 * Created by pc on 2017/12/25.
 */

class LiveListActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter:LiveListAdapter
    override fun initToolbar(): Boolean {
        mTitle.text="灾情直播"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.inflateMenu(R.menu.menu_person)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "发起直播"
        actionView.movementMethod = ScrollingMovementMethod.getInstance()
        actionView.setOnClickListener {
            startActivity(Intent(mContext, LivePushActivity::class.java))
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_live
    }

    override fun init() {
        super.init()
        initRecycler()
    }

    private fun initRecycler() {
        var data=ArrayList<LiveList>()
        data.add(LiveList())
        data.add(LiveList())
        mAdapter = LiveListAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mAdapter.setNewData(data)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext,LivePullActivity::class.java))
            }
        })

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
//        DataCtrlClass.getFireInfoListByPage(mContext,arguments?.getString("status")?:"", currentPage = currentPage) {
//            refreshLayout?.finishRefresh()
//            if (it != null) {
//                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
//                    mAdapter.setNewData(it.fireInfoList)
//                } else {
//                    mAdapter.addData(it.fireInfoList ?: ArrayList())
//                }
//                if (it.fireInfoList?.isNotEmpty() == true) {
//                    mAdapter.loadMoreComplete()
//                    currentPage++
//                } else {
//                    mAdapter.loadMoreEnd()
//                }
//            } else {
//                mAdapter.loadMoreFail()
//            }
//        }
    }
}
