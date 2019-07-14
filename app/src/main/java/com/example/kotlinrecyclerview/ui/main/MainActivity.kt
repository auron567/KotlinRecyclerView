package com.example.kotlinrecyclerview.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kotlinrecyclerview.R
import com.example.kotlinrecyclerview.ui.all.AllFragment
import com.example.kotlinrecyclerview.ui.favorites.FavoritesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_all -> {
                    title = getString(R.string.app_name)
                    view_pager.setCurrentItem(0, false)
                    true
                }
                R.id.navigation_favorites -> {
                    title = getString(R.string.favorites)
                    view_pager.setCurrentItem(1, false)
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupViewPager()
    }

    private fun setupNavigation() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupViewPager() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                return when (position) {
                    0 -> AllFragment.newInstance()
                    1 -> FavoritesFragment.newInstance()
                    else -> null
                }
            }

            override fun getCount() = 2
        }
    }
}