package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import com.exz.firecontrol.R
import com.exz.firecontrol.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_key_unit.*

/**
 * Created by pc on 2017/12/19.
 * 重点单位
 */

class KeyUnitActivity : BaseActivity(), View.OnClickListener {

    override fun initToolbar(): Boolean {
        mTitle.text = "重点单位"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        SZWUtils.setPaddingSmart(scrollView, 55f)
        SZWUtils.setMargin(header, 55f)
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_key_unit
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        bt_search.setOnClickListener(this)
        bt_tab_1.setOnClickListener(this)
        bt_tab_2.setOnClickListener(this)
        bt_tab_3.setOnClickListener(this)
        bt_tab_4.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when (p0) {
            bt_search->{//
                startActivity(Intent(mContext,SearchUnitActivity::class.java))
            }
            bt_tab_1 -> {
                startActivity(Intent(mContext,UnitTabLayoutActivity::class.java).putExtra(UnitTabLayoutActivity.Intent_Class_Name,mContext.getString(R.string.petrochemical)))
            }
            bt_tab_2 -> {
                startActivity(Intent(mContext,UnitTabLayoutActivity::class.java).putExtra(UnitTabLayoutActivity.Intent_Class_Name,mContext.getString(R.string.tall_buildings)))
            }
            bt_tab_3 -> {
                startActivity(Intent(mContext,UnitTabLayoutActivity::class.java).putExtra(UnitTabLayoutActivity.Intent_Class_Name,mContext.getString(R.string.personnel_intensive_class)))
            }
            bt_tab_4 -> {
                startActivity(Intent(mContext,UnitTabLayoutActivity::class.java).putExtra(UnitTabLayoutActivity.Intent_Class_Name,mContext.getString(R.string.other_categories)))
            }
        }
    }

}
