package com.w83ll43.musicplayer.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.w83ll43.musicplayer.databinding.ActivityLoginBinding
import com.w83ll43.musicplayer.ui.MainActivity
import com.w83ll43.musicplayer.ui.common.BaseActivity
import com.w83ll43.musicplayer.utils.XToastUtils
import com.xuexiang.xui.utils.WidgetUtils
import com.xuexiang.xui.widget.dialog.LoadingDialog

class LoginActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private var _binding: ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding
        get() = _binding!!
    private lateinit var mLoadingDialog: LoadingDialog

    override fun initBinding() {
        super.initBinding()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        mLoadingDialog = WidgetUtils.getLoadingDialog(this)
            .setIconScale(0.4f)
            .setLoadingSpeed(8)
        setContentView(binding.root)
    }

    override fun initListener() {
        super.initListener()
        binding.sentBtn.setOnClickListener {
            viewModel.sent(binding.phone.text.toString())
        }

        binding.loginBtn.setOnClickListener {
            val data = LoginData(binding.phone.text.toString(), binding.code.text.toString())
            viewModel.login(data)
        }
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.phoneLiveData.observe(this, Observer{
            if (it.isSuccess) {
                XToastUtils.success("发送验证码成功！")
            } else {
                XToastUtils.error("发送验证码失败！")
            }
        })

        viewModel.loginDataLiveData.observe(this, Observer {
            if (it.isSuccess) {
                XToastUtils.success("登录成功！")
                val userInfo = it.getOrNull()
                if (userInfo != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                XToastUtils.error("登录失败！")
            }
        })
    }

    override fun initBar() {
        super.initBar()
        window.statusBarColor = Color.WHITE
    }
}