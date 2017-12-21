package com.exz.firecontrol.module.disaster

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import com.exz.firecontrol.R
import com.exz.firecontrol.module.unit.DrawingsActivity
import com.exz.firecontrol.module.unit.FirewaterSupplyActivity
import com.exz.firecontrol.pop.SchemePop
import com.exz.firecontrol.widget.MyWebActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_disaster_detail.*

/**
 * Created by pc on 2017/12/21.
 * 灾情详情
 */

class DisasterDetailActivity : BaseActivity(), View.OnClickListener {


    private lateinit var mPop: SchemePop
    override fun initToolbar(): Boolean {
        mTitle.text = "灾情详情"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(mContext, scrollView)
        StatusBarUtil.setMargin(mContext, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.inflateMenu(R.menu.menu_person)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "建议方案"
        actionView.movementMethod = ScrollingMovementMethod.getInstance()
        actionView.setOnClickListener {
            mPop.showPopupWindow()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_disaster_detail
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mPop = SchemePop(mContext)
        rl_related_plans.setOnClickListener(this)
        rl_plans.setOnClickListener(this)
        rl_xfsy.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            rl_related_plans -> {//关联预案
                startActivity(Intent(mContext, MyWebActivity::class.java).putExtra(MyWebActivity.Intent_Title, "关联预案").putExtra(MyWebActivity.Intent_Url, ""))
            }
            rl_plans -> {//图纸资料
                startActivity(Intent(mContext, DrawingsActivity::class.java))
            }
            rl_xfsy->{//消防水源
                startActivity(Intent(mContext, FirewaterSupplyActivity::class.java))
            }
        }
    }
}
