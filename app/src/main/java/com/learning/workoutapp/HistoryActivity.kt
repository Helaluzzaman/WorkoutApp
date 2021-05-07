package com.learning.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class HistoryActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        toolbar = findViewById(R.id.tb_actionBar_history)
        setSupportActionBar(toolbar)
        val n_actionbar = supportActionBar
        if(n_actionbar != null){
            n_actionbar.setDisplayHomeAsUpEnabled(true)

        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}