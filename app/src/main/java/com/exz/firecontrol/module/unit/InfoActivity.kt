package com.exz.firecontrol.module.unit

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.InfoAdapter
import com.exz.firecontrol.bean.InfoBean
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_info.*

/**
 * Created by pc on 2017/12/20.
 * 基本信息 消防基本信息
 */

class InfoActivity : BaseActivity() {
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
        super.init()
        initRecycler()
    }

    private var data = ArrayList<InfoBean>()
    private fun initRecycler() {
        if (intent.getStringExtra(Intent_Class_Name).equals("单位基本信息")) {
            data.add(InfoBean("单位基本信息", "中国"))
            data.add(InfoBean("单位地址", "中国"))
            data.add(InfoBean("法定代表人", "中国"))
            data.add(InfoBean("消防管理人", "中国"))
            data.add(InfoBean("消防安全负责人", "中国"))
        } else if (intent.getStringExtra(Intent_Class_Name).equals("消防基本信息")) {
            data.add(InfoBean("消防泵", "中国"))
            data.add(InfoBean("消防栓", "中国"))
            data.add(InfoBean("消防水罐", "中国"))
            data.add(InfoBean("消防水带", "中国"))
            data.add(InfoBean("泡沫储存量", "中国"))
            data.add(InfoBean("泡沫类型", "中国"))
            data.add(InfoBean("消防水源形式", "中国"))
            data.add(InfoBean("固定灭火系统", "中国"))
            data.add(InfoBean("储罐类型", "中国"))
        }

        mAdapter = InfoAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
    }

    companion object {
        var Intent_Class_Name = "ClassName"
    }
}
