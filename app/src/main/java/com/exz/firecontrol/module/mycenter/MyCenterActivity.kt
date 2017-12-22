package com.exz.firecontrol.module.mycenter

import android.content.Intent
import android.view.View
import com.exz.firecontrol.R
import com.exz.firecontrol.module.FeedbackActivity
import com.exz.firecontrol.module.SettingActivity
import com.exz.firecontrol.module.login.ChangePasswordActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_my_conter.*

/**
 * Created by pc on 2017/12/22.
 * 个人中心
 */

class MyCenterActivity : BaseActivity(), View.OnClickListener {

    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, bt_userinfo)
        iv_back.setOnClickListener { finish() }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_my_conter
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        my_user_info.setOnClickListener(this)
        tv_change_password.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)
        tv_setting.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            my_user_info -> {//个人信息
                startActivity(Intent(mContext,MyUserInfoActivity::class.java))
            }
            tv_change_password->{//修改密码
                startActivity(Intent(mContext, ChangePasswordActivity::class.java))
            }
            tv_feedback->{//意见建议
                startActivity(Intent(mContext, FeedbackActivity::class.java))
            }
            tv_setting->{//系统设置
                startActivity(Intent(mContext, SettingActivity::class.java))
            }
        }
    }

}
