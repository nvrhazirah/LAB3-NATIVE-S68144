package com.example.sqlitedemo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var idEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var addButton: Button
    private lateinit var viewButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var sortCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        idEditText = findViewById(R.id.et_id)
        nameEditText = findViewById(R.id.et_name)
        ageEditText = findViewById(R.id.et_age)
        resultTextView = findViewById(R.id.tv_result)
        addButton = findViewById(R.id.btn_add)
        viewButton = findViewById(R.id.btn_view)
        updateButton = findViewById(R.id.btn_update)
        deleteButton = findViewById(R.id.btn_delete)
        sortCheckBox = findViewById(R.id.sortCheckBox)

        addButton.setOnClickListener { addUser() }
        viewButton.setOnClickListener { viewUsers() }
        updateButton.setOnClickListener { updateUser() }
        deleteButton.setOnClickListener { deleteUser() }
    }

    private fun addUser() {
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString().toIntOrNull()
        if (name.isNotEmpty() && age != null) {
            val success = databaseHelper.addUser(name, age)
            if (success) {
                Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show()
                nameEditText.text.clear()
                ageEditText.text.clear()
            } else {
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter valid name and age", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewUsers() {
        val sorted = sortCheckBox.isChecked
        val users = databaseHelper.getAllUsers(sorted)
        resultTextView.text = if (users.isNotEmpty()) {
            users.joinToString("\n")
        } else {
            "No users found"
        }
    }

    private fun updateUser() {
        val id = idEditText.text.toString().toIntOrNull()
        val newName = nameEditText.text.toString()
        val newAge = ageEditText.text.toString().toIntOrNull()
        if (id != null && newName.isNotEmpty() && newAge != null) {
            val success = databaseHelper.updateUser(id, newName, newAge)
            if (success) {
                Toast.makeText(this, "User updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to update user. User ID may not exist.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter valid ID, name, and age.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteUser() {
        val id = idEditText.text.toString().toIntOrNull()
        if (id != null) {
            val success = databaseHelper.deleteUser(id)
            if (success) {
                Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to delete user.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter a valid ID.", Toast.LENGTH_SHORT).show()
        }
    }
}
