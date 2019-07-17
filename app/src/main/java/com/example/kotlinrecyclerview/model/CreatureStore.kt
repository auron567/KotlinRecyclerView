package com.example.kotlinrecyclerview.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object CreatureStore {
    private lateinit var creatures: List<Creature>
    private lateinit var foods: List<Food>
    private val gson = Gson()

    private const val TAG = "CreatureStore"

    fun loadCreatures(context: Context) {
        val json = loadJSONFromAsset("creatures.json", context)
        val type = object : TypeToken<List<Creature>>() {}.type
        creatures = gson.fromJson(json, type)
        creatures
            .filter { Favorites.isFavorite(context, it) }
            .forEach { it.isFavorite = true }
        Log.d(TAG, "Found ${creatures.size} creatures")
    }

    fun loadFoods(context: Context) {
        val json = loadJSONFromAsset("foods.json", context)
        val type = object : TypeToken<List<Food>>() {}.type
        foods = gson.fromJson(json, type)
        Log.d(TAG, "Found ${foods.size} foods")
    }

    fun getCreatures() = creatures

    fun getFavoriteCreatures(context: Context): List<Creature> {
        return Favorites.getFavorites(context).mapNotNull { getCreatureById(it) }
    }

    fun getCreatureFoods(creature: Creature): List<Food> {
        return creature.foods.mapNotNull { getFoodById(it) }
    }

    fun getCreatureById(id: Int): Creature? {
        return creatures.firstOrNull { it.id == id }
    }

    fun getFoodById(id: Int): Food? {
        return foods.firstOrNull { it.id == id }
    }

    private fun loadJSONFromAsset(filename: String, context: Context): String? {
        var json: String? = null

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (e: IOException) {
            Log.e(TAG, "Error reading from $filename: ${e.message}")
        }

        return json
    }
}