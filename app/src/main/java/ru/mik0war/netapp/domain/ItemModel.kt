package ru.mik0war.netapp.domain

import ru.mik0war.netapp.utils.ItemMapper
import ru.mik0war.netapp.utils.MappedItem

sealed interface ItemModel : MappedItem{

    data class Success(
        private val name: String,
        private val id: Int
    ) : ItemModel {
        override fun <T> map(mapper: ItemMapper<T>): T = mapper.map(id, name)
    }

    data class Error(
        private val message: String
    ) : ItemModel {
        override fun <T> map(mapper: ItemMapper<T>): T {
            throw IllegalStateException()
        }
    }
}
