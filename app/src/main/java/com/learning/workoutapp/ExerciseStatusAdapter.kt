package com.learning.workoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseStatusAdapter(val context: Context, val items: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.customViewHolder>() {

    class customViewHolder(customView: View): RecyclerView.ViewHolder(customView){
        val tv_view = customView.findViewById<TextView>(R.id.tv_exercise_id_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customViewHolder {
        return customViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.exercise_status_number,
                        parent,
                        false))
    }

    override fun onBindViewHolder(holder: customViewHolder, position: Int) {
        holder.tv_view.text = items[position].id.toString()
    }
    override fun getItemCount(): Int {
        return items.size
    }

}
