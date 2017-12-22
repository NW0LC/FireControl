package com.exz.firecontrol.module.login

import android.text.TextUtils
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_change_password.*
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/22.
 * 修改密码
 */

class ChangePasswordActivity : BaseActivity() {
    override fun initToolbar(): Boolean {
        mTitle.text =mContext.getString(R.string.change_password)
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
        return R.layout.activity_change_password
    }

    override fun init() {
        super.init()
        bt_submit.setOnClickListener {
            var oldPwd = ed_old_password.text.toString().trim()
            if (TextUtils.isEmpty(oldPwd)) {
                ed_old_password.setShakeAnimation()
                return@setOnClickListener
            }

            var newPwd = ed_new_password.text.toString().trim()
            if (TextUtils.isEmpty(newPwd)) {
                ed_new_password.setShakeAnimation()
                return@setOnClickListener
            }
            var password = ed_password.text.toString().trim()
            if (TextUtils.isEmpty(password)) {
                ed_password.setShakeAnimation()
                return@setOnClickListener
            }
            if (!newPwd.equals(password)) {
                toast("两次输入的密码不一致!")
                return@setOnClickListener
            }
        }
    }
}
