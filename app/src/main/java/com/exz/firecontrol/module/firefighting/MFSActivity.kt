package com.exz.firecontrol.module.firefighting

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.amap.api.maps.model.LatLng
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.MFSAdapter
import com.exz.firecontrol.bean.FireDataListBean
import com.exz.firecontrol.bean.UserBean
import com.exz.firecontrol.module.MapLocationActivity
import com.exz.firecontrol.module.MapLocationActivity.Companion.Intent_Lat
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by pc on 2017/12/19.
 * 微型消防站
 */

class MFSActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: MFSAdapter<FireDataListBean.FireDataBean>
    override fun initToolbar(): Boolean {
        mTitle.text = "微型消防站"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_frie_brigade
    }

    override fun init() {
        initView()
        refreshLayout.autoRefresh()
    }

    private fun initView() {
        initRecycler()
    }
    private fun initRecycler() {
        mAdapter = MFSAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, MapLocationActivity::class.java)
                val latLngList = ArrayList<LatLng>()
                latLngList.add(LatLng(mAdapter.data[position].Latitude, mAdapter.data[position].Longitude))
                intent.putExtra(Intent_Lat, latLngList).putExtra(MapLocationActivity.Intent_Class_Name, mAdapter.data[position].Name)
                startActivity(intent)
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
        DataCtrlClass.getFireDataListByPage(mContext,"6",(MyApplication.user as UserBean).comid,(MyApplication.user as UserBean).oid,"0","0",currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.FireDatas)
                } else {
                    mAdapter.addData(it.FireDatas ?: ArrayList())
                }
                if (it.FireDatas?.isNotEmpty() == true) {
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
