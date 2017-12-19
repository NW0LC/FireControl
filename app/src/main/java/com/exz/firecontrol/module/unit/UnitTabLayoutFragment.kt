package com.exz.firecontrol.module.unit

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.UnitTabLayoutAdapter
import com.exz.firecontrol.bean.UnitTabLayoutBean
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_unit_tab.*

/**
 * Created by pc on 2017/12/19.
 */

class UnitTabLayoutFragment : MyBaseFragment(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: UnitTabLayoutAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_unit_tab, container, false)
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

    private var data = ArrayList<UnitTabLayoutBean>()
    private fun initRecycler() {
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        data.add(UnitTabLayoutBean())
        mAdapter = UnitTabLayoutAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(context!!, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(context!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(context, UnitDetailActivity::class.java))
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
        var Arguments = "arguments"
        fun newInstance(type: Int): UnitTabLayoutFragment {
            val bundle = Bundle()
            bundle.putInt(Arguments, type)
            val fragment = UnitTabLayoutFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
