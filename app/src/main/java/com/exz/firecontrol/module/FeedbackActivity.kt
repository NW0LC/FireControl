package com.exz.firecontrol.module

import android.text.TextUtils
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_feedback.*

/**
 * Created by pc on 2017/12/22.
 * 意见建议
 */

class FeedbackActivity : BaseActivity() {
    override fun initToolbar(): Boolean {
        mTitle.text = mContext.getString(R.string.feedback)
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
        return R.layout.activity_feedback
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        bt_submit.setOnClickListener {
            val content = ed_content.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                ed_content.setShakeAnimation()
                return@setOnClickListener
            }else{
DataCtrlClass.saveUserAdvise(this,content){
        if (it!=null)
        {
            finish()

        }

}
            }

        }
    }
}
