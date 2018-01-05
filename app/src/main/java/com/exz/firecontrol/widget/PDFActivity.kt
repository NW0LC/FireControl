package com.exz.firecontrol.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.exz.firecontrol.R
import com.lihaodong.pdf.http.HttpListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_my_pdf.*
import java.lang.Exception

/**
 * Created by 史忠文
 * on 2018/1/4.
 */
class PDFActivity : BaseActivity(){
    companion object {
       var Intent_Url = "info"
       var Intent_Title = "name"
    }
    override fun initToolbar(): Boolean {
        mTitle.text = intent.getStringExtra(Intent_Title)?:""
        mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId()= R.layout.activity_my_pdf
    private var isAnimStart = false
    private var currentProgress: Int = 0
    override fun init() {
        try {
            pdfView.fromUrl(intent.getStringExtra(Intent_Url)?:"",object :HttpListener{
                override fun onFailed(e: Exception?) {

                }

                override fun onLoading(newProgress: Int) {
                    currentProgress = progressBar.progress
                    if (newProgress >= 100 && !isAnimStart) {
                        isAnimStart = true
                        progressBar.progress = newProgress
                        startDismissAnimation(progressBar.progress)
                    } else {
                       startProgressAnimation(newProgress)
                    }
                }
            })
        } catch (e: Exception) {
        }
    }

    private fun startProgressAnimation(newProgress: Int) {
        val animator = ObjectAnimator.ofInt(this.progressBar, "progress", this.currentProgress, newProgress)
        animator.duration = 300L
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }
    private fun startDismissAnimation(progress: Int) {
        val anim = ObjectAnimator.ofFloat(this.progressBar, "alpha", 1.0f, 0.0f)
        anim.duration = 1500L
        anim.interpolator = DecelerateInterpolator()
        anim.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedFraction
            val offset = 100 - progress
            progressBar.progress = (progress.toFloat() + offset.toFloat() * fraction).toInt()
        }
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                progressBar.progress = 0
                progressBar.visibility = View.GONE
                isAnimStart = false
            }
        })
        anim.start()
    }
}