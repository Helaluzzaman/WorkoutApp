package com.learning.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
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
        recyclerView = findViewById(R.id.rv_history)
        setRecyclerView()
    }
    fun setRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistoryAdapter(this, getHistory())
    }
    fun getHistory(): ArrayList<History>{
        val dataBase = DataBase(this)
        Log.i("History size", dataBase.viewHistory().size.toString())
        return dataBase.viewHistory()
    }
}
class History(val id: Int, val date: String)