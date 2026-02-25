package ru.mik0war.netapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

class ItemLiveData {
    private val listValues = MutableLiveData<List<ItemUIState>>()

    fun changeValue(newList: List<ItemUIState>){
        listValues.value = newList
    }

    fun observe(owner: LifecycleOwner, adapter: ItemRecyclerViewAdapter){
        listValues.observe(owner){
            adapter.submitList(listValues.value)
        }
    }

}