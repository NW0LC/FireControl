package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.FirewaterAdapter
import com.exz.firecontrol.bean.FireDataListBean
import com.exz.firecontrol.module.MapLocationActivity
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Latitude
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Longitude
import com.exz.firecontrol.utils.RecycleViewDivider
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_firewater_supply.*

/**
 * Created by pc on 2017/12/20.
 * 消防水源
 */

class FirewaterSupplyActivity : BaseActivity() , OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: FirewaterAdapter<FireDataListBean.FireDataBean>
    override fun initToolbar(): Boolean {
        mTitle.text = "消防水源"
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
        return R.layout.activity_firewater_supply
    }


    override fun init() {
        SZWUtils.setRefreshAndHeaderCtrl(this,header,refreshLayout)
        initRecycler()
        onRefresh(refreshLayout)
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
    private fun initRecycler() {
        mAdapter = FirewaterAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
//        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(Intent_Longitude, mAdapter.data[position].Longitude).putExtra(Intent_Latitude,  mAdapter.data[position].Latitude).putExtra(MapLocationActivity.Intent_Class_Name,"消防栓"))
            }
        })

    }
    private fun iniData() {
        DataCtrlClass.getFireDataListByPage(mContext,intent.getStringExtra(Intent_FireWater_comId)?:"",intent.getStringExtra(Intent_FireWater_lon)?:"",intent.getStringExtra(Intent_FireWater_lat)?:"") {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.FireDatas)
                } else {
                    mAdapter.addData(it.FireDatas ?: ArrayList())
                }
                if (it.FireDatas?.isNotEmpty() == true) {
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
        var Intent_FireWater_comId="Intent_FireWater_comId"
        var Intent_FireWater_lon="Intent_FireWater_lon"
        var Intent_FireWater_lat="Intent_FireWater_lat"
    }
}
