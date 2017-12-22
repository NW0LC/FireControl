package com.exz.firecontrol.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import com.exz.firecontrol.bean.FireMainLocAllListBean
import kotlinx.android.synthetic.main.item_person.view.*

/**
 * Created by pc on 2017/12/21.
 */

class PersonAdapter<T: FireMainLocAllListBean.FireManLocBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_person, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        val v = helper.itemView
        v.img_head.setImageURI(item.userHead)
        v.Name.text = item.Name
        v.RoleName.text = item.RoleName
        v.PCNumber.text = item.PCNumber
        if (item.IsOnline==1) { //1.在线0.不在线
            v.IsOnline.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.MaterialGreenA400)
        } else {
            v.IsOnline.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.MaterialGrey400)
        }
        v.IsOnline.text=if (item.IsOnline==1) mContext.getString(R.string.on_line) else  mContext.getString(R.string.off_line)
    }
}
