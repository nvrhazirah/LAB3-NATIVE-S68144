package com.example.sharedpreferencesdemo

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var greetingTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button
    private lateinit var clearButton: Button
    private lateinit var ageEditText: EditText
    private lateinit var cityEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //INITIALIZE UI COMPONENTS

        greetingTextView = findViewById(R.id.tv_greeting)
        nameEditText = findViewById(R.id.et_name)
        saveButton = findViewById(R.id.btn_save)
        loadButton = findViewById(R.id.btn_load)
        clearButton = findViewById(R.id.btn_clear)
        ageEditText = findViewById(R.id.et_age)
        cityEditText = findViewById(R.id.et_city)

        //Set up button click listeners
        saveButton.setOnClickListener {
            saveName()
        }

        loadButton.setOnClickListener {
            loadName()
        }
        clearButton.setOnClickListener {
            clearName()
        }

    }
    private fun saveName(){
        //Get the SharedPreferences object
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        //Open the ditor to write to SharedPreferences
        val editor = sharedPreferences.edit()
        val name = nameEditText.text.toString()
        editor.putString("userName",name)
        editor.apply()
    }
    private fun loadName(){
        //Get the SharedPreferences object

        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        //Open the ditor to write to SharedPreferences
        val savedName = sharedPreferences.getString("userName","No name saved")
        greetingTextView.text = "Welcome, $savedName!"
    }

    private fun clearName() {
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("userName")
        editor.apply()
        greetingTextView.text = "No name saved"
    }
    private fun saveUserInfo() {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val city = cityEditText.text.toString()

        if (name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            greetingTextView.text = "All fields are required."
        } else {
            val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("userName", name)
            editor.putString("userAge", age)
            editor.putString("userCity", city)

            editor.apply()
            greetingTextView.text = "Welcome, $name! Age: $age, City: $city"
        }
    }

    private fun loadUserInfo() {
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("userName", "No name saved")
        val age = sharedPreferences.getString("userAge", "No age saved")
        val city = sharedPreferences.getString("userCity", "No city saved")

        greetingTextView.text = "Welcome, $name! Age: $age, City: $city"
    }

}