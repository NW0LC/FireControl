package com.exz.firecontrol.module.login

import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.PreferencesService
import com.szw.framelibrary.observer.SmsContentObserver
import com.szw.framelibrary.utils.SZWUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_forget_pwd.*

/**
 * Created by pc on 2017/12/18.
 * 忘记密码
 */

class ForgetPwdActivity : BaseActivity(), View.OnClickListener {
    private val downKey = "F"
    private val time = 120000//倒计时时间
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var smsContentObserver: SmsContentObserver
    override fun initToolbar(): Boolean {
        mTitle.text =mContext.getString(R.string.forget_pwd_class_name)
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
        return R.layout.activity_forget_pwd
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        ed_phone.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_forget_pwd_phone), null, null, null)
        ed_code.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_forget_pwd_code), null, null, null)
        ed_pwd.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_forget_pwd), null, null, null)
        ed_pwd2.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.mipmap.icon_forget_pwd), null, null, null)
        smsContentObserver = SZWUtils.registerSMS(mContext, SZWUtils.patternCode(mContext, ed_code,4))

        val currentTime = System.currentTimeMillis()
        if (PreferencesService.getDownTimer(mContext, downKey) in 1..(currentTime - 1)) {
            downTimer(time - (currentTime - PreferencesService.getDownTimer(mContext, downKey)))
        }
        bt_code.setOnClickListener(this)
        bt_commit.setOnClickListener(this)
    }

    private fun downTimer(l: Long) {
        countDownTimer = object : CountDownTimer(l, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                resetTimer(false, millisUntilFinished)
            }

            override fun onFinish() {
                resetTimer(true, java.lang.Long.MIN_VALUE)
            }
        }
        countDownTimer.start()
    }
    private fun resetTimer(b: Boolean, millisUntilFinished: Long) {
        if (b) {
            countDownTimer.cancel()
            bt_code.text = getString(R.string.get_code)
            bt_code.isClickable = true
            bt_code.delegate.backgroundColor=ContextCompat.getColor(mContext,R.color.colorPrimary)
            PreferencesService.setDownTimer(mContext, downKey, 0)
        } else {
            bt_code.isClickable = false
            bt_code.text = String.format(getString(R.string.login_hint_get_reGetCode), millisUntilFinished / 1000)
            bt_code.delegate.backgroundColor=ContextCompat.getColor(mContext,R.color.MaterialGrey400)
        }

    }
    override fun onClick(p0: View) {
        when (p0) {
            bt_code -> {
           val phone=ed_phone.text.toString().trim()
                if(TextUtils.isEmpty(phone)){
                    ed_phone.setShakeAnimation()
                    return
                }

                PreferencesService.setDownTimer(this, downKey, System.currentTimeMillis())

                downTimer(time - (System.currentTimeMillis() - PreferencesService.getDownTimer(mContext, downKey)))
//                DataCtrlClass.getSecurityCode(this, ed_phone.text.toString(), "2") {
//                    if (it != null) {
//                    } else {
//                        resetTimer(true, java.lang.Long.MIN_VALUE)
//                    }
//                }
            }
            bt_commit -> {

                val phone=ed_phone.text.toString().trim()
                if(TextUtils.isEmpty(phone)){
                    ed_phone.setShakeAnimation()
                    return
                }

                val code=ed_code.text.toString().trim()
                if(TextUtils.isEmpty(code)){
                    ed_code.setShakeAnimation()
                    return
                }

                val pwd=ed_pwd.text.toString().trim()
                if(TextUtils.isEmpty(pwd)){
                    ed_pwd.setShakeAnimation()
                    return
                }
                val pwd2=ed_pwd2.text.toString().trim()
                if(TextUtils.isEmpty(pwd2)){
                    ed_pwd2.setShakeAnimation()
                    return
                }
                finish()
            }
        }
    }


}
