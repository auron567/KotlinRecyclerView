package com.example.kotlinrecyclerview.ui.creature

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.model.Creature
import com.example.kotlinrecyclerview.model.CreatureStore
import com.example.kotlinrecyclerview.model.Favorites
import com.example.kotlinrecyclerview.ui.adapter.FoodAdapter
import kotlinx.android.synthetic.main.activity_creature.*

class CreatureActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CREATURE_ID = "EXTRA_CREATURE_ID"

        fun newIntent(context: Context, creatureId: Int) =
            Intent(context, CreatureActivity::class.java).apply {
                putExtra(EXTRA_CREATURE_ID, creatureId)
            }
    }

    private lateinit var creature: Creature
    private val adapter = FoodAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creature)

        setupActivity()
    }

    private fun setupActivity() {
        val creatureById = CreatureStore.getCreatureById(intent.getIntExtra(EXTRA_CREATURE_ID, 1))
        if (creatureById == null) {
            Toast.makeText(this, R.string.invalid_creature, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            creature = creatureById
            setupTitle()
            setupViews()
            setupFavoriteButton()
            setupFoods()
        }
    }

    private fun setupTitle() {
        title = String.format(getString(R.string.creature_activity_title), creature.nickname)
    }

    private fun setupViews() {
        with(creature) {
            creature_image.setImageResource(resources.getIdentifier(uri, null, packageName))
            creature_name.text = fullName
            planet_name.text = planet
        }
    }

    private fun setupFavoriteButton() {
        if (creature.isFavorite) {
            favorite_button.setImageResource(R.drawable.ic_favorite_black_24dp)
        } else {
            favorite_button.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }

        favorite_button.setOnClickListener {
            if (creature.isFavorite) {
                favorite_button.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                Favorites.removeFavorite(this, creature)
                Toast.makeText(this, R.string.removed_favorites, Toast.LENGTH_SHORT).show()
            } else {
                favorite_button.setImageResource(R.drawable.ic_favorite_black_24dp)
                Favorites.addFavorite(this, creature)
                Toast.makeText(this, R.string.added_favorites, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupFoods() {
        food_recycler_view.layoutManager =
            GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        food_recycler_view.adapter = adapter

        val dividerInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        val itemDecoration = FoodItemDecoration(ContextCompat.getColor(this, R.color.black), dividerInPixels)
        food_recycler_view.addItemDecoration(itemDecoration)

        val foods = CreatureStore.getCreatureFoods(creature)
        adapter.updateFoods(foods)
    }
}