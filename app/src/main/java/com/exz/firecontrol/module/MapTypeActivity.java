package com.exz.firecontrol.module;

import com.exz.firecontrol.R;
import com.szw.framelibrary.base.BaseActivity;

/**
 * Created by pc on 2017/12/21.
 * 实时路况
 */

public class MapTypeActivity extends BaseActivity{
    @Override
    public boolean initToolbar() {
        return false;
    }

    @Override
    public int setInflateId() {
        return R.layout.activity_map_type;
    }
}
