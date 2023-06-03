package com.w83ll43.musicplayer.ui.setting

import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.ActivitySettingBinding
import com.w83ll43.musicplayer.ui.common.BaseActivity

class SettingActivity : BaseActivity() {
    private lateinit var _binding: ActivitySettingBinding
    private val binding:ActivitySettingBinding
        get() = _binding

    override fun initBinding() {
        super.initBinding()
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsPage, SettingFragment())
            .commit()
    }
}