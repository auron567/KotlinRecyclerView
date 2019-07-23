package com.example.kotlinrecyclerview.ui.all

import android.os.Bundle
import android.view.*
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
    private lateinit var layoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        creature_recycler_view.layoutManager = layoutManager
        creature_recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_all, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_span_1 -> {
                showListView()
                return true
            }
            R.id.action_span_2 -> {
                showGridView()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showListView() {
        layoutManager.spanCount = 1
    }

    private fun showGridView() {
        layoutManager.spanCount = 2
    }
}