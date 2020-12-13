@file:Suppress("DEPRECATION")
package com.Elisha.Prayer.myapp
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_pray.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.log10
class Pray : AppCompatActivity() {
    private val textEngine: TextToSpeech by lazy{ TextToSpeech(this, TextToSpeech.OnInitListener{
        val speechListener = object : UtteranceProgressListener(){
            override fun onStart(p0: String?) {}
            override fun onError(utteranceId: String?) {}
            @Synchronized override fun onDone(p0: String?) {
                runOnUiThread { Stop.text = getString(R.string.Back) }
                findViewById<ImageView>(R.id.animated_image).visibility = View.INVISIBLE
            }
        }
        textEngine.setOnUtteranceProgressListener(speechListener)
    } ) }
    private fun pray(lang: Locale, text:String, speed:Float, engine: TextToSpeech = textEngine) {
        with(engine) {
            language = lang
            setSpeechRate(speed)
            val map = HashMap<String, String>()
            map[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "tts1"
            map[TextToSpeech.Engine.KEY_PARAM_VOLUME] = (PreferenceManager.getDefaultSharedPreferences(this@Pray).getInt("Volume", 0).toFloat()/100F).toString()
            speak(text, TextToSpeech.QUEUE_FLUSH, map)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textEngine.playSilence(100,TextToSpeech.QUEUE_FLUSH,null)
        setContentView(R.layout.activity_pray)
        val languages = listOf<Locale>(Locale.TAIWAN, Locale.JAPAN, Locale.ENGLISH, Locale.CHINESE)
        val texts = listOf(getString(R.string.Pthai), getString(R.string.Pshinto), getString(R.string.Pchristian), getString(R.string.Pchinese))
        Start.setOnClickListener {
            findViewById<ImageView>(R.id.animated_image).setBackgroundResource(R.drawable.animation)
            (findViewById<ImageView>(R.id.animated_image).background as AnimationDrawable).start()
            Start.startAnimation(loadAnimation(this, R.anim.fadeout))
            Stop.startAnimation(loadAnimation(this, R.anim.fadein))
            pray(languages[intent.getIntExtra("Lang", 2)], texts[intent.getIntExtra("Lang", 2)], log10(PreferenceManager.getDefaultSharedPreferences(this).getInt("Rate", 0).toFloat() + 1) + 2)
            Start.visibility = View.INVISIBLE
            Stop.visibility = View.VISIBLE
        }
        title = resources.getStringArray(R.array.religions)[intent.getIntExtra("Lang", 2) + 1]
        Stop.setOnClickListener{
            textEngine.stop()
            textEngine.shutdown()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onBackPressed() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("back", false)){ super.onBackPressed() }
    }
    override fun onDestroy() {
        textEngine.stop()
        textEngine.shutdown()
        super.onDestroy()
    }
}