package com.example.todo.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.todo.R
import com.example.todo.domain.entities.TaskData
import com.example.todo.domain.entities.TaskKey
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val KEY = "com.example.todo.ui.details.DetailFragment.EXTRA_KEY"
private const val EDIT = "com.example.todo.ui.details.DetailFragment.EXTRA_EDIT"

class DetailFragment : Fragment() {

    companion object {
        fun newEditModeInstance(key: TaskKey) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY, key)
                putBoolean(EDIT, true)
            }
        }

        fun newAddModeInstance() = DetailFragment().apply {
            arguments = Bundle().apply {
                putBoolean(EDIT, false)
            }
        }
    }

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initButton()
        if (arguments?.getBoolean(EDIT) == true) {
            detailViewModel.openEditMode(arguments!!.getParcelable(KEY)!!)
        } else {
            detailViewModel.openInsertMode()
        }
        observeTask()
        observeBack()
    }

    private fun observeBack() {
        val backObserver = Observer<Boolean> {
            if (it) requireActivity().onBackPressed()
        }

        detailViewModel.back.observe(viewLifecycleOwner, backObserver)
    }

    private fun observeTask() {
        val dataObserver = Observer<TaskData> {
            renderTaskData(it)
        }

        detailViewModel.task.observe(viewLifecycleOwner, dataObserver)
    }

    private fun renderTaskData(taskData: TaskData) {
        detailText.setText(taskData.name)
        doneText.text = if (taskData.isDone) getString(R.string.done) else getString(R.string.plan)
    }

    private fun initButton() {
        val isEdit = arguments?.getBoolean(EDIT) ?: false
        if (isEdit) {
            detailsButton.text = getString(R.string.save)
        } else {
            detailsButton.text = getString(R.string.insert)
        }
        detailsButton.setOnClickListener {
            detailViewModel.buttonAction(detailText.text.toString())
        }
    }

}
