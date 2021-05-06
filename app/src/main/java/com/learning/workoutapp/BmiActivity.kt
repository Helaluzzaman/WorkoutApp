package com.learning.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout

class BmiActivity : AppCompatActivity() {
    private val METRIC_VIEW = "MATRIC_VIEW"
    private val US_VIEW = "US_VIEW"
    private var currentView = METRIC_VIEW

    lateinit var tb_actionbar: Toolbar
    lateinit var tl_height: TextInputLayout
    lateinit var tl_weight: TextInputLayout
    lateinit var tv_yourbmi: TextView
    lateinit var tv_bmi: TextView
    lateinit var tv_result: TextView
    lateinit var tv_resultDescription: TextView
    lateinit var b_calculate: Button
    lateinit var ll_metricInput: LinearLayout
    lateinit var ll_usInput: LinearLayout
    lateinit var etWeightPd: EditText
    lateinit var etHeightFeet: EditText
    lateinit var etHeightInch: EditText
    lateinit var llResult: LinearLayout
    private var bmi = -1f


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
        tb_actionbar.setNavigationOnClickListener {
            onBackPressed()
        }

        tl_height = findViewById(R.id.tf_height_cm)
        tl_weight = findViewById(R.id.tf_weight_kg)
        tv_yourbmi = findViewById(R.id.tv_yourBmi)
        tv_bmi= findViewById(R.id.tv_bmi_value)
        tv_result = findViewById(R.id.tv_bmi_result)
        tv_resultDescription = findViewById(R.id.tv_bmi_result_description)
        b_calculate = findViewById(R.id.b_calculate_bmi)
        llResult = findViewById(R.id.ll_resultbmi)
        b_calculate.setOnClickListener {
            if(currentView == METRIC_VIEW){
                bmi = calculateBmi()
            }else{
                bmi = calculateBmiUs()
            }
            if(bmi > 0 ){
                if(!llResult.isVisible) llResult.isVisible = true
                DisplayBmiResult(bmi)
            }
        }
        ll_metricInput = findViewById(R.id.ll_input_metric)
        ll_usInput = findViewById(R.id.ll_input_us)
        etWeightPd = findViewById(R.id.et_weight_pd)
        etHeightFeet = findViewById(R.id.et_height_ft)
        etHeightInch = findViewById(R.id.et_height_inch)

    }
    private fun calculateBmiUs(): Float{
        val weightlb = etWeightPd.text
        val heightFeet = etHeightFeet.text
        val heightInch = etHeightInch.text
        if(weightlb.isNotEmpty() && heightFeet.isNotEmpty() && heightInch.isNotEmpty()){
            val weightkg = weightlb.toString().toFloat()/2.2046
            val feet = heightFeet.toString().toFloat()
            val inch = heightInch.toString().toFloat()
            val heightm = ((feet*30.48) + (inch*2.54))/100
            return (weightkg/(heightm*heightm)).toFloat()
        }else {
            Toast.makeText(this, "Enter All input!", Toast.LENGTH_SHORT).show()
            return -1f
        }
    }

    fun OnRadioButtonClicked(view: View){
        if(view is RadioButton){
            val checked = view.isChecked
            when(view.id){
                R.id.rb_metric -> if(checked){
                    Toast.makeText(this, "metrix", Toast.LENGTH_SHORT).show()
                    if(currentView == US_VIEW){
                        ll_usInput.visibility = View.GONE
                        ll_metricInput.isVisible = true
                        currentView = METRIC_VIEW
                        llResult.visibility = View.INVISIBLE
                    }
                }
                R.id.rb_us -> if(checked){
                    Toast.makeText(this, "us", Toast.LENGTH_SHORT).show()
                    if(currentView == METRIC_VIEW){
                        ll_metricInput.visibility = View.GONE
                        ll_usInput.isVisible = true
                        currentView = US_VIEW
                        llResult.visibility = View.INVISIBLE
                    }
                }
            }

        }
    }

    fun DisplayBmiResult(bmi: Float){
        val bmiLabel:String
        var bmiDescription: String = ""
        when{
            bmi < 16 ->{
                bmiLabel = "Very severely underweight"
                bmiDescription = "Oops! You really need to take care of your better! Eat more!"
            }
            bmi < 17 -> {
                bmiLabel = "Severely underweight"
                bmiDescription = "Oops! You really need to take care of your better! Eat more!"
            }
            bmi < 18.5 -> {
                bmiLabel = "Underweight"
                bmiDescription = "Oops! You really need to take care of your better! Eat more!"
            }
            bmi < 25 -> {
                bmiLabel = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            bmi < 30 -> {
                bmiLabel = "Overweight"
                bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi < 35 ->{
                bmiLabel = "Obese Class I"
                bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi < 40 -> {
                bmiLabel  = "Obese Class II"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            bmi >= 40 -> {
                bmiLabel = "Obese Class III"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            else -> {
                bmiLabel = "unknown"
            }
        }
        tv_yourbmi.visibility = TextView.VISIBLE
        tv_bmi.text = String.format("%.2f", bmi)
        tv_result.text = bmiLabel
        tv_resultDescription.text = bmiDescription
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
                Toast.makeText(this, "Please Fill All input", Toast.LENGTH_SHORT).show()
            }
        }
        return -1f
    }
}
