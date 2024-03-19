package com.example.converteasy
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.converteasy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var resultConvert: TextView
    private lateinit var variableConvert: TextView
    private var currentUnit = Unit.CELSIUS
    //private lateinit var binding: LinearLayout
    enum class Unit(val symbol: String) {
        CELSIUS("°C"),
        KELVIN("K"),
        FAHRENHEIT("°F")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultConvert = findViewById(R.id.result_convert)
        variableConvert = findViewById(R.id.variable_convert)
        variableConvert.text = "0 °C"
        resultConvert.text = "0 K"

        findViewById<MaterialButton>(R.id.button_converter).setOnClickListener {
            changeScreen()
        }

        setNumericAndACButtonListeners()

       // binding.setOnClickListener{  }
        variableConvert.setOnClickListener {        }
        resultConvert.setOnClickListener {        }
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
            }
        }
    }

    private fun onNumericAndACButtonClick(button: View) {
        if (button.id == R.id.button_AC) {
            // Reset para os valores iniciais
            variableConvert.text = "0 °C"
            resultConvert.text = "0 K"
        } else if (button is MaterialButton) {
            val number = button.text.toString()
            val currentValue = variableConvert.text.toString().dropLast(3).trim() // Remove " °C"

            val newValue = if (currentValue == "0") {
                number // Substitui o zero
            } else {
                currentValue + number // Concatena o novo número
            }

            variableConvert.text = "$newValue °C"
            updateConversion(newValue.toDouble())
        }
    }

    private fun updateConversion(celsius: Double) {
        // Converte de Celsius para Kelvin
        val kelvinValue = celsius + 273.15
        resultConvert.text = "$kelvinValue K"
    }


}
