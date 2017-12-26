package com.exz.firecontrol.module

import android.content.Intent
import android.view.View
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.module.login.LoginActivity
import com.exz.firecontrol.widget.MyWebActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by pc on 2017/12/22.
 * 系统设置
 */

class SettingActivity : BaseActivity(), View.OnClickListener {

    override fun initToolbar(): Boolean {
        mTitle.text = mContext.getString(R.string.setting)
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
        return R.layout.activity_setting
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        bt_about_us.setOnClickListener(this)
        bt_system_update.setOnClickListener(this)
        bt_submit.setOnClickListener(this)
    }
    override fun onClick(p0: View) {
        when (p0) {
            bt_about_us-> {
                startActivity(Intent(mContext,MyWebActivity::class.java).putExtra(MyWebActivity.Intent_Title,"关于我们").
                        putExtra(MyWebActivity.Intent_Url,"http://www.hhuav.com/mobile/about.html"))
            }
            bt_system_update -> {
            }
            bt_submit -> {
                DataCtrlClass.updateIsOnline(this,"0"){
                    if (it!=null)
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }
        }
    }

}
