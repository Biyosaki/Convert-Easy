package com.example.converteasy.ui.dashboard

import com.example.converteasy.R

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var resultConvert: TextView
    private lateinit var variableConvert: TextView
    private var currentUnit = Unit.REAIS

    enum class Unit(val symbol: String) {
        REAIS("R$"),
        DOLAR("US$"),
        FAHRENHEIT("˚F")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultConvert = findViewById(R.id.result_convert)
        variableConvert = findViewById(R.id.variable_convert)
        variableConvert.text = "R$ 0"
        resultConvert.text = "US$ 0"

        findViewById<MaterialButton>(R.id.button_converter).setOnClickListener{}
        // Setar os listeners para os botões numéricos e AC
        setNumericAndACButtonListeners()


        // Alterar unidade ao clicar nas TextViews
        variableConvert.setOnClickListener {
            // Implementação opcional para escolher a unidade para 'variableConvert'
        }
        resultConvert.setOnClickListener {
            // Implementação opcional para escolher a unidade para 'resultConvert'
        }
    }

    private fun setNumericAndACButtonListeners() {
        val buttonsIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_AC)

        buttonsIds.forEach { buttonId ->
            findViewById<MaterialButton>(buttonId).setOnClickListener { button ->
                onNumericAndACButtonClick(button)
            }
        }
    }

    private fun onNumericAndACButtonClick(button: View) {
        if (button.id == R.id.button_AC) {
            // Reset para os valores iniciais
            variableConvert.text = "R$ 0"
            resultConvert.text = "US$ 0 "
        } else if (button is MaterialButton) {
            val number = button.text.toString()
            val currentValue = variableConvert.text.toString().dropLast(3).trim() // Remove " °C"

            val newValue = if (currentValue == "0") {
                number // Substitui o zero
            } else {
                currentValue + number // Concatena o novo número
            }

            variableConvert.text = "R$ $newValue"
            updateConversion(newValue.toDouble())
        }
    }

    private fun updateConversion(celsius: Double) {
        // Converte de Celsius para Kelvin
        val kelvinValue = celsius * 4.80
        resultConvert.text = "US$ $kelvinValue "
    }


}