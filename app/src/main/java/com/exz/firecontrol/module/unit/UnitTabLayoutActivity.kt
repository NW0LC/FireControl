package com.exz.firecontrol.module.unit

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_unit_tab_layout.*

/**
 * Created by pc on 2017/12/19.
 *
 */

class UnitTabLayoutActivity : BaseActivity(){

    private val mTitles = arrayOf("一级重点单位", "二级重点单位")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mFragments = ArrayList<Fragment>()
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
        initView()
    }
    private fun initView() {
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mFragments.add(UnitTabLayoutFragment.newInstance(mTitles[0],intent.getStringExtra(Intent_Class_Name)?:""))
        mFragments.add(UnitTabLayoutFragment.newInstance(mTitles[1],intent.getStringExtra(Intent_Class_Name)?:""))
        mTabLayout.setTabData(mTabEntities, this, R.id.frameLayout, mFragments)

    }

    companion object {
        val Intent_Class_Name="ClassName"
    }
}