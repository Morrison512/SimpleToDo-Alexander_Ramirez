package com.example.simpletodoalexanderramirez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongCLicked(position: Int) {
                // Remove item from list
                listOfTasks.removeAt(position)
                // Notify adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        // 1. LEt's detect when the user clicks on the add button.
//      findViewById<Button>(R.id.button).setOnClickListener {
//          //code in here is going to be exectued when the user clicks on a button
//          Log.i("Alex", "User clicked on button")
//
//      }
        loadItems()


        // lookup the recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextFile = findViewById<EditText>(R.id.addTasksField)

        findViewById<Button>(R.id.button).setOnClickListener {
            // Grab the text the user has inputted into @+id/addTasksField
            val userInputtedTask = inputTextFile.text.toString()

            // Add the string to our list of tasks: ListOfTasks
            listOfTasks.add(userInputtedTask)

            // notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // reset text field
            inputTextFile.setText("")

            saveItems()
        }
    }


    // Save the data that the user has inputed
    // Save data by writing and reading from a file

    // get the file we need
    fun getDataFile() : File {

        // every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    // save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}