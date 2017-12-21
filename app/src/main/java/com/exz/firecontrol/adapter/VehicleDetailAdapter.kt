package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.VehicleDetailBean
import kotlinx.android.synthetic.main.item_vehicle_detail.view.*

/**
 * Created by pc on 2017/12/21.
 */

class VehicleDetailAdapter() : BaseQuickAdapter<VehicleDetailBean, BaseViewHolder>(R.layout.item_vehicle_detail, null) {

    override fun convert(helper: BaseViewHolder, item: VehicleDetailBean) {
        var v = helper.itemView
        v.tv_key.text = item.key
        v.tv_value.text = item.value
    }
}
