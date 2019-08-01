package com.example.kotlinrecyclerview.ui.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.app.Constants
import com.example.kotlinrecyclerview.app.inflate
import com.example.kotlinrecyclerview.model.Creature
import com.example.kotlinrecyclerview.ui.creature.CreatureActivity
import kotlinx.android.synthetic.main.list_item_creature_card.view.creature_card
import kotlinx.android.synthetic.main.list_item_creature_card.view.creature_image
import kotlinx.android.synthetic.main.list_item_creature_card.view.creature_name
import kotlinx.android.synthetic.main.list_item_creature_card_jupiter.view.*
import java.lang.IllegalArgumentException

class CreatureCardAdapter(private val creatures: MutableList<Creature>)
    : RecyclerView.Adapter<CreatureCardAdapter.ViewHolder>() {

    var scrollDirection = ScrollDirection.DOWN
    var jupiterSpanSize = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewType.OTHER.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature_card))
            ViewType.JUPITER.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature_card_jupiter))
            ViewType.MARS.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature_card_mars))
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount() = creatures.size

    override fun getItemViewType(position: Int): Int {
        return when (creatures[position].planet) {
            Constants.JUPITER -> ViewType.JUPITER.ordinal
            Constants.MARS -> ViewType.MARS.ordinal
            else -> ViewType.OTHER.ordinal
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    fun spanSizeAtPosition(position: Int): Int {
        return if (creatures[position].planet == Constants.JUPITER) {
            jupiterSpanSize
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
            setBackgroundColors()
            animateView(itemView)
        }

        override fun onClick(view: View) {
            val context = view.context
            val intent = CreatureActivity.newIntent(context, creature.id)
            context.startActivity(intent)
        }

        private fun setBackgroundColors() {
            val backgroundColor = creature.backgroundColor
            itemView.creature_card.setCardBackgroundColor(backgroundColor)

            val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
            itemView.creature_name.setTextColor(textColor)
            if (itemView.slogan != null) {
                itemView.slogan.setTextColor(textColor)
            }
        }

        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }

        private fun animateView(viewToAnimate: View) {
            if (viewToAnimate.animation == null) {
                val animId = if (scrollDirection == ScrollDirection.DOWN) {
                    R.anim.slide_from_bottom
                } else {
                    R.anim.slide_from_top
                }
                val animation = AnimationUtils.loadAnimation(viewToAnimate.context, animId)
                viewToAnimate.animation = animation
            }
        }
    }

    enum class ScrollDirection {
        UP, DOWN
    }

    enum class ViewType {
        JUPITER, MARS, OTHER
    }
}