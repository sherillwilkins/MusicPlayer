package com.w83ll43.musicplayer.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.w83ll43.musicplayer.R

class SettingFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
    }
}