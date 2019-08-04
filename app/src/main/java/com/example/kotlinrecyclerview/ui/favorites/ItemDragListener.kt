package com.example.kotlinrecyclerview.ui.favorites

import androidx.recyclerview.widget.RecyclerView

interface ItemDragListener {

    fun onItemDrag(viewHolder: RecyclerView.ViewHolder)
}