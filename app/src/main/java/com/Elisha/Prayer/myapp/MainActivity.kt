package com.Elisha.Prayer.myapp
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("Dark", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        setContentView(R.layout.activity_main)
        Pray.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein))
        Settings.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein))
        Info.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein))
        Pray.setOnClickListener{
            val rel = PreferenceManager.getDefaultSharedPreferences(this).getString("religion", "-1")?.toInt()
            if ( rel!! < 0) {
                startActivity(Intent(this, Langs::class.java))
            }else {
                val intent = Intent(this, com.Elisha.Prayer.myapp.Pray::class.java)
                intent.putExtra("Lang", rel)
                startActivity(intent)
            }
        }
        Settings.setOnClickListener{ startActivity(Intent(this, PreferenceActivity::class.java)) }
        Info.setOnClickListener{ startActivity(Intent(this, Information::class.java)) }
    }
    override fun onBackPressed() {}
}