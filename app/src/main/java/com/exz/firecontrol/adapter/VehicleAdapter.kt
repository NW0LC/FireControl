package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.VehicleBean

/**
 * Created by pc on 2017/12/20.
 */

class VehicleAdapter() : BaseQuickAdapter<VehicleBean, BaseViewHolder>(R.layout.item_veicle, null) {

    override fun convert(helper: BaseViewHolder, item: VehicleBean) {

    }
}
