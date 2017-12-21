package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.EnterPriseAllListBean
import kotlinx.android.synthetic.main.item_drawings.view.*

/**
 * Created by pc on 2017/12/20.
 */

class SearchUnitAdapter<T: EnterPriseAllListBean.EnterpriseInfoBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_drawings, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
                helper.itemView.tv_name.text=item.Name
    }
}