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
    private lateinit var listItemDecoration: RecyclerView.ItemDecoration
    private lateinit var gridItemDecoration: RecyclerView.ItemDecoration
    private var gridState = GridState.GRID

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

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.padding_standard)
        listItemDecoration = SpacingItemDecoration(1, spacingInPixels)
        gridItemDecoration = SpacingItemDecoration(2, spacingInPixels)

        creature_recycler_view.addItemDecoration(gridItemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_all, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val listMenuItem = menu.findItem(R.id.action_span_1)
        val gridMenuItem = menu.findItem(R.id.action_span_2)
        when (gridState) {
            GridState.LIST -> {
                listMenuItem.isEnabled = false
                gridMenuItem.isEnabled = true
            }
            GridState.GRID -> {
                listMenuItem.isEnabled = true
                gridMenuItem.isEnabled = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_span_1 -> {
                gridState = GridState.LIST
                updateRecyclerView(1, listItemDecoration, gridItemDecoration)
                return true
            }
            R.id.action_span_2 -> {
                gridState = GridState.GRID
                updateRecyclerView(2, gridItemDecoration, listItemDecoration)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateRecyclerView(
        spanCount: Int,
        addItemDecoration: RecyclerView.ItemDecoration,
        removeItemDecoration: RecyclerView.ItemDecoration
    ) {
        layoutManager.spanCount = spanCount
        creature_recycler_view.removeItemDecoration(removeItemDecoration)
        creature_recycler_view.addItemDecoration(addItemDecoration)
    }

    private enum class GridState {
        LIST, GRID
    }
}