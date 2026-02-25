package ru.mik0war.netapp.ui

import androidx.recyclerview.widget.DiffUtil

class ItemDiffUtillCallback : DiffUtil.ItemCallback<ItemUIState>() {
    override fun areItemsTheSame(
        oldItem: ItemUIState,
        newItem: ItemUIState
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: ItemUIState,
        newItem: ItemUIState
    ): Boolean = oldItem == newItem
}