package com.exz.firecontrol.module.disaster

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_disaster.*

/**
 * Created by pc on 2017/12/21.
 * 灾情列表
 */

class DisasterActivity : BaseActivity() {


    override fun initToolbar(): Boolean {
        mTitle.text = "灾情列表"
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

    override fun init() {
        super.init()
        initView()
    }

    private val mTitles = arrayOf("全部", "待处理", "正在处理", "已结束")
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private val mFragments = java.util.ArrayList<Fragment>()
    private fun initView() {
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mFragments.add(DisasterFragment.newInstance())
        mFragments.add(DisasterFragment.newInstance())
        mFragments.add(DisasterFragment.newInstance())
        mFragments.add(DisasterFragment.newInstance())
        mainTabBar.setTabData(mTabEntities, this, R.id.mFrameLayout, mFragments)
    }


    override fun setInflateId(): Int {
        return R.layout.activity_disaster
    }
}
