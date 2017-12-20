package com.exz.firecontrol.module.unit

import android.content.Intent
import android.view.View
import com.exz.firecontrol.R
import com.exz.firecontrol.module.MapLocationActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_firewater_supply.*

/**
 * Created by pc on 2017/12/20.
 * 消防水源
 */

class FirewaterSupplyActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {
        mTitle.text = "消防水源"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setPaddingSmart(this, blurView)
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
        super.init()
        initView()
    }

    private fun initView() {
        tv_fire_hydrant.setOnClickListener(this)
        tv_fire_pool.setOnClickListener(this)
        tv_firefighting_jug.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            tv_fire_hydrant -> {//消防栓
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(MapLocationActivity.Intent_Class_Name,"消防栓"))
            }
            tv_fire_pool -> {//消防水池
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(MapLocationActivity.Intent_Class_Name,"消防水池"))
            }
            tv_firefighting_jug -> {//消防水罐
                startActivity(Intent(mContext, MapLocationActivity::class.java).putExtra(MapLocationActivity.Intent_Class_Name,"消防水罐"))
            }
        }
    }
}
