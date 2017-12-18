package com.exz.firecontrol.module.login

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.exz.firecontrol.R
import com.exz.firecontrol.module.MainActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by pc on 2017/12/18.
 */

class LoginActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {
        StatusBarUtil.immersive(this)
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        ed_account.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_login_account), null, null, null)
        ed_pwd.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_login_password), null, null, null)
        bt_forget_pwd.setOnClickListener(this)
        bt_commit.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            bt_forget_pwd -> {
                startActivity(Intent(mContext, ForgetPwdActivity::class.java))

            }
            bt_commit -> {
                startActivity(Intent(mContext,MainActivity::class.java))
                val accont = ed_account.text.toString().trim()
                if (TextUtils.isEmpty(accont)) {
                    ed_account.setShakeAnimation()
                    return
                }
                val pwd = ed_pwd.text.toString().trim()
                if (TextUtils.isEmpty(pwd)) {
                    ed_pwd.setShakeAnimation()
                    return
                }


            }
        }
    }
}
