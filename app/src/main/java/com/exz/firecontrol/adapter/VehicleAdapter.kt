package com.exz.firecontrol.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.firecontrol.R
import kotlinx.android.synthetic.main.item_veicle.view.*


class VehicleAdapter<T: FireCarLocBean>: BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_veicle, ArrayList<T>()) {

    override fun convert(helper: BaseViewHolder, item: T) {
        val itemView = helper.itemView
        itemView.tv_title.text=item.RoleName
        itemView.tv_num.text=item.carNum
        itemView.tv_unit.text=item.RoleName
        itemView.tv_is_online.delegate.backgroundColor=ContextCompat.getColor(mContext,if (item.IsOnline=="1")R.color.MaterialGreenA400 else R.color.MaterialGrey400)
        itemView.tv_is_online.text=if (item.IsOnline=="1") mContext.getString(R.string.on_line) else  mContext.getString(R.string.off_line)

        when (item.carType) {
            "1" -> { }
            "2" -> { }
            "3" -> { }
            "4" -> { }
            "5" -> { }
            "6" -> { }
            "7" -> { }
            else -> {
            }
        }
    }
}
