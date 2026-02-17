package ru.mik0war.netapp.data

import ru.mik0war.netapp.domain.ItemModel

interface DataSource {

    suspend fun getList(): List<ItemModel>

    suspend fun getItem(id: Int): ItemModel

    suspend fun createItem(item: ItemModel)
}