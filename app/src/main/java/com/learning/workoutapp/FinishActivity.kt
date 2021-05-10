package com.learning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*


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
        addDateToHistory()

    }

    fun onFinish(view: View){
        finish()
    }
    fun addDateToHistory(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val sdf = SimpleDateFormat("yyyy.MMM.dd 'at' hh:mm a", Locale.US)
        val date = sdf.format(dateTime)
        val dataBase = DataBase(this)
        val result = dataBase.addNewHistory(date)
        if(result > -1){
            Toast.makeText(this, "$date added", Toast.LENGTH_SHORT).show()
        }
        Log.i("Database:", date)
    }
}