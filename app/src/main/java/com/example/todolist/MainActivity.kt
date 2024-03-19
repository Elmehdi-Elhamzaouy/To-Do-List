package com.example.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var addTaskButton: Button
    private lateinit var tasksListView: ListView
    private lateinit var todaysTasks: LinearLayout
    private lateinit var upcoming: LinearLayout
    private lateinit var showAll: TextView
    private lateinit var dbApp: DBApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTaskButton = findViewById(R.id.AddTask)
        tasksListView = findViewById(R.id.tasks)
        todaysTasks = findViewById(R.id.TodaysTasks)
        upcoming = findViewById(R.id.upcoming)
        showAll = findViewById(R.id.showAll)
        dbApp = DBApp(this)

        addTaskButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
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
        val adapter = ArrayAdapter(this, R.layout.list_items, R.id.TextTask, taskTitles)
        tasksListView.adapter = adapter
    }

    override fun onDestroy() {
        dbApp.close()
        super.onDestroy()
    }
}
