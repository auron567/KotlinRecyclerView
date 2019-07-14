package com.example.kotlinrecyclerview.app

import android.app.Application
import com.example.kotlinrecyclerview.model.CreatureStore

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CreatureStore.loadCreatures(this)
        CreatureStore.loadFoods(this)
    }
}