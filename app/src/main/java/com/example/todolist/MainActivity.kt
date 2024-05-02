package com.example.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var addTaskButton: Button
    private lateinit var dailyTasks: Button
    private lateinit var tasksListView: ListView
    private lateinit var todaysTasks: LinearLayout
    private lateinit var upcoming: LinearLayout
    private lateinit var showAll: TextView
    private lateinit var todayTextView: TextView
    private lateinit var upcomingTasks: TextView
    private lateinit var dbApp: DBApp

    private val REQUEST_CODE_ADD_TASK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTaskButton = findViewById(R.id.AddTask)
        dailyTasks = findViewById(R.id.dailyTasks)
        tasksListView = findViewById(R.id.tasks)
        todaysTasks = findViewById(R.id.TodaysTasks)
        upcoming = findViewById(R.id.upcoming)
        showAll = findViewById(R.id.showAll)
        todayTextView = findViewById(R.id.today)
        upcomingTasks = findViewById(R.id.upcomingTasks)
        dbApp = DBApp(this)

        todayTextView.text = "${dbApp.getAllTodayTasks().size} tasks"
        upcomingTasks.text = "${dbApp.getAllExceptTodayTasks().size} tasks"

        addTaskButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK)
        }
        dailyTasks.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK)
        }

        showAll.setOnClickListener {
            todaysTasks.setBackgroundResource(R.drawable.radiussed)
            upcoming.setBackgroundResource(R.drawable.radiussed)
            updateListView(dbApp.getAllTasks())
        }

        todaysTasks.setOnClickListener {
            todaysTasks.setBackgroundColor(Color.parseColor("#E8EDF2"))
            upcoming.setBackgroundResource(R.drawable.radiussed)
            updateListView(dbApp.getAllTodayTasks())
        }
        upcoming.setOnClickListener {
            upcoming.setBackgroundColor(Color.parseColor("#E8EDF2"))
            todaysTasks.setBackgroundResource(R.drawable.radiussed)
            updateListView(dbApp.getAllExceptTodayTasks())
        }

        updateListView(dbApp.getAllTasks())

    }

    private fun updateListView(tasks: List<Tasks>) {
        val taskTitles = tasks.map { it.title }.toTypedArray()
        tasksListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskTitles)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            updateListView(dbApp.getAllTasks())
        }
    }

    override fun onDestroy() {
        dbApp.close()
        super.onDestroy()
    }
}



