package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.DisasterBean

/**
 * Created by pc on 2017/12/18.
 */

class DisasterAdapter() : BaseQuickAdapter<DisasterBean, BaseViewHolder>(R.layout.item_disaster, ArrayList<DisasterBean>()) {

    override fun convert(helper: BaseViewHolder, item: DisasterBean) {

    }
}
