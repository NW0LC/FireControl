package com.exz.firecontrol.module.vehicle

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
import com.exz.firecontrol.adapter.FireCarLocBean
import com.exz.firecontrol.adapter.VehicleAdapter
import com.exz.firecontrol.bean.StairBean
import com.exz.firecontrol.module.vehicle.VehicleDetailActivity.Companion.Intent_VehicleDetail_Id
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
import kotlinx.android.synthetic.main.activity_vehicle.*
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow

/**
 * Created by pc on 2017/12/20.
 * 消防车辆
 */

class VehicleActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private lateinit var mPopCarType: StairPop//车辆类型
    private lateinit var mPopCarState: StairPop//车辆状态
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 0
    private lateinit var mAdapter: VehicleAdapter<FireCarLocBean>

    private var dataState = ArrayList<StairBean>()
    private var dataType = ArrayList<StairBean>()
    private var searchContent=""
    private var carType=""
    private var carState=""
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
        edTitle.hint = "搜索车辆编号"
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_vehicle
    }

    override fun init() {
        initView()
        initPop()
        initRecycler()
    }
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
        rb2.setOnClickListener(this)
    }
    private fun initPop() {
//        1.登高车2.指挥车3.泡沫车4.水罐车5.干粉车6.高鹏车 7越野摩托车
        dataType.add(StairBean("", "全部类型", true))
        dataType.add(StairBean("1", "登高车"))
        dataType.add(StairBean("2", "指挥车"))
        dataType.add(StairBean("3", "泡沫车"))
        dataType.add(StairBean("4", "水罐车"))
        dataType.add(StairBean("5", "干粉车"))
        dataType.add(StairBean("6", "高喷车"))
        dataType.add(StairBean("7", "越野摩托车"))
        dataState.add(StairBean("", "全部",true))
        dataState.add(StairBean("1", "在线"))
        dataState.add(StairBean("0", "离线"))
        mPopCarType = StairPop(mContext, {
            carType=it.id
            setGaryOrblue(rb1, true, it.name)
            onRefresh(refreshLayout)
        })
        mPopCarState = StairPop(mContext, {
            carState= it.id
            setGaryOrblue(rb2, true, it.name)
            onRefresh(refreshLayout)
        })

        val onDismissListener: BasePopupWindow.OnDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        mPopCarState.onDismissListener = onDismissListener
        mPopCarType.onDismissListener = onDismissListener
        mPopCarState.data = dataState
        mPopCarType.data = dataType
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
        mAdapter = VehicleAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext!!, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext!!, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext,VehicleDetailActivity::class.java).putExtra(Intent_VehicleDetail_Id,mAdapter.data[position].id))
            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        refreshLayout.autoRefresh()
    }

    override fun onClick(p0: View) {
        when (p0) {
            rb1 -> {
                mPopCarType.showPopupWindow(radioGroup)
            }
            rb2 -> {
                mPopCarState.showPopupWindow(radioGroup)
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
        DataCtrlClass.getFireCarListByPage(this,carType = carType,isOnline = carState ,carNum =searchContent , currentPage = currentPage) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it.FireCarLocs)
                    tv_count.text=String.format(getString(R.string.vehicles),it.carCount.toString())
                } else {
                    mAdapter.addData(it.FireCarLocs ?: ArrayList())
                }
                if (it.FireCarLocs?.isNotEmpty() == true) {
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
