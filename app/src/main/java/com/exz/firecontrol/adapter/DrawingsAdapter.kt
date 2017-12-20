package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.InfoBean
import kotlinx.android.synthetic.main.item_drawings.view.*

/**
 * Created by pc on 2017/12/20.
 */

class DrawingsAdapter() : BaseQuickAdapter<InfoBean, BaseViewHolder>(R.layout.item_drawings, null) {

    override fun convert(helper: BaseViewHolder, item: InfoBean) {
        var v = helper.itemView
        v.tv_name.text=item.key
    }
}
