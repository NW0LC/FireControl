package com.exz.firecontrol.module.mycenter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.imageloder.GlideImageLoader
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_my_user_info.*
import java.util.*

/**
 * Created by pc on 2017/12/22.
 * 个人资料
 */

class MyUserInfoActivity : BaseActivity(), View.OnClickListener {

    override fun initToolbar(): Boolean {
        mTitle.text = mContext.getString(R.string.my_user_info)
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setMargin(this, header)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_my_user_info
    }

    override fun init() {
        initView()
        initCamera()
        initData()
    }

    private fun initView() {
        iv_header.setOnClickListener(this)
    }

    private fun initData(){
        DataCtrlClass.getFireManById(this,MyApplication.user?.userId?:"") {
            if (it!=null) {
                iv_header.setImageURI(it.FireManInfo?.userHead?:"")
                tv_nicekname.text=it.FireManInfo?.Name?:"——"
                tv_unit.text=it.FireManInfo?.RoleName?:"——"
                tv_phone.text=it.FireManInfo?.telephone?:"——"
                tv_phone.text=it.FireManInfo?.telephone?:"——"
                tv_place_post.text=it.FireManInfo?.policeRank?:"——"
            }
        }
    }
    private fun initCamera() {
        val w = ScreenUtils.getScreenWidth() * 0.2
        val layoutParams = iv_header.layoutParams
        layoutParams.width = w.toInt()
        layoutParams.height = w.toInt()
        iv_header.layoutParams = layoutParams
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader()
        //显示相机
        imagePicker.isShowCamera = true
        //是否裁剪
        imagePicker.isCrop = true
        //是否按矩形区域保存裁剪图片
        imagePicker.isSaveRectangle = true
        //圖片緩存
        imagePicker.imageLoader = GlideImageLoader()
        imagePicker.isMultiMode = false//单选
        //矩形尺寸
        imagePicker.style = CropImageView.Style.RECTANGLE
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics).toInt()
        imagePicker.focusWidth = width
        imagePicker.focusHeight = width
        //圖片輸出尺寸
        imagePicker.outPutX = width
        imagePicker.outPutY = width
    }
    override fun onClick(p0: View) {
        when (p0) {
            iv_header -> {
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
                val images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
                iv_header.setImageURI("file:///"+(images[0] as ImageItem).path)
            }
        } catch (e: Exception) {
        }
    }

}
