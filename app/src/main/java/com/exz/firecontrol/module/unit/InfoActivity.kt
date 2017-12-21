package com.exz.firecontrol.module.unit

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.InfoAdapter
import com.exz.firecontrol.bean.InfoBean
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_info.*

/**
 * Created by pc on 2017/12/20.
 * 基本信息 消防基本信息
 */

class InfoActivity : BaseActivity(), OnRefreshListener {
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        initRecycler()
    }

    private lateinit var mAdapter: InfoAdapter
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
        return R.layout.activity_info
    }

    override fun init() {
        SZWUtils.setRefreshAndHeaderCtrl(this,header,refreshLayout)
        initRecycler()
    }

    private var data = ArrayList<InfoBean>()
    private fun initRecycler() {
        data.clear()
        DataCtrlClass.getEnterPrise(this,intent.getStringExtra(Intent_getEnterPrise_id)?:""){
            refreshLayout?.finishRefresh()
            if (it!=null){
                if (intent.getStringExtra(Intent_Class_Name) == "单位基本信息") {
                    data.add(InfoBean("单位基本信息",it.Name?:""))
                    data.add(InfoBean("单位地址", it.Address?:""))
                    data.add(InfoBean("法定代表人", it.LegalAgent?:""))
                    data.add(InfoBean("消防管理人", it.Manager?:""))
                    data.add(InfoBean("消防安全负责人", it.Artificial?:""))
                } else if (intent.getStringExtra(Intent_Class_Name) == "消防基本信息") {
                    data.add(InfoBean("消防泵", it.FirePump?:""))
                    data.add(InfoBean("消防栓", it.Hydrant?:""))
                    data.add(InfoBean("消防水罐", it.WaterTank?:""))
                    data.add(InfoBean("消防水带",it.FireHose?:""))
                    data.add(InfoBean("泡沫储存量", it.FoamReserve?:""))
                    data.add(InfoBean("泡沫类型",it.FoamType?:""))
                    data.add(InfoBean("消防水源形式", it.FireWaterType?:""))
                    data.add(InfoBean("固定灭火系统", it.FFES?:""))
                    data.add(InfoBean("储罐类型", it.OilTankType?:""))
                }
                mAdapter.setNewData(data)
            }
        }


        mAdapter = InfoAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
    }

    companion object {
        var Intent_Class_Name = "ClassName"
        var Intent_getEnterPrise_id = "Intent_getEnterPrise_id"
        var Intent_getEnterPrise_comId = "Intent_getEnterPrise_comId"
    }
}
