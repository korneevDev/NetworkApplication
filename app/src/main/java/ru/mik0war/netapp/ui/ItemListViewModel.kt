package ru.mik0war.netapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mik0war.netapp.domain.ItemInteractor
import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.utils.DispatcherManager
import ru.mik0war.netapp.utils.ItemMapper

class ItemListViewModel(
    private val interactor: ItemInteractor,
    private val itemListLiveData: ItemLiveData,
    private val dispatcherManager: DispatcherManager,
    private val mapper: ItemMapper<ItemUIState>
) : ViewModel() {

    fun showList() = viewModelScope.launch(dispatcherManager.main()) {
        itemListLiveData.changeValue(interactor.getList().map { it.map(mapper) })
    }

    fun observe(owner: LifecycleOwner, adapter: ItemRecyclerViewAdapter){
        itemListLiveData.observe(owner, adapter)
    }
}