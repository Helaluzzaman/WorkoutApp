package com.learning.workoutapp


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity() {
    private var restDownTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseDownTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    lateinit var restView: LinearLayout
    lateinit var actionbar: Toolbar
    lateinit var progressBar: ProgressBar
    lateinit var progressText : TextView

    lateinit var exerciseView: LinearLayout
    lateinit var iv_exerciseImage: ImageView
    lateinit var exerciseProgressBar: ProgressBar
    lateinit var exerciseProgressText : TextView
    lateinit var tv_exerciseName : TextView

    private var exercises : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1


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

        progressBar = findViewById(R.id.pb_time_progress)
        progressText = findViewById(R.id.tv_timer)
        setRestTimerView()
        restView = findViewById(R.id.ll_restView)


        exerciseView = findViewById(R.id.ll_exerciseView)
        exerciseProgressBar = findViewById(R.id.pb_exercise_time_progress)
        exerciseProgressText = findViewById(R.id.tv_exercise_timer)
        iv_exerciseImage = findViewById(R.id.iv_exercise_image)
        tv_exerciseName = findViewById(R.id.tv_exercise_name)
        // exercise List:
        exercises = Exercises.getDefaultExerciseList()
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
    private fun setRestTimerView(){
        if(restDownTimer != null){
            restDownTimer!!.cancel()
        }
        setRestTimer()
    }
    private fun setAgainRestTimerView(){
        restProgress = 0
        exerciseProgress= 0
        restView.visibility = View.VISIBLE
        exerciseView.visibility = View.GONE
        if(restDownTimer != null){
            restDownTimer!!.cancel()
        }
        setRestTimer()
    }

    private fun setExerciseTimer(){
        exerciseProgressBar.progress = exerciseProgress
        exerciseDownTimer = object : CountDownTimer(30000, 1000){
            override fun onFinish() {
                setAgainRestTimerView()
            }

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = (30- exerciseProgress)
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
        }
        setExerciseTimer()
    }
    @SuppressLint("SetTextI18n")
    private fun myExerciseUiSetup(){
        currentExercisePosition++
        if(currentExercisePosition < exercises!!.size ){
            val image = exercises!![currentExercisePosition].image
            val exerciseName = exercises!![currentExercisePosition].name
            val exerciseId = exercises!![currentExercisePosition].id
            iv_exerciseImage.setImageResource(image)
            tv_exerciseName.text = "$exerciseId. $exerciseName"
        }else{
            Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show()
            currentExercisePosition = -1
        }
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
        super.onDestroy()
    }
}