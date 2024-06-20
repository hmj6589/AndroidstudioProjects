package com.example.ch17_storage

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class MySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // 레이아웃이 아니라 R(리소스)
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}