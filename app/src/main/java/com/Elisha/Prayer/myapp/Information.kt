package com.Elisha.Prayer.myapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
class Information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
    }
    override fun onBackPressed() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("back", false)){ super.onBackPressed() }
    }
}