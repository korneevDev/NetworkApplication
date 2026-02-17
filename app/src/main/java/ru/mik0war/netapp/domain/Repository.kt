package ru.mik0war.netapp.domain

interface Repository {

    suspend fun getList(): List<ItemModel>

    suspend fun getItem(id: Int): ItemModel

    suspend fun createItem(item: ItemModel)
}