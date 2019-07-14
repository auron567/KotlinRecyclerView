package com.example.kotlinrecyclerview.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.ui.creature.CreatureActivity

class AllFragment : Fragment() {

    companion object {
        fun newInstance() = AllFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            startActivity(CreatureActivity.newIntent(view.context, 1))
        }
    }
}