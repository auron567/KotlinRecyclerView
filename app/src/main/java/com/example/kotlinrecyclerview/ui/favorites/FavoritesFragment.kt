package com.example.kotlinrecyclerview.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.model.CreatureStore
import com.example.kotlinrecyclerview.ui.adapter.CreatureAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private val adapter = CreatureAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorites_recycler_view.layoutManager = LinearLayoutManager(activity)
        favorites_recycler_view.adapter = adapter

        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        val itemDecoration = DividerItemDecoration(ContextCompat.getColor(context!!, R.color.black), heightInPixels)

        favorites_recycler_view.addItemDecoration(itemDecoration)
    }

    override fun onResume() {
        super.onResume()

        val composites = CreatureStore.getFavoriteComposites(context!!)
        adapter.updateCreatures(composites)
    }
}