package com.learning.workoutapp

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
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
            DisplayBmiResult(calculateBmi())
        }

    }

    fun DisplayBmiResult(bmi: Float){
        val bmiLabel:String
        val bmiDescription: String
//        if(bmi < 16f){
//            bmiLabel = "Severe Thinness"
//        }else if( bmi < 17){
//            bmiLabel = "Modarate Thinness"
//        }else if(bmi < 18.5){
//            bmiLabel = "Mild Thinness"
//        }
        when{
            bmi < 16 ->{
                bmiLabel = "Severe Thinness"
            }
            bmi < 17 -> {
                bmiLabel = "Modarate Thinness"
            }
            bmi < 18 -> {
                bmiLabel = "Mild Thinness"
            }
            else -> {
                bmiLabel = "unknown"
            }
        }




        tv_yourbmi.visibility = TextView.VISIBLE
        tv_result.text = String.format("%.2f", bmi)
        tv_resultDescription.text = bmiLabel
    }

    fun calculateBmi(): Float{
        val weighttext = tl_weight.editText
        val heithText = tl_height.editText
        if(weighttext !=null && heithText!=null){
            val weightkg = weighttext.text.toString().toFloat()
            val heightm = heithText.text.toString().toFloat()/100
            val bmi = weightkg/(heightm*heightm)
            return bmi
        }
        return 0f
    }

    class BMI(
        val bmi: Float
    )
}