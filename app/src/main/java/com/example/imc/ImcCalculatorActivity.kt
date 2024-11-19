package com.example.imc

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class ImcCalculatorActivity : AppCompatActivity() {
    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView
    private lateinit var slider:RangeSlider
    private lateinit var tvHeight:TextView
    private lateinit var displayPeso:TextView
    private lateinit var displayEdad:TextView
    private lateinit var addPeso:FloatingActionButton
    private lateinit var subtractPeso:FloatingActionButton
    private lateinit var addEdad:FloatingActionButton
    private lateinit var subtractEdad:FloatingActionButton
    private lateinit var btnCalular:AppCompatButton

    private var age = 20
    private var weight = 50
    private var height = 120f
    private var isMaleSelected:Boolean = false
    private var isFemaleSelected:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_calculator)
        initComponents()
        initListeners()
        setWeight()
        setAge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun initComponents(){
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        slider = findViewById(R.id.slider)
        tvHeight = findViewById(R.id.tvHeight)
        addEdad = findViewById(R.id.btnAddAge)
        subtractEdad = findViewById(R.id.btnSubtractAge)
        addPeso = findViewById(R.id.btnAddWeight)
        subtractPeso = findViewById(R.id.btnSubtractWeight)
        displayEdad = findViewById(R.id.displayEdad)
        displayPeso = findViewById(R.id.displayPeso)
        btnCalular = findViewById(R.id.btnCalcular)

    }
    private fun initListeners(){
        viewMale.setOnClickListener{
            isMaleSelected = true
            isFemaleSelected = false
            setGenderColor()
        }
        viewFemale.setOnClickListener{
            isFemaleSelected = true
            isMaleSelected = false
            setGenderColor()
        }
        slider.addOnChangeListener{_, value, _ ->
            tvHeight.text = DecimalFormat("#.##").format(value) + " cm"
            height = value
        }
        addPeso.setOnClickListener{
            weight++
            setWeight()
        }
        subtractPeso.setOnClickListener{
            weight--
            setWeight()
        }

        addEdad.setOnClickListener{
            age++
            setAge()
        }
        subtractEdad.setOnClickListener{
            age--
            setAge()
        }
        btnCalular.setOnClickListener{
            val result = calculateIMC()
            navigate2result(result)
        }
    }
    private fun calculateIMC():Float{
        return weight/(height*height)*4000
    }
    private fun navigate2result(result:Float){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("RESULT", result)
        startActivity(intent)
    }

    private fun setGenderColor(){
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMaleSelected))
    }
    private fun getBackgroundColor(isComponentSelected:Boolean):Int{
        val colorReference = if(isComponentSelected){
            R.color.bg_comp
        }else{
            R.color.bg_comp_sel
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun setWeight(){
        displayPeso.text = weight.toString()
    }
    private fun setAge(){
        displayEdad.text = String.format(age.toString())
    }
}