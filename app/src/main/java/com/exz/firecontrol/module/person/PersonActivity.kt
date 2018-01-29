package com.exz.firecontrol.module.person

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.adapter.PersonAdapter
import com.exz.firecontrol.bean.FireMainLocAllListBean
import com.exz.firecontrol.bean.StairBean
import com.exz.firecontrol.module.person.PersonDetailActivity.Companion.Intent_PersonDetail_Id
import com.exz.firecontrol.pop.StairPop
import com.exz.firecontrol.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import com.szw.framelibrary.view.DrawableCenterButton
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_person.*
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow

/**
 * Created by pc on 2017/12/20.
 * 人员信息选择
 */

class PersonActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private lateinit var mPop: StairPop//在线状态
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: PersonAdapter<FireMainLocAllListBean.FireManLocBean>

    private var isOnline=""
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        edTitle.hint = "搜索人员姓名"
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_person
    }

    override fun init() {
        initView()
        initPop()
        initRecycler()
    }



    var dataState = ArrayList<StairBean>()
    var searchContent=""
    private fun initView() {
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        edTitle.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // do something
                 searchContent = edTitle.text.toString().trim { it <= ' ' }
                onRefresh(refreshLayout)
                return@OnEditorActionListener true
            }
            false
        })
        rb1.setOnClickListener(this)
    }
    private fun initPop() {
        dataState.add(StairBean("", "全部", true))
        dataState.add(StairBean("1", "在线"))
        dataState.add(StairBean("0", "离线"))
        mPop = StairPop(mContext, {
            isOnline= it.id
            setGaryOrblue(rb1, true, it.name)
            onRefresh(refreshLayout)
        })
        mPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        mPop.data = dataState
    }
    private fun setGaryOrblue(rb: DrawableCenterButton, check: Boolean, name: String) {
        if (!TextUtils.isEmpty(name)) rb.text = name
        if (check) {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.blue_arrow), null)
            rb.textColor = ContextCompat.getColor(mContext, R.color.colorPrimary)

        } else {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.gray_arrow), null)
            rb.textColor = ContextCompat.getColor(mContext, R.color.MaterialGrey600)
        }
    }

    private fun initRecycler() {
        mAdapter = PersonAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, PersonDetailActivity::class.java).putExtra(Intent_PersonDetail_Id,mAdapter.data[position].id.toString()))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        refreshLayout.autoRefresh()
    }

    override fun onClick(p0: View) {
        when (p0) {
            rb1 -> {
                mPop.showPopupWindow(radioGroup)
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 0
        refreshState = Constants.RefreshState.STATE_REFRESH
        iniData()

    }


    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.getFireManAllByPage(this,searchContent,isOnline,currentPage){
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.FireManLocs)
                    tv_count.text=String.format(getString(R.string.numCount),it.fireManCount.toString())
                } else {
                    mAdapter.addData(it.FireManLocs ?: ArrayList())
                }
                if (it.FireManLocs?.isNotEmpty() == true) {
                    mAdapter.loadMoreComplete()
                    currentPage=mAdapter.data.size
                } else {
                    mAdapter.loadMoreEnd()
                }
            } else {
                mAdapter.loadMoreFail()
            }
        }
    }

}