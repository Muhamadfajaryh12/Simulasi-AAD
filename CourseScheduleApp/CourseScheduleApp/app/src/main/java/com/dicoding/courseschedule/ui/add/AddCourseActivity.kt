package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity:AppCompatActivity(),TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private var startTime : String = ""
    private var endTime : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this,factory)[AddCourseViewModel::class.java]

        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                onBackPressed()
            } else {
                Toast.makeText(applicationContext, "Time cant be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<TextInputEditText>(R.id.add_ed_course_name).text.toString().trim()
                val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val lecturer = findViewById<TextInputEditText>(R.id.add_ed_lecture).text.toString().trim()
                val note = findViewById<TextInputEditText>(R.id.add_ed_note).text.toString().trim()

                viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showStartTimePicker(view: View){
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, TIME_START)
    }


    fun showEndTimePicker(view: View) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, TIME_END)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
           TIME_START -> {
                findViewById<TextView>(R.id.add_tv_start_time).text = timeFormat.format(calendar.time)
                startTime = timeFormat.format(calendar.time)
            }
            TIME_END -> {
                findViewById<TextView>(R.id.add_tv_end_time).text = timeFormat.format(calendar.time)
                endTime = timeFormat.format(calendar.time)
            }
        }
    }
    companion object {
        private const val TIME_START = "startTime"
        private const val TIME_END = "endTime"
    }

}


