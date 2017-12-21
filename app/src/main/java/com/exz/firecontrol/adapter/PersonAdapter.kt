package com.exz.firecontrol.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireMainLocAllListBean

/**
 * Created by pc on 2017/12/21.
 */

class PersonAdapter() : BaseQuickAdapter<FireMainLocAllListBean.FireManLocBean, BaseViewHolder>(R.layout.item_person, null) {

    override fun convert(helper: BaseViewHolder, item: FireMainLocAllListBean.FireManLocBean) {
        var v = helper.itemView
//        v.img_head.setImageURI(item.userHead)
//        v.Name.text = item.Name
//        v.RoleName.text = item.roleName
//        v.PCNumber.text = item.PCNumber
//        if (item.IsOnline.equals("1")) { //1.在线0.不在线
//            v.IsOnline.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
//        } else {
//            v.IsOnline.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.MaterialGrey300)
//        }
    }
}
