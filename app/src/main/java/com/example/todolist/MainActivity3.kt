package com.example.todolist

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity3 : AppCompatActivity() {

    private lateinit var dailyTasksListView: ListView
    private lateinit var exit: View
    private lateinit var dbApp: DBApp
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        exit = findViewById(R.id.exit)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        exit.setOnClickListener {
            finish()
        }

        dailyTasksListView = findViewById(R.id.dailyTasksSec)
        dbApp = DBApp(this)

        val dailyTasks = dbApp.getAllDailyTasks().map { it.title }.toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dailyTasks)
        dailyTasksListView.adapter = adapter

        dailyTasksListView.setOnItemLongClickListener { _, _, position, _ ->
            val taskToDelete = adapter.getItem(position)
            if (taskToDelete != null) {
                AlertDialog.Builder(this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete") { _, _ ->
                        dbApp.deleteTask(taskToDelete)
                        adapter.remove(taskToDelete)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            true
        }


        swipeRefreshLayout.setOnRefreshListener {
            adapter.clear()
            adapter.addAll(dbApp.getAllDailyTasks().map { it.title })
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}


