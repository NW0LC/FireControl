package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.DrawingsAdapter
import com.exz.firecontrol.bean.InfoBean
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import com.szw.framelibrary.view.preview.PreviewActivity
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_info.*

/**
 * Created by pc on 2017/12/20.
 * 图纸资料
 */

class DrawingsActivity : BaseActivity() {
    private lateinit var mAdapter: DrawingsAdapter
    override fun initToolbar(): Boolean {
        mTitle.text = "图纸资料"
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_drawings
    }

    override fun init() {
        super.init()
        initRecycler()
    }
    private var data = ArrayList<InfoBean>()
    private fun initRecycler() {
        data.add(InfoBean("工艺流程图", ""))
        data.add(InfoBean("接地点分布图", ""))
        data.add(InfoBean("配电线路图", ""))
        data.add(InfoBean("消防器材分布图", ""))
        data.add(InfoBean("巡检路线路图", ""))
        data.add(InfoBean("中石化安徽宿州油库灭火作战图", ""))
        data.add(InfoBean("总平面图", ""))
        mAdapter = DrawingsAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object :OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(mContext, PreviewActivity::class.java)
                intent.putExtra(PreviewActivity.PREVIEW_INTENT_IMAGES, "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=569334953,1638673400&fm=27&gp=0.jpg")
                intent.putExtra(PreviewActivity.PREVIEW_INTENT_SHOW_NUM, false)
                startActivity(intent)
            }
        })
    }
}
