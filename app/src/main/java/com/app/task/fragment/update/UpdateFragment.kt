package com.it22564436.task.fragment.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.it22564436.task.R
import com.it22564436.task.Task.Task
import com.it22564436.task.Task.TaskViewModel
import java.util.Calendar


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var updateNameEditText: EditText
    private lateinit var updateDescriptionEditText: EditText
    private lateinit var updatePriorityEditText: EditText
    private lateinit var updateDeadlineEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        updateNameEditText = view.findViewById<EditText>(R.id.updatename)
        updateDescriptionEditText = view.findViewById<EditText>(R.id.updatedescription)
        updatePriorityEditText = view.findViewById<EditText>(R.id.updatepriority)
        updateDeadlineEditText = view.findViewById<EditText>(R.id.updatedeadline)

        updateNameEditText.setText(args.currentTask.name)
        updateDescriptionEditText.setText(args.currentTask.description)
        updatePriorityEditText.setText(args.currentTask.priority.toString())
        updateDeadlineEditText.setText(args.currentTask.deadline.toString())

        val btnUpdate = view.findViewById<Button>(R.id.btnupdate)
        btnUpdate.setOnClickListener {
            updateDataToDatabase()
        }

        val btnDelete = view.findViewById<ImageButton>(R.id.deleteButton)
        btnDelete.setOnClickListener {
            deleteTask()
        }

        val deadlineEditText = view.findViewById<EditText>(R.id.updatedeadline)
        deadlineEditText.setOnClickListener {
            showDatePickerDialog(requireContext())
        }

        return view
    }



    private fun updateDataToDatabase() {

        if (inputCheck()) {
            val name = updateNameEditText.text.toString()
            val description = updateDescriptionEditText.text.toString()
            val priority = updatePriorityEditText.text.toString().toInt()
            val deadline = updateDeadlineEditText.text.toString()

            // Create Task object
            val updateTask = Task(args.currentTask.id, name, description, priority, deadline)

            // Insert task into the database using ViewModel
            mTaskViewModel.updateTask(updateTask)
            Toast.makeText(requireContext(), "Update Successfully", Toast.LENGTH_SHORT).show()
            // Navigate back to the TaskListFragment
            //findNavController().navigate(R.id.list)
            findNavController().navigate(R.id.action_updateFragment_to_taskList)
        } else {
            // Display an error message or handle invalid input
            // For example, you can show a Toast message
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(): Boolean {
        val name = updateNameEditText.text.toString()
        val description = updateDescriptionEditText.text.toString()
        val priority = updatePriorityEditText.text.toString()
        val deadline = updateDeadlineEditText.text.toString()

        return !(name.isEmpty() || description.isEmpty() || priority.isEmpty() || deadline.isEmpty())
    }

    private fun deleteTask() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(args.currentTask)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentTask.name}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_taskList)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentTask.name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentTask.name}?")
        builder.create().show()
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
                updateDeadlineEditText.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

}