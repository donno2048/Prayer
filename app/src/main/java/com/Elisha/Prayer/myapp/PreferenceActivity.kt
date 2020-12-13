package com.Elisha.Prayer.myapp
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
class PreferenceActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.content_preference,MainPreference()).commit()
        }else{ title = savedInstanceState.getCharSequence(TAG_TITLE) }
        supportFragmentManager.addOnBackStackChangedListener { if(supportFragmentManager.backStackEntryCount == 0){ setTitle(R.string.settings) } }
        supportActionBar?.setTitle(R.string.settings)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putCharSequence(TAG_TITLE, title)
    }
    override fun onSupportNavigateUp(): Boolean {
        if(supportFragmentManager.popBackStackImmediate()){ return true }
        return super.onSupportNavigateUp()
    }
    class MainPreference:PreferenceFragmentCompat(){
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) { setPreferencesFromResource(R.xml.root_preferences,rootKey) }
    }
    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat?, pref: Preference?): Boolean {
        val extras = pref?.extras
        val fragment = pref?.fragment?.let{
            supportFragmentManager.fragmentFactory.instantiate(classLoader, it).apply {
                arguments = extras
                setTargetFragment(caller, 0)
            }
        }
        fragment?.let { supportFragmentManager.beginTransaction().replace(R.id.content_preference, it).addToBackStack(null).commit() }
        title = pref?.title
        return true
    }
    override fun onBackPressed() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("back", false)){ super.onBackPressed() }
    }
    companion object{ private const val TAG_TITLE = "Settings" }
}