package com.learning.workoutapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(val context: Context,val history: ArrayList<History>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder( items: View): RecyclerView.ViewHolder(items){
        val tvSerial = items.findViewById<TextView>(R.id.tv_history_serial)
        val tvDate = items.findViewById<TextView>(R.id.tv_history_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSerial.text = history[position].id.toString()
        holder.tvDate.text= history[position].date
    }

    override fun getItemCount(): Int {
        Log.i("history size in adapter", history.size.toString())
        return history.size
    }
}