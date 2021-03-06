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
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.UnitTabLayoutAdapter
import com.exz.firecontrol.bean.EnterPriseAllListBean
import com.exz.firecontrol.module.unit.InfoActivity.Companion.Intent_getEnterPrise_id
import com.exz.firecontrol.module.unit.UnitDetailActivity.Companion.Intent_UnitDetailActivity_lat
import com.exz.firecontrol.module.unit.UnitDetailActivity.Companion.Intent_UnitDetailActivity_lon
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_unit_tab.*

class UnitTabLayoutFragment : MyBaseFragment(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: UnitTabLayoutAdapter<EnterPriseAllListBean.EnterpriseInfoBean>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_unit_tab, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()
        SZWUtils.setRefreshAndHeaderCtrl(this,header,refreshLayout)
        refreshLayout.autoRefresh()
    }

    fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.setPaddingSmart(activity, mRecyclerView)
        StatusBarUtil.setMargin(activity, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 45f)
        SZWUtils.setMargin(header, 45f)
        return false
    }

    private fun initRecycler() {
        mAdapter = UnitTabLayoutAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(RecycleViewDivider(context!!, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(context!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(context, UnitDetailActivity::class.java).putExtra(Intent_getEnterPrise_id,mAdapter.data[position].Id).
                putExtra(Intent_UnitDetailActivity_lon,mAdapter.data[position].Longitude).putExtra(Intent_UnitDetailActivity_lat,mAdapter.data[position].Latitude))
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
        DataCtrlClass.getEnterPriseAllList(context,Type = arguments?.getString(Arguments_type)?:"",Level = arguments?.getString(Arguments_level)?:"",currentPage= currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.EnterpriseInfos)
                } else {
                    mAdapter.addData(it.EnterpriseInfos ?: ArrayList())
                }
                if (it.EnterpriseInfos?.isNotEmpty() == true) {
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


    companion object {
        var Arguments_level = "Arguments_level"
        var Arguments_type = "Arguments_type"
        fun newInstance(level: String,type:String): UnitTabLayoutFragment {
            val bundle = Bundle()
            bundle.putString(Arguments_level, level)
            bundle.putString(Arguments_type, type)
            val fragment = UnitTabLayoutFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
