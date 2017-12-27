package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireDataListBean
import kotlinx.android.synthetic.main.item_drawings.view.*

class FirewaterAdapter<T: FireDataListBean.FireDataBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_drawings, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        helper.itemView.tv_name.text=item.Name
    }
}
