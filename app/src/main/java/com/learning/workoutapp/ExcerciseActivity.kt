package com.learning.workoutapp


import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restDownTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseDownTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    lateinit var restView: LinearLayout
    lateinit var actionbar: Toolbar
    lateinit var progressBar: ProgressBar
    lateinit var progressText : TextView
    lateinit var upcommingExercise: TextView
    lateinit var rvStatusContainer: RecyclerView

    lateinit var exerciseView: LinearLayout
    lateinit var iv_exerciseImage: ImageView
    lateinit var exerciseProgressBar: ProgressBar
    lateinit var exerciseProgressText : TextView
    lateinit var tv_exerciseName : TextView

    private var exercises : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts : TextToSpeech? = null
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise)

        actionbar = findViewById(R.id.tb_actionBar)
        setSupportActionBar(actionbar)
        val thisActionBar = supportActionBar
        if(thisActionBar != null){
            thisActionBar.setDisplayHomeAsUpEnabled(true)
        }
        actionbar.setNavigationOnClickListener {
            onBackPressed()
        }
        tts = TextToSpeech(this, this)

        progressBar = findViewById(R.id.pb_time_progress)
        progressText = findViewById(R.id.tv_timer)
        restView = findViewById(R.id.ll_restView)
        upcommingExercise = findViewById(R.id.tv_upcomming_exercise_name)

        exerciseView = findViewById(R.id.ll_exerciseView)
        exerciseProgressBar = findViewById(R.id.pb_exercise_time_progress)
        exerciseProgressText = findViewById(R.id.tv_exercise_timer)
        iv_exerciseImage = findViewById(R.id.iv_exercise_image)
        tv_exerciseName = findViewById(R.id.tv_exercise_name)
        // exercise List:
        exercises = Exercises.getDefaultExerciseList()
        setRestTimerView()
        setExerciseStatusRecyclerView()
    }
    private fun setExerciseStatusRecyclerView(){
        rvStatusContainer = findViewById(R.id.rv_exercise_status_container)
        rvStatusContainer.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rvStatusContainer.adapter = exercises?.let { ExerciseStatusAdapter(this, it) }
    }
    private fun setRestTimer(){
//        progressBar.progress = restProgress
        restDownTimer = object : CountDownTimer(11000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressText.text = (10- restProgress).toString()
                progressBar.progress = (10 -restProgress)
            }
            override fun onFinish() {
                setExerciseView()
            }
        }.start()
    }
    private fun createPlayer(){
        try{
            player = MediaPlayer.create(this, R.raw.press_start)
            player!!.isLooping = false
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun setRestTimerView(){
        if(restDownTimer != null){
            restDownTimer!!.cancel()
            restProgress = 0
        }
        setRestTimer()
        upcommingExercise.text = exercises!![currentExercisePosition+1].name
        restView.visibility = View.VISIBLE
        exerciseView.visibility = View.GONE
        createPlayer()
        player!!.start()
    }
    private fun setExerciseTimer(){
        exerciseProgressBar.progress = exerciseProgress
        exerciseDownTimer = object : CountDownTimer(31000, 1000){
            override fun onFinish() {
                if(currentExercisePosition < 1 ){        // Todo -> exercises!!.size-1
                    setRestTimerView()
                }else{
                    Toast.makeText(this@ExcerciseActivity, "Congratulations, you finished" +
                            "your 7 min workout.", Toast.LENGTH_SHORT).show()
                    //todo
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = (30 - exerciseProgress)
                exerciseProgressText.text = (30- exerciseProgress).toString()
            }
        }
        exerciseDownTimer!!.start()
    }
    private fun setExerciseView(){
        restView.visibility = View.GONE
        //setting everything
        myExerciseUiSetup()
        exerciseView.visibility = View.VISIBLE
        if(exerciseDownTimer!= null){
            exerciseDownTimer!!.cancel()
            exerciseProgress= 0
        }
        setExerciseTimer()
    }
    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    @SuppressLint("SetTextI18n")
    private fun myExerciseUiSetup(){
        currentExercisePosition++
        val image = exercises!![currentExercisePosition].image
        val exerciseName = exercises!![currentExercisePosition].name
        val exerciseId = exercises!![currentExercisePosition].id
        iv_exerciseImage.setImageResource(image)
        tv_exerciseName.text = "$exerciseId. $exerciseName"
        speakOut(exerciseName)
    }

    override fun onBackPressed() {
        val snb = Snackbar.make(findViewById(android.R.id.content), "Do you really want to quit?", Snackbar.LENGTH_LONG)
        snb.setAction("Yes") {
            super.onBackPressed()
        }
        snb.show()
    }
    override fun onDestroy() {
        if(restDownTimer != null){
            restDownTimer!!.cancel()
        }
        if(exerciseDownTimer != null){
            exerciseDownTimer!!.cancel()
        }
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
            player!!.release()
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result= tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("tts", "Language does not support" )
            }
        }else{
            Log.e("tts", "tts initialization failed")
        }
    }
}