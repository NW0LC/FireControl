package com.exz.firecontrol.module.disaster

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.DisasterAdapter
import com.exz.firecontrol.bean.FireInfoListBean
import com.exz.firecontrol.bean.UserBean
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_disaster.*

/**
 * Created by pc on 2017/12/21.
 */

class DisasterFragment : MyBaseFragment(), BaseQuickAdapter.RequestLoadMoreListener, OnRefreshListener {
    private lateinit var mAdapter: DisasterAdapter<FireInfoListBean.FireInfoBean>
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_disaster, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()
    }

    fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.setPaddingSmart(activity, mRecyclerView)
        StatusBarUtil.setMargin(activity, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 65f)
        SZWUtils.setMargin(header, 65f)
        return false
    }
    private var data = ArrayList<FireInfoListBean.FireInfoBean>()
    private fun initRecycler() {
        data.add(FireInfoListBean.FireInfoBean())
        data.add(FireInfoListBean.FireInfoBean())
        data.add(FireInfoListBean.FireInfoBean())
        data.add(FireInfoListBean.FireInfoBean())
        data.add(FireInfoListBean.FireInfoBean())
        mAdapter = DisasterAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.setNewData(data)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(RecycleViewDivider(context!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(context!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object :OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(context, DisasterDetailActivity::class.java))
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
        DataCtrlClass.getFireInfoListByPage(context!!, (MyApplication.user as UserBean).oid, (MyApplication.user as UserBean).comid, currentPage = currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.fireInfoList)
                } else {
                    mAdapter.addData(it.fireInfoList ?: ArrayList())
                }
                if (it.fireInfoList?.isNotEmpty() == true) {
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
        fun newInstance(): DisasterFragment {
            val bundle = Bundle()
            val fragment = DisasterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
