package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBApp(context: Context) : SQLiteOpenHelper(context, "BD_List", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Tasks (title TEXT PRIMARY KEY, note TEXT, date TEXT, isDaily BOOLEAN)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Tasks")
        onCreate(db)
    }

    fun addTask(title: String, note: String, date: String, isDaily: Boolean): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("note", note)
            put("date", date)
            put("isDaily", isDaily)
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
                val isDaily = it.getInt(it.getColumnIndex("isDaily")) == 1

                val task = Tasks(title, note, date, isDaily)
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
            put("isDaily", if (task.isDaily) 1 else 0)
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
                val isDaily = it.getInt(it.getColumnIndex("isDaily")) == 1

                val task = Tasks(title, note, date, isDaily)
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
                val isDaily = it.getInt(it.getColumnIndex("isDaily")) == 1

                val task = Tasks(title, note, date, isDaily)
                tasksList.add(task)
            }
        }

        return tasksList
    }

    @SuppressLint("Range")
    fun getAllDailyTasks(): List<Tasks> {
        val tasksList = mutableListOf<Tasks>()
        val db = readableDatabase
        val cursor = db.query(
            "Tasks",
            null,
            "isDaily = ?",
            arrayOf("1"),
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex("title"))
                val note = it.getString(it.getColumnIndex("note"))
                val date = it.getString(it.getColumnIndex("date"))
                val isDaily = it.getInt(it.getColumnIndex("isDaily")) == 1

                val task = Tasks(title, note, date, isDaily)
                tasksList.add(task)
            }
        }

        return tasksList
    }


    fun deleteTask(title: String) {
        val db = writableDatabase
        db.delete("Tasks", "title = ?", arrayOf(title))
        db.close()
    }
}

