package com.example.todo.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.domain.entities.Task
import com.example.todo.domain.entities.TaskKey
import com.example.todo.ui.list.adapter.TaskAdapter
import com.example.todo.ui.list.adapter.TaskListener
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    interface Listener {
        fun onItemClick(key: TaskKey)
        fun onNewTaskButtonClick()
    }

    companion object {
        fun newInstance() = ListFragment()
    }

    private val listViewModel: ListViewModel by viewModel()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initList()
        observeTasks()
        initNewTaskButton()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listViewModel
    }

    private fun observeTasks() {
        val listObserver = Observer<List<Task>> { list ->
            Log.d("MainActivity", list.toString())
            taskAdapter.submitList(list)
        }
        listViewModel.tasks.observe(viewLifecycleOwner, listObserver)
    }

    private fun initList() {
        taskList.layoutManager = LinearLayoutManager(context)
        taskList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        taskList.adapter = taskAdapter
    }

    private fun initAdapter() {
        taskAdapter = TaskAdapter(object : TaskListener {
            override fun onClick(key: TaskKey) {
                (activity as? Listener)?.onItemClick(key)
            }

            override fun onTaskDoneClick(key: TaskKey) {
                listViewModel.changeDone(key)
            }
        })
    }

    private fun initNewTaskButton() {
        newTaskButton.setOnClickListener {
            (activity as? Listener)?.onNewTaskButtonClick()
        }
    }
}

