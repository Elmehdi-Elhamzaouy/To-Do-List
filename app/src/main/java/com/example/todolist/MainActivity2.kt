package com.example.todolist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var save_btn: TextView
    private lateinit var title: EditText
    private lateinit var notes: EditText
    private lateinit var date: TextView
    private lateinit var exit: View
    private lateinit var isDaily: Switch
    private lateinit var dbApp: DBApp

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        save_btn = findViewById(R.id.save)
        title = findViewById(R.id.title)
        notes = findViewById(R.id.textArea)
        date = findViewById(R.id.date)
        exit = findViewById(R.id.exit)

        isDaily = findViewById(R.id.isDaily)
        dbApp = DBApp(this)

        val taskDateView = findViewById<View>(R.id.taskDate)
        taskDateView.setOnClickListener {
            showDatePicker()
        }

        exit.setOnClickListener {
            finish()
        }

        save_btn.setOnClickListener {
            val titleText = title.text.toString()
            val note = notes.text.toString()
            val taskDate = date.text.toString()
            dbApp.addTask(titleText, note, taskDate, isDaily.isChecked)
            Toast.makeText(this,"Task Added Succefully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val currentDate = Calendar.getInstance()
                if (selectedDate == currentDate) {
                    date.text = "Today"
                } else {
                    date.text = "$dayOfMonth/${month + 1}/$year"
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

}
