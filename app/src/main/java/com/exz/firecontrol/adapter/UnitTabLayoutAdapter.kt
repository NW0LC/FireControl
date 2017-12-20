package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.EnterPriseAllListBean
import kotlinx.android.synthetic.main.item_unit_tab_layout.view.*

/**
 * Created by pc on 2017/12/19.
 */

class UnitTabLayoutAdapter<T: EnterPriseAllListBean.EnterpriseInfoBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_unit_tab_layout, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        var v = helper.itemView
        v.tv_oval_num.text = (helper.adapterPosition + 1).toString()
    }
}
