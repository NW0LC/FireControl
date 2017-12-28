package com.exz.firecontrol.module.login

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.module.MainActivity
import com.szw.framelibrary.app.MyApplication
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
        initView()
        ed_account.setText(MyApplication.getSPUtils(this)?.getString(USER_NAME) ?: "")
        ed_pwd.setText(MyApplication.getSPUtils(this)?.getString(USER_PWD) ?: "")
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
                DataCtrlClass.changeKey(this){
                    if (it!=null)
                    DataCtrlClass.userLogin(this,ed_account.text.toString(),ed_pwd.text.toString()){
                        if (it!=null){
                            MyApplication.user=it
                            MyApplication.getSPUtils(this)?.put(USER_NAME,ed_account.text.toString())
                            MyApplication.getSPUtils(this)?.put(USER_PWD,ed_pwd.text.toString())
                            startActivity(Intent(mContext,MainActivity::class.java))
                            finish()
                        }
                    }

                }


            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    companion object {
        var USER_NAME = "user_name"
        var USER_PWD = "user_pwd"
    }
}
