package com.exz.firecontrol.module.unit

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.exz.firecontrol.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_unit_tab_layout.*

/**
 * Created by pc on 2017/12/19.
 *
 */

class UnitTabLayoutActivity : BaseActivity(), OnRefreshListener {


    override fun initToolbar(): Boolean {
        mTitle.text =intent.getStringExtra(Intent_Class_Name)
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_unit_tab_layout
    }

    override fun init() {
        super.init()
        initView()
    }
    private val mTitles = arrayOf("一级单位", "二级单位","三级单位", "四级单位","五级单位", "六级单位")
    private val frament =ArrayList<Fragment>()
    private fun initView() {
        for (mTitle in mTitles) {
            frament.add(UnitTabLayoutFragment.newInstance(mTitle.indexOf(mTitle),intent.getStringExtra(Intent_getEnterPriseAllList_Type)?:""))
        }
        mainTabBar.setViewPager(mViewPager,mTitles,this,frament)

    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
    }

    companion object {
        val Intent_Class_Name="ClassName"
        val Intent_getEnterPriseAllList_Type="Intent_getEnterPriseAllList_Type"
    }
}