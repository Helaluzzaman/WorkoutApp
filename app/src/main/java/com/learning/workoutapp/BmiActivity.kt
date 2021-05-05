package com.learning.workoutapp

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.roundToLong

class BmiActivity : AppCompatActivity() {
    lateinit var tb_actionbar: Toolbar
    lateinit var tl_height: TextInputLayout
    lateinit var tl_weight: TextInputLayout
    lateinit var tv_yourbmi: TextView
    lateinit var tv_result: TextView
    lateinit var tv_resultDescription: TextView
    lateinit var b_calculate: Button
    private var calculateButtonHalfEnable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        tb_actionbar = findViewById(R.id.tb_actionBar_bmi)
        setSupportActionBar(tb_actionbar)
        val n_actionbar = supportActionBar
        if(n_actionbar != null){
            n_actionbar.setDisplayHomeAsUpEnabled(true)
            n_actionbar.setDisplayShowTitleEnabled(true)
            n_actionbar.title = "BMI calculator"
        }

        tl_height = findViewById(R.id.tf_height_cm)
        tl_weight = findViewById(R.id.tf_weight_kg)
        tv_yourbmi = findViewById(R.id.tv_yourBmi)
        tv_result= findViewById(R.id.tv_bmi_result)
        tv_resultDescription = findViewById(R.id.tv_bmi_result_description)
        b_calculate = findViewById(R.id.b_calculate_bmi)
        b_calculate.setOnClickListener {
            val bmi = calculateBmi()
            if(bmi > 0 ){
                DisplayBmiResult(calculateBmi())
            }
        }
    }

    fun DisplayBmiResult(bmi: Float){
        val bmiLabel:String
        val bmiDescription: String
        when{
            bmi < 16 ->{
                bmiLabel = "Very severely underweight"
            }
            bmi < 17 -> {
                bmiLabel = "Severely underweight"
            }
            bmi < 18.5 -> {
                bmiLabel = "Underweight"
            }
            bmi < 25 -> {
                bmiLabel = "Normal"
            }
            bmi < 30 -> {
                bmiLabel = "Overweight"
            }
            bmi < 35 ->{
                bmiLabel = "Obese Class I"
            }
            bmi < 40 -> {
                bmiLabel  = "Obese Class II"
            }
            bmi >= 40 -> {
                bmiLabel = "Obese Class III"
            }
            else -> {
                bmiLabel = "unknown"
            }
        }




        tv_yourbmi.visibility = TextView.VISIBLE
        tv_result.text = String.format("%.2f", bmi)
        tv_resultDescription.text = bmiLabel
    }

    fun calculateBmi(): Float {
        val et_weight = tl_weight.editText
        val et_heith = tl_height.editText
        if (et_weight != null && et_heith != null) {
            if (et_heith.text.isNotEmpty() && et_weight.text.isNotEmpty()) {
                val weightkg = et_weight.text.toString().toFloat()
                val heightm = et_heith.text.toString().toFloat() / 100
                val bmi = weightkg / (heightm * heightm)
                return bmi
            } else {
                Toast.makeText(this, "Please Enter input First!", Toast.LENGTH_SHORT).show()
            }
        }
        return -1f
    }

    class BMI(
        val bmi: Float
    )
}
