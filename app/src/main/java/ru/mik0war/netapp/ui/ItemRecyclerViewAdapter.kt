package ru.mik0war.netapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mik0war.netapp.databinding.FragmentItemListBinding

class ItemRecyclerViewAdapter: ListAdapter<ItemUIState, ItemRecyclerViewAdapter.ViewHolder>(
    ItemDiffUtillCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: FragmentItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemUIState){
            item.show(binding)
        }

    }

}