@file:Suppress("DEPRECATION")
package com.Elisha.Prayer.myapp
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_langs.*
class Langs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_langs)
        fun pray(Language:Int){
            val intent = Intent(this, Pray::class.java)
            intent.putExtra("Lang", Language)
            startActivity(intent)
        }
        B1.setOnClickListener{ pray(0) }
        B2.setOnClickListener{ pray(1) }
        B3.setOnClickListener{ pray(2) }
        B4.setOnClickListener{ pray(3) }
    }
    override fun onBackPressed() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("back", false)){ super.onBackPressed() }
    }
}