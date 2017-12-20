package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.InfoBean
import kotlinx.android.synthetic.main.item_info.view.*

/**
 * Created by pc on 2017/12/20.
 */

class InfoAdapter() : BaseQuickAdapter<InfoBean, BaseViewHolder>(R.layout.item_info, null) {

    override fun convert(helper: BaseViewHolder, item: InfoBean) {
var v=helper.itemView
        v.tv_key.text=item.key
        v.tv_value.text=item.value
    }
}
