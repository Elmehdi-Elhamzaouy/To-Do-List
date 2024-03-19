package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBApp(context: Context) : SQLiteOpenHelper(context, "BD_List", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Tasks (title TEXT PRIMARY KEY, note TEXT, date TEXT, isDone BOOLEAN)"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Tasks")
    }

    fun addTask(title: String, note: String, date: String, isDone: Boolean): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("note", note)
            put("date", date)
            put("isDone", isDone)
        }
        return db.insert("Tasks", null, values)
    }

    @SuppressLint("Range")
    fun getAllTasks(): List<Tasks> {
        val tasksList = mutableListOf<Tasks>()
        val db = readableDatabase
        val cursor = db.query(
            "Tasks",
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex("title"))
                val note = it.getString(it.getColumnIndex("note"))
                val date = it.getString(it.getColumnIndex("date"))
                val isDone = it.getInt(it.getColumnIndex("isDone")) == 1

                val task = Tasks(title, note, date, isDone)
                tasksList.add(task)
            }
        }

        return tasksList
    }

    fun updateTask(task: Tasks) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("note", task.note)
            put("date", task.date)
            put("isDone", if (task.isDone) 1 else 0)
        }

        db.update("Tasks", values, "title = ?", arrayOf(task.title))
        db.close()
    }


    @SuppressLint("Range")
    fun getAllTodayTasks(): List<Tasks> {
        val tasksList = mutableListOf<Tasks>()
        val db = readableDatabase
        val cursor = db.query(
            "Tasks",
            null,
            "date = ?",
            arrayOf("Today"),
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex("title"))
                val note = it.getString(it.getColumnIndex("note"))
                val date = it.getString(it.getColumnIndex("date"))
                val isDone = it.getInt(it.getColumnIndex("isDone")) == 1

                val task = Tasks(title, note, date, isDone)
                tasksList.add(task)
            }
        }

        return tasksList
    }

    @SuppressLint("Range")
    fun getAllExceptTodayTasks(): List<Tasks> {
        val tasksList = mutableListOf<Tasks>()
        val db = readableDatabase
        val cursor = db.query(
            "Tasks",
            null,
            "date != ?",
            arrayOf("Today"),
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex("title"))
                val note = it.getString(it.getColumnIndex("note"))
                val date = it.getString(it.getColumnIndex("date"))
                val isDone = it.getInt(it.getColumnIndex("isDone")) == 1

                val task = Tasks(title, note, date, isDone)
                tasksList.add(task)
            }
        }

        return tasksList
    }


}
