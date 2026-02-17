package ru.mik0war.netapp.data

import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.domain.Repository

class RepositoryImpl : Repository {
    override suspend fun getList(): List<ItemModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getItem(id: Int): ItemModel {
        TODO("Not yet implemented")
    }

    override suspend fun createItem(item: ItemModel) {
        TODO("Not yet implemented")
    }
}