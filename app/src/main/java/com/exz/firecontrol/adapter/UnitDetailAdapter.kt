package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.UnitDetailBean
import kotlinx.android.synthetic.main.item_unit_detail.view.*

/**
 * Created by pc on 2017/12/19.
 */

class UnitDetailAdapter() : BaseQuickAdapter<UnitDetailBean, BaseViewHolder>(R.layout.item_unit_detail, null) {

    override fun convert(helper: BaseViewHolder, item: UnitDetailBean) {
        var v = helper.itemView
        v.img.setBackgroundResource(item.img)
        v.tv_name.text=item.name
    }
}
