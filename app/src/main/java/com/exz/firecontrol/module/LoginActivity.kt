package com.exz.firecontrol.module

import com.exz.firecontrol.R
import com.szw.framelibrary.base.BaseActivity

/**
 * Created by pc on 2017/12/18.
 */

class LoginActivity : BaseActivity() {
    override fun initToolbar(): Boolean {
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_login
    }
}
