package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.LiveList

/**
 * Created by pc on 2017/12/25.
 */

class LiveListAdapter() : BaseQuickAdapter<LiveList, BaseViewHolder>(R.layout.item_live_list, null) {

    override fun convert(helper: BaseViewHolder, item: LiveList) {

    }
}
