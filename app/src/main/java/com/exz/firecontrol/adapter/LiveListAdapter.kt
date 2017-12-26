package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireInfoLiveBean

/**
 * Created by pc on 2017/12/25.
 */

class LiveListAdapter<T: FireInfoLiveBean.LiveListBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_live_list, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {


    }
}
