package com.learning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar


class FinishActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar      // vvi use androidx -> toolbar not android's
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        toolbar = findViewById(R.id.tb_actionBar_finish)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


    }

    fun onFinish(view: View) {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}