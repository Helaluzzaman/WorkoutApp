package com.learning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var ll_startButton : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_startButton = findViewById(R.id.ll_start)
        ll_startButton.setOnClickListener {
            mainToExerciseIntent()
        }
    }
    fun mainToExerciseIntent(){
        val intent = Intent(this, ExcerciseActivity::class.java)
        startActivity(intent)
    }

}