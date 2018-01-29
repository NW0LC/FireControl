package com.exz.firecontrol.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireDataListBean
import kotlinx.android.synthetic.main.item_firefighting.view.*
import kotlinx.android.synthetic.main.lay_firefighting.view.*


class MFSAdapter<T: FireDataListBean.FireDataBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_firefighting, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item:T) {
        val v = helper.itemView
        v.tv_title.text=item.Name
        v.tv_location.text=item.Address
        v.tv_people.text=item.Liable
        v.iv_state.setBackgroundResource(R.mipmap.icon_firefighting_stand)
        v.tv_more.visibility= View.GONE
        v.tv_see_details.text="查看详情"
    }
}
