package com.example.converteasy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.converteasy.databinding.ActivityMainBinding

class TerceiraTela : AppCompatActivity() {
    private lateinit var resultConvert: TextView
    private lateinit var variableConvert: TextView
    private val conversionRate = 5.0 // Exemplo de taxa de conversão, atualize conforme necessário

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terceira_tela)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultConvert = findViewById(R.id.result_convert)
        variableConvert = findViewById(R.id.variable_convert)
        variableConvert.text = "R$ 0,00"
        resultConvert.text = "US$ 0,00"

        findViewById<MaterialButton>(R.id.button_converter).setOnClickListener {
            changeScreen()
        }

        setNumericAndACButtonListeners()
        setDotButtonListener()
    }

    private fun changeScreen() {
        val btClick = Intent(this, SegundaTela::class.java)
        startActivity(btClick)
    }
    private fun setNumericAndACButtonListeners() {
        val buttonsIds = listOf(R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_AC)

        buttonsIds.forEach { buttonId ->
            findViewById<MaterialButton>(buttonId).setOnClickListener { button ->
                onNumericAndACButtonClick(button)
                convertAndDisplay()
            }
        }
    }

    private fun setDotButtonListener() {
        findViewById<MaterialButton>(R.id.button_dot).setOnClickListener {
            var currentValue = variableConvert.text.toString().drop(3) // Remove "R$ "
            if (!currentValue.contains(",")) {
                currentValue += ","
                variableConvert.text = "R$ $currentValue"
            }
            convertAndDisplay()
        }
    }

    private fun onNumericAndACButtonClick(button: View) {
        if (button.id == R.id.button_AC) {
            variableConvert.text = "R$ 0,00"
            resultConvert.text = "US$ 0,00"
        } else if (button is MaterialButton) {
            var currentValue = variableConvert.text.toString().drop(3) // Remove "R$ "
            if (currentValue == "0,00") currentValue = ""

            val number = if (button.text.toString() == ".") "," else button.text.toString()
            val newValue = if (currentValue.contains(",") && currentValue.split(",")[1].length < 2) {
                "$currentValue$number" // Adiciona após a vírgula até 2 dígitos
            } else if (!currentValue.contains(",")) {
                "$currentValue$number" // Continua adicionando antes da vírgula
            } else {
                currentValue // Não adiciona mais dígitos após os 2 após a vírgula
            }

            variableConvert.text = "R$ $newValue"
        }
    }

    private fun convertAndDisplay() {
        val valueInReais = variableConvert.text.toString().drop(3).replace(",", ".").toDoubleOrNull() ?: 0.0
        val convertedValue = valueInReais / conversionRate
        resultConvert.text = "US$ ${String.format("%.2f", convertedValue).replace(".", ",")}"
    }
}
