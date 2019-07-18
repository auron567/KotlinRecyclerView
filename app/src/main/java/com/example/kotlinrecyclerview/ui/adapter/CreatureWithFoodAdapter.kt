package com.example.kotlinrecyclerview.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.app.inflate
import com.example.kotlinrecyclerview.model.Creature
import com.example.kotlinrecyclerview.model.CreatureStore
import com.example.kotlinrecyclerview.ui.creature.CreatureActivity
import kotlinx.android.synthetic.main.list_item_creature_with_food.view.*

class CreatureWithFoodAdapter(private val creatures: MutableList<Creature>)
    : RecyclerView.Adapter<CreatureWithFoodAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(parent.inflate(R.layout.list_item_creature_with_food)).apply {
            itemView.food_recycler_view.setRecycledViewPool(viewPool)
        }
        LinearSnapHelper().attachToRecyclerView(holder.itemView.food_recycler_view)

        return holder
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var creature: Creature
        private val adapter = FoodAdapter(mutableListOf())

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context

            itemView.creature_image.setImageResource(
                context.resources.getIdentifier(creature.thumbnail, null, context.packageName)
            )
            setupFoods()
        }

        override fun onClick(view: View) {
            val context = view.context
            val intent = CreatureActivity.newIntent(context, creature.id)
            context.startActivity(intent)
        }

        private fun setupFoods() {
            itemView.food_recycler_view.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.food_recycler_view.adapter = adapter

            val foods = CreatureStore.getCreatureFoods(creature)
            adapter.updateFoods(foods)
        }
    }
}