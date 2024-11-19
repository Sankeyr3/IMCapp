package com.example.imc

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.loader.content.Loader.ForceLoadContentObserver
import org.w3c.dom.Text

class ResultActivity : AppCompatActivity() {
    private lateinit var category:TextView
    private lateinit var result:TextView
    private lateinit var description:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        initComponents()
        val imc:Float = intent.extras?.getFloat("RESULT") ?: 4f
        val values = arrayOf(
            arrayOf("#a11515", "obesidad", "Estás significativamente por encima de lo óptimo para tu peso y altura."),
            arrayOf("#ba8c02", "Sobrepeso", "Estás por encima de lo óptimo para tu peso y altura."),
            arrayOf("#4dd404", "Normal", "Tu peso y altura se encuentran en un rango óptimo para tu salud."),
            arrayOf("#35a0b0", "Peso inferior al normal", "Estás por debajo de lo óptimo para tu peso y altura."),
            arrayOf("#f9f9f9", "Valor desconocido", ""),
        )
        val index = when(imc){
            in Float.MIN_VALUE..18.5f -> 3
            in 18.5f..24.9f -> 2
            in 24.9f..29.9f -> 1
            in 24.9f..Float.MAX_VALUE -> 0
            else -> 4
        }
        result.text = String.format("%.2f", imc).toString()
        category.setTextColor(Color.parseColor(values[index][0]))
        category.text = values[index][1]
        description.text = values[index][2]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initComponents(){
        category = findViewById(R.id.category)
        result = findViewById(R.id.result)
        description = findViewById(R.id.description)
    }
}