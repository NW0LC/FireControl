package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.UnitDetailAdapter
import com.exz.firecontrol.bean.UnitDetailBean
import com.exz.firecontrol.module.MapLocationActivity
import com.exz.firecontrol.module.unit.DrawingsActivity.Companion.Intent_getDrawFileList_id
import com.exz.firecontrol.module.unit.EnterPriseDataActivity.Companion.Intent_EnterPriseDataActivity_id
import com.exz.firecontrol.module.unit.InfoActivity.Companion.Intent_getEnterPrise_id
import com.exz.firecontrol.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_unit_detail.*

/**
 * Created by pc on 2017/12/19.
 */

class UnitDetailActivity : BaseActivity() {
    private lateinit var mAdapter: UnitDetailAdapter
    override fun initToolbar(): Boolean {
        mTitle.text = "单位详情"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 10f)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_unit_detail
    }

    override fun init() {
        initView()
        initRecycler()
    }

    private fun initView() {
    }

    private var data = ArrayList<UnitDetailBean>()
    private fun initRecycler() {
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_1), "单位基本信息"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_2), "消防基本信息"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_3), "图纸资料"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_4), "救援预案"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_5), "消防水源"))
        data.add(UnitDetailBean((R.mipmap.icon_unit_detail_6), "地图导航"))
        mAdapter = UnitDetailAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                    when (mAdapter.data[position].name) {
                        "单位基本信息" -> {
                            startActivity(Intent(mContext, InfoActivity::class.java).putExtra(Intent_getEnterPrise_id,intent.getStringExtra(Intent_getEnterPrise_id)?:"").putExtra(InfoActivity.Intent_Class_Name, mAdapter.data[position].name))
                        }
                        "消防基本信息" -> {
                            startActivity(Intent(mContext, InfoActivity::class.java).putExtra(Intent_getEnterPrise_id,intent.getStringExtra(Intent_getEnterPrise_id)?:"").putExtra(InfoActivity.Intent_Class_Name, mAdapter.data[position].name))
                        }
                        "图纸资料" -> {
                            startActivity(Intent(mContext, DrawingsActivity::class.java).putExtra(Intent_getDrawFileList_id,intent.getStringExtra(Intent_getEnterPrise_id)?:""))
                        }
                        "救援预案" -> {
                            startActivity(Intent(mContext, EnterPriseDataActivity::class.java).putExtra(Intent_EnterPriseDataActivity_id,intent.getStringExtra(Intent_getEnterPrise_id)?:""))
                        }
                        "消防水源" -> {
                            startActivity(Intent(mContext, FirewaterSupplyActivity::class.java))
                        }
                        "地图导航" -> {
                            intent.getStringExtra(Intent_UnitDetailActivity_lon)
                            intent.getStringExtra(Intent_UnitDetailActivity_lat)
                            startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(MapLocationActivity.Intent_Class_Name,"地址"))
                        }
                    }

            }
        })
    }
    companion object {

        var Intent_UnitDetailActivity_lon = "Intent_UnitDetailActivity_lon"
        var Intent_UnitDetailActivity_lat = "Intent_UnitDetailActivity_lat"
    }
}
