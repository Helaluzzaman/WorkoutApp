package com.learning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    lateinit var ll_startButton : LinearLayout
    lateinit var cv_bmi: CardView
    lateinit var cv_history: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_startButton = findViewById(R.id.ll_start)
        ll_startButton.setOnClickListener {
            mainToExerciseIntent()
        }
        cv_bmi = findViewById(R.id.cv_bmi)
        cv_bmi.setOnClickListener {
            mainToBmiIntent()
        }
        cv_history = findViewById(R.id.cv_history)
        cv_history.setOnClickListener {
            mainToHistoryIntent()
        }
    }

    private fun mainToBmiIntent() {
        val intent  = Intent(this, BmiActivity::class.java)
        startActivity(intent)
    }

    fun mainToExerciseIntent(){
        val intent = Intent(this, ExcerciseActivity::class.java)
        startActivity(intent)
    }
    fun mainToHistoryIntent(){
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}