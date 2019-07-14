package com.example.kotlinrecyclerview.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Favorites {
    private var favorites: MutableList<Int>? = null
    private val gson = Gson()

    private const val KEY_FAVORITES = "KEY_FAVORITES"

    fun isFavorite(context: Context, creature: Creature): Boolean {
        return getFavorites(context).contains(creature.id)
    }

    fun addFavorite(context: Context, creature: Creature) {
        creature.isFavorite = true

        val favorites = getFavorites(context)
        favorites.add(creature.id)
        saveFavorites(context, KEY_FAVORITES, favorites)
    }

    fun removeFavorite(context: Context, creature: Creature) {
        creature.isFavorite = false

        val favorites = getFavorites(context)
        favorites.remove(creature.id)
        saveFavorites(context, KEY_FAVORITES, favorites)
    }

    private fun getFavorites(context: Context): MutableList<Int> {
        if (favorites == null) {
            val json = sharedPrefs(context).getString(KEY_FAVORITES, "")
            val listType = object : TypeToken<MutableList<Int>>() {}.type
            favorites = gson.fromJson(json, listType) ?: mutableListOf()
        }

        return favorites!!
    }

    private fun saveFavorites(context: Context, key: String, list: List<Int>) {
        val json = gson.toJson(list)
        sharedPrefs(context).edit().putString(key, json).apply()
    }

    private fun sharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    }
}