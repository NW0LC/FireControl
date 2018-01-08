package com.exz.firecontrol.module.live

import android.content.ContentResolver
import android.content.res.Configuration
import android.database.ContentObserver
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.view.Surface
import android.view.View
import com.exz.firecontrol.DataCtrlClass
import com.exz.firecontrol.R
import com.exz.firecontrol.module.live.LiveListActivity.Companion.Intent_live_id
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXLivePushConfig
import com.tencent.rtmp.TXLivePusher
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.layout_live_push.*



open class LivePushActivity : BaseActivity(), View.OnClickListener {
    private val VIDEO_SRC_CAMERA = 0
    private val mVideoSrc = VIDEO_SRC_CAMERA
    lateinit var mLivePusher: TXLivePusher
    lateinit var mLivePushConfig: TXLivePushConfig
    private var mVideoPublish: Boolean = false
    private var mFrontCamera = true
    private var mPortrait = true
    // 关注系统设置项“自动旋转”的状态切换
    private lateinit var mRotationObserver: RotationObserver

    override fun initToolbar(): Boolean {
        mTitle.text = "直播"
        StatusBarUtil.immersive(this)
        blurView.setOverlayColor(ContextCompat.getColor(mContext, R.color.transparen))
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.layout_live_push
    }

    override fun init() {
        initView()
        mRotationObserver = RotationObserver(Handler())
        mRotationObserver.startObserver()
        //锁定Activity不旋转的情况下，才能进行横屏|竖屏推流切换
        if (isActivityCanRotation()) {
            btnPushOrientation.visibility = View.GONE
        }
        btnPushOrientation.setOnClickListener(this)

        DataCtrlClass.getLivePath(this, intent.getStringExtra(Intent_live_id) ?: "") {
            if (it != null)
                rtmpUrl = it.livePath ?: ""
        }
    }

    private fun initView() {
        mLivePusher = TXLivePusher(mContext)
        mLivePushConfig = TXLivePushConfig()
        if (Build.VERSION.SDK_INT > 18) {
            mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_HARDWARE)
        }
        mLivePusher.config = mLivePushConfig

        bt_lay.setOnClickListener(this)
        btnPlay.setOnClickListener(this)
        btnCameraChange.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()

        if (mCaptureView != null) {
            mCaptureView.onResume()
        }

        if (mVideoPublish && mVideoSrc == VIDEO_SRC_CAMERA) {
            mLivePusher.resumePusher()
            mLivePusher.resumeBGM()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (mCaptureView != null) {
            mCaptureView.onPause()
        }

        if (mVideoPublish && mVideoSrc == VIDEO_SRC_CAMERA) {
            mLivePusher.pausePusher()
            mLivePusher.pauseBGM()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopPublishRtmp()
        if (mCaptureView != null) {
            mCaptureView.onDestroy()
        }
        mRotationObserver.stopObserver()
    }

    var rtmpUrl = ""
    private fun startPush() {
        btnPlay.setImageResource(R.mipmap.play_pause)
        mVideoPublish = false
        mLivePusher.startPusher(rtmpUrl)
        mLivePusher.startCameraPreview(mCaptureView)
        mCaptureView.visibility = View.VISIBLE
    }

    private fun stopPublishRtmp() {
        btnPlay.setImageResource(R.mipmap.play_start)
        mVideoPublish = true
        mLivePusher.stopBGM()
        mLivePusher.stopCameraPreview(true)
        mLivePusher.stopScreenCapture()
        mLivePusher.setPushListener(null)
        mLivePusher.stopPusher()
        mCaptureView.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnPlay -> {
                if (mVideoPublish) {
                    stopPublishRtmp()
                } else {
                    startPush()
                }
            }
            btnCameraChange -> {//相机
                mFrontCamera = !mFrontCamera

                if (mLivePusher.isPushing) {
                    mLivePusher.switchCamera()
                }
                mLivePushConfig.setFrontCamera(mFrontCamera)
                btnCameraChange.setImageResource(if (mFrontCamera) R.mipmap.camera_change else R.mipmap.camera_change2)
            }

            btnPushOrientation -> {
                mPortrait = !mPortrait
                val renderRotation: Int = if (mPortrait) {
                    mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN)
                    btnPushOrientation.setImageResource(R.mipmap.landscape)
                    0
                } else {
                    mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT)
                    btnPushOrientation.setImageResource(R.mipmap.portrait)
                    90
                }
                if (mLivePusher.isPushing) {
                    mLivePusher.config = mLivePushConfig
                } else
                    mLivePusher.config = mLivePushConfig
                mLivePusher.setRenderRotation(renderRotation)
            }
        }
    }

    /**
     * 判断Activity是否可旋转。只有在满足以下条件的时候，Activity才是可根据重力感应自动旋转的。
     * 系统“自动旋转”设置项打开；
     * @return false---Activity可根据重力感应自动旋转
     */
    protected fun isActivityCanRotation(): Boolean {
        // 判断自动旋转是否打开
        val flag = Settings.System.getInt(this.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0)
        return flag != 0
    }

    //观察屏幕旋转设置变化，类似于注册动态广播监听变化机制
    inner class RotationObserver(handler: Handler) : ContentObserver(handler) {
        private var mResolver: ContentResolver = this@LivePushActivity.contentResolver

        //屏幕旋转设置改变时调用
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            //更新按钮状态
            if (isActivityCanRotation()) {
                btnPushOrientation.visibility = View.GONE
                onActivityRotation()
            } else {
                btnPushOrientation.visibility = View.VISIBLE
                mPortrait = true
                mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN)
                btnPushOrientation.setImageResource(R.mipmap.landscape)
                mLivePusher.setRenderRotation(0)
                mLivePusher.config = mLivePushConfig
            }

        }

        fun startObserver() {
            mResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false, this)
        }

        fun stopObserver() {
            mResolver.unregisterContentObserver(this)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        onActivityRotation()
        super.onConfigurationChanged(newConfig)
    }

    protected fun onActivityRotation() {
        // 自动旋转打开，Activity随手机方向旋转之后，需要改变推流方向
        val mobileRotation = this.windowManager.defaultDisplay.rotation
        var pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN
        when (mobileRotation) {
            Surface.ROTATION_0 -> pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN
            Surface.ROTATION_180 -> pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_UP
            Surface.ROTATION_90 -> {
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT
            }
            Surface.ROTATION_270 -> {
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT
            }
            else -> {
            }
        }
        mLivePusher.setRenderRotation(0) //因为activity也旋转了，本地渲染相对正方向的角度为0。
        mLivePushConfig.setHomeOrientation(pushRotation)
        if (mLivePusher.isPushing) {
            mLivePusher.config = mLivePushConfig
            mLivePusher.stopCameraPreview(true)
            mLivePusher.startCameraPreview(mCaptureView)
        }
    }
}
