package com.example.kotlinrecyclerview.ui.adapter

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.app.inflate
import com.example.kotlinrecyclerview.model.Creature
import com.example.kotlinrecyclerview.model.Favorites
import com.example.kotlinrecyclerview.ui.creature.CreatureActivity
import com.example.kotlinrecyclerview.ui.favorites.ItemDragListener
import com.example.kotlinrecyclerview.ui.favorites.ItemSelectedListener
import com.example.kotlinrecyclerview.ui.favorites.ItemTouchHelperListener
import kotlinx.android.synthetic.main.list_item_creature.view.*
import java.util.*

class CreatureAdapter(private val creatures: MutableList<Creature>, private val itemDragListener: ItemDragListener)
    : RecyclerView.Adapter<CreatureAdapter.ViewHolder>(), ItemTouchHelperListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_creature))
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    fun updateCreatures(creatures: List<Creature>) {
        this.creatures.clear()
        this.creatures.addAll(creatures)
        notifyDataSetChanged()
    }

    override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(creatures, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(creatures, i, i - 1)
            }
        }

        Favorites.saveFavorites(recyclerView.context, creatures.map { it.id })
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, ItemSelectedListener {
        private lateinit var creature: Creature

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context

            itemView.creature_image.setImageResource(
                context.resources.getIdentifier(creature.thumbnail, null, context.packageName)
            )
            itemView.creature_name.text = creature.fullName
            itemView.creature_nickname.text = creature.nickname
            animateView(itemView)

            itemView.handle.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    itemDragListener.onItemDrag(this)
                }
                false
            }
        }

        override fun onClick(view: View) {
            val context = view.context
            val intent = CreatureActivity.newIntent(context, creature.id)
            context.startActivity(intent)
        }

        private fun animateView(viewToAnimate: View) {
            if (viewToAnimate.animation == null) {
                val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.scale_xy)
                viewToAnimate.animation = animation
            }
        }

        override fun onItemSelected() {
            itemView.list_item_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.selected_item))
        }

        override fun onItemCleared() {
            itemView.list_item_container.setBackgroundColor(0)
        }
    }
}