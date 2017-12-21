package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.KnowledgeInfoListBean
import kotlinx.android.synthetic.main.item_unit_tab_layout.view.*

/**
 * Created by pc on 2017/12/20.
 */

class CategoryAdapter<T : KnowledgeInfoListBean.KnowledgeListBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_unit_tab_layout, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        helper.itemView.tv_oval_num.text = (helper.adapterPosition + 1).toString()
        helper.itemView.tv_title.text = item.title
    }
}
