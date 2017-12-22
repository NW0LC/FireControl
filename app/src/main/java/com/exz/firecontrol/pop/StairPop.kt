package com.exz.firecontrol.pop

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.StairLisAdapter
import com.exz.firecontrol.bean.StairBean
import com.szw.framelibrary.utils.RecycleViewDivider
import kotlinx.android.synthetic.main.pop_stair_list.view.*
import razerdp.basepopup.BasePopupWindow

/**
 * Created by pc on 2017/12/20.
 */

class StairPop(context: Context, listener: (entity: StairBean) -> Unit) : BasePopupWindow(context) {

    private lateinit var inflate: View
    var mAdapter: StairLisAdapter = StairLisAdapter()
    var data = ArrayList<StairBean>()
        set(value) {
            field = value
            mAdapter.setNewData(value)
        }

    init {
        popupWindow.isClippingEnabled = false
        mAdapter.bindToRecyclerView(inflate.mRecyclerView)
        inflate.mRecyclerView.layoutManager = LinearLayoutManager(getContext())
        inflate.mRecyclerView.addItemDecoration(RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(getContext(), R.color.White)))
        inflate.mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                mAdapter.data.forEach { it.check = false }
                mAdapter.data[position].check = true
                adapter.notifyDataSetChanged()
                listener.invoke(mAdapter.data[position])
                dismiss()
            }

        })
    }


    override fun getClickToDismissView(): View = popupWindowView

    override fun onCreatePopupView(): View? {
        inflate = View.inflate(context, R.layout.pop_stair_list, null)
        return inflate
    }

    override fun initAnimaView(): View = findViewById(R.id.mRecyclerView)

    override fun initShowAnimation(): Animation {
        val shakeAnimate = AnimationUtils.loadAnimation(context, R.anim.translate_show_start)
        return shakeAnimate
    }


}
