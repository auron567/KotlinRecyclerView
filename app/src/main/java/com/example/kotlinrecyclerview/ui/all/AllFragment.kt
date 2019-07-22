package com.example.kotlinrecyclerview.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.model.CreatureStore
import com.example.kotlinrecyclerview.ui.adapter.CreatureCardAdapter
import kotlinx.android.synthetic.main.fragment_all.*

class AllFragment : Fragment() {

    companion object {
        fun newInstance() = AllFragment()
    }

    private val adapter = CreatureCardAdapter(CreatureStore.getCreatures().toMutableList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        creature_recycler_view.layoutManager = layoutManager
        creature_recycler_view.adapter = adapter
    }
}