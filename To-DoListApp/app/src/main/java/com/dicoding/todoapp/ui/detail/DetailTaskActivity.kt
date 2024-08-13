package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action

        val taskId = intent.getIntExtra(TASK_ID,0)

        val factory  = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this,factory)[DetailTaskViewModel::class.java]

        viewModel.setTaskId(taskId)

        viewModel.task.observe(this){task ->
            task?.let{
                detailTask(it)
            }
        }

        findViewById<Button>(R.id.btn_delete_task).setOnClickListener {
            viewModel.deleteTask()
            finish()
        }

    }

    private fun  detailTask(task: Task?){
        task?.let{
            val edTitle = findViewById<TextInputEditText>(R.id.detail_ed_title)
            val edDescription = findViewById<TextInputEditText>(R.id.detail_ed_description)
            val edDueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)

            edTitle.setText(task.title)
            edDescription.setText(task.description)
            edDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
        }
    }

}