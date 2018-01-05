package com.exz.firecontrol.module.unit

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.DrawingsAdapter
import com.exz.firecontrol.bean.DrawFileListBean
import com.exz.firecontrol.bean.UserBean
import com.exz.firecontrol.widget.MyWebActivity
import com.exz.firecontrol.widget.MyWebActivity.Intent_Title
import com.exz.firecontrol.widget.MyWebActivity.Intent_Url
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_drawings.*

/**
 * Created by pc on 2017/12/20.
 * 图纸资料
 */

class DrawingsActivity : BaseActivity() {
    private lateinit var mAdapter: DrawingsAdapter<DrawFileListBean.DrawingFilesBean>
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
    private fun initRecycler() {
        mAdapter = DrawingsAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object :OnItemClickListener(){
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent = Intent(this@DrawingsActivity, MyWebActivity::class.java)
                intent.putExtra(Intent_Url, mAdapter.data[position].Path)
                intent.putExtra(Intent_Title, mAdapter.data[position].Name)
                startActivity(intent)
            }
        })
        DataCtrlClass.getDrawFileList(this,intent.getStringExtra(Intent_getDrawFileList_id)?:"",intent.getStringExtra(Intent_getDrawFileList_comId)?:(MyApplication.user as UserBean).comid){
                if (it!=null)
                    mAdapter.setNewData(it.DrawingFiles)
        }
    }
    companion object {
        val Intent_getDrawFileList_id="Intent_getDrawFileList_id"
        val Intent_getDrawFileList_comId="Intent_getDrawFileList_comId"
    }

}
