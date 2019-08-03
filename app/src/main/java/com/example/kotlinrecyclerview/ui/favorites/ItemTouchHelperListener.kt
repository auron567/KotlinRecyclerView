package com.example.kotlinrecyclerview.ui.favorites

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {

    fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean
}