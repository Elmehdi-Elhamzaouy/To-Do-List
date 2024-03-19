package com.example.todolist

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CursorAdapter
import android.widget.TextView

class TaskAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, 0) {
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val titleTextView = view.findViewById<TextView>(R.id.TextTask)
        val isDoneCheckBox = view.findViewById<CheckBox>(R.id.isDone)

        val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
        val isDone = cursor.getInt(cursor.getColumnIndexOrThrow("isDone")) == 1

        titleTextView.text = title
        isDoneCheckBox.isChecked = isDone
    }
}
