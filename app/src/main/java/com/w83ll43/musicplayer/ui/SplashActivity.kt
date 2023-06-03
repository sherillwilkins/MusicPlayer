package com.w83ll43.musicplayer.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

import com.permissionx.guolindev.PermissionX
import com.w83ll43.musicplayer.databinding.ActivitySplashBinding
import com.w83ll43.musicplayer.logic.dao.UserDao
import com.w83ll43.musicplayer.ui.common.BaseActivity
import com.w83ll43.musicplayer.ui.login.LoginActivity
import java.util.*
import kotlin.concurrent.schedule

/**
 * 闪屏页
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding: ActivitySplashBinding
        get() = _binding!!
    private val splashDuration = 3 * 1000L

    private val alphaAnimation by lazy {
        AlphaAnimation(0.5f, 1.0f).apply {
            duration = splashDuration
            fillAfter = true
        }
    }
    private val scaleAnimation by lazy {
        ScaleAnimation(
            1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = splashDuration
            fillAfter = true
        }
    }

    override fun initBinding() {
        super.initBinding()
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

    }

    override fun initView() {
        super.initView()
        binding.ivSlogan.startAnimation(alphaAnimation)
        binding.ivSplashPicture.startAnimation(scaleAnimation)
        /**
         * 进入定时器，从dao层中取出用户信息 判断是否登录过
         */
        val isLogin: Boolean = UserDao.isLogin()
        Timer().schedule(2000) {
            val intent = Intent(
                this@SplashActivity,
                if (isLogin) MainActivity::class.java
                else LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    override fun initData() {
        super.initData()
    }

    private fun requestWriteExternalStoragePermission() {
        PermissionX.init(this@SplashActivity)
            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                val message = "需要存储权限来处理您的图片信息"
                scope.showRequestReasonDialog(deniedList, message, "确定", "取消")
            }
            .onForwardToSettings { scope, deniedList ->
                val message = "需要存储权限来处理您的图片信息"
                scope.showForwardToSettingsDialog(deniedList, message, "设置", "取消")
            }
            .request { allGranted, grantedList, deniedList ->
                requestReadPhoneStatePermission()
            }
    }

    private fun requestReadPhoneStatePermission() {
        PermissionX.init(this@SplashActivity).permissions(Manifest.permission.READ_PHONE_STATE)
            .onExplainRequestReason { scope, deniedList ->
                val message = "需要访问手机识别码等信息"
                scope.showRequestReasonDialog(deniedList, message, "确定", "拒绝")
            }
            .onForwardToSettings { scope, deniedList ->
                val message = "需要访问手机识别码等信息"
                scope.showForwardToSettingsDialog(deniedList, message, "设置取消")
            }
    }
}