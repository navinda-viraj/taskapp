package com.it22564436.task.fragment.add

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.it22564436.task.R
import com.it22564436.task.Task.Task
import com.it22564436.task.Task.TaskViewModel
import java.util.Calendar

class AddFragment : Fragment() {

    private lateinit var mTaskViewModel: TaskViewModel

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priorityEditText: EditText
    private lateinit var deadlineEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Initialize EditText fields
        nameEditText = view.findViewById(R.id.name)
        descriptionEditText = view.findViewById(R.id.description)
        priorityEditText = view.findViewById(R.id.priority)
        deadlineEditText = view.findViewById(R.id.deadline)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

        val deadlineEditText = view.findViewById<EditText>(R.id.deadline)
        deadlineEditText.setOnClickListener {
            showDatePickerDialog(requireContext())
        }

        return view
    }

    private fun insertDataToDatabase() {
        if (inputCheck()) {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val priority = priorityEditText.text.toString().toInt()
            val deadline = deadlineEditText.text.toString()

            // Create Task object
            val task = Task(0, name, description, priority, deadline)

            // Insert task into the database using ViewModel
            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show()
            // Navigate back to the TaskListFragment
            //findNavController().navigate(R.id.list)
            findNavController().navigate(R.id.action_addFragment_to_taskList)
        } else {
            // Display an error message or handle invalid input
            // For example, you can show a Toast message
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(): Boolean {
        val name = nameEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val priority = priorityEditText.text.toString()
        val deadline = deadlineEditText.text.toString()

        return !(name.isEmpty() || description.isEmpty() || priority.isEmpty() || deadline.isEmpty())
    }

    private fun showDatePickerDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                deadlineEditText.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }


}
