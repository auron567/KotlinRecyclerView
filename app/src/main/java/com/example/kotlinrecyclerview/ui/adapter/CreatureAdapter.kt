package com.example.kotlinrecyclerview.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.app.inflate
import com.example.kotlinrecyclerview.model.CompositeItem
import com.example.kotlinrecyclerview.model.Creature
import com.example.kotlinrecyclerview.ui.creature.CreatureActivity
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_planet_header.view.*
import java.lang.IllegalArgumentException

class CreatureAdapter(private val compositeItems: MutableList<CompositeItem>)
    : RecyclerView.Adapter<CreatureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_planet_header))
            ViewType.CREATURE.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature))
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount() = compositeItems.size

    override fun getItemViewType(position: Int): Int {
        return if (compositeItems[position].isHeader) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.CREATURE.ordinal
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(compositeItems[position])
    }

    fun updateCreatures(creatures: List<CompositeItem>) {
        this.compositeItems.clear()
        this.compositeItems.addAll(creatures)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var creature: Creature

        fun bind(compositeItem: CompositeItem) {
            if (compositeItem.isHeader) {
                itemView.header_name.text = compositeItem.header.name
            } else {
                this.creature = compositeItem.creature
                val context = itemView.context

                itemView.creature_image.setImageResource(
                    context.resources.getIdentifier(creature.thumbnail, null, context.packageName)
                )
                itemView.creature_name.text = creature.fullName
                itemView.creature_nickname.text = creature.nickname
                itemView.setOnClickListener(this)
                animateView(itemView)
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
    }

    enum class ViewType {
        HEADER, CREATURE
    }
}