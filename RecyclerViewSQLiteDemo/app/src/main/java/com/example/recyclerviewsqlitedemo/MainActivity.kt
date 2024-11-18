package com.example.recyclerviewsqlitedemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addUserButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recycler_view)
        addUserButton = findViewById(R.id.btn_add_user)

        addUserButton.setOnClickListener {
            showAddUserDialog()
        }

        setupRecyclerView()
        loadUserData()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadUserData() {
        val users = databaseHelper.getAllUsers()
        userAdapter = UserAdapter(users)
        recyclerView.adapter = userAdapter
    }

    private fun showAddUserDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add User")
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_user, null)
        builder.setView(dialogLayout)

        val etName = dialogLayout.findViewById<EditText>(R.id.et_name)
        val etAge = dialogLayout.findViewById<EditText>(R.id.et_age)

        builder.setPositiveButton("Add") { dialog, _ ->
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().toIntOrNull()

            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
                age == null -> {
                    Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val isUserAdded = databaseHelper.addUser(name, age)
                    if (isUserAdded) {
                        loadUserData()
                        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}
