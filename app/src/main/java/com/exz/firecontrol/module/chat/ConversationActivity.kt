package com.exz.firecontrol.module.chat

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.exz.firecontrol.R
import kotlinx.android.synthetic.main.action_bar_custom.*

class ConversationActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_conversation)
        initView()
    }


    private fun initView() {
        mTitle.text=Chat_Class_Name
        mTitle.setTextColor(ContextCompat.getColor(this, R.color.White))
        toolbar.setNavigationOnClickListener {
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

}
