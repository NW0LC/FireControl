package com.exz.firecontrol.module.chat

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.exz.firecontrol.R
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*



/**
 * Created by pc on 2017/12/22.
 */

class ConversationActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(32)
        setContentView(R.layout.activity_conversation)
        initView()
    }


    private fun initView() {
        mTitle.text=Chat_Class_Name
        mTitle.setTextColor(ContextCompat.getColor(this, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            Chat_Class_Name=""
            finish()
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (null != this.currentFocus && event.action == MotionEvent.ACTION_UP) {
            val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return mInputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
        return super.onTouchEvent(event)
    }
    companion object {
        var Chat_Class_Name="灾情讨论"
    }

    override fun onBackPressed() {
        Chat_Class_Name=""
        super.onBackPressed()
    }
}
