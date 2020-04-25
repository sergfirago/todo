package com.example.todo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.domain.entities.TaskKey
import com.example.todo.ui.details.DetailFragment
import com.example.todo.ui.list.ListFragment

class MainActivity : AppCompatActivity(), ListFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragmentWithoutStack(ListFragment.newInstance())
    }

    override fun onItemClick(key: TaskKey) {
        replaceFragment(DetailFragment.newEditModeInstance(key), "detail")
    }

    override fun onNewTaskButtonClick() {
        replaceFragment(DetailFragment.newAddModeInstance(), "newFragment")
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer, fragment
        ).addToBackStack(tag)
            .commit()
    }

    private fun replaceFragmentWithoutStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer, fragment
        )
            .commit()
    }
}
