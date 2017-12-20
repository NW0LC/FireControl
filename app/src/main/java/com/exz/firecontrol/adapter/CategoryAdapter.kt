package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.CategoryBean
import kotlinx.android.synthetic.main.item_unit_tab_layout.view.*

/**
 * Created by pc on 2017/12/20.
 */

class CategoryAdapter() : BaseQuickAdapter<CategoryBean, BaseViewHolder>(R.layout.item_unit_tab_layout, null) {

    override fun convert(helper: BaseViewHolder, item: CategoryBean) {
        var v=helper.itemView
        v.tv_oval_num.text=(helper.adapterPosition+1).toString()
    }
}
