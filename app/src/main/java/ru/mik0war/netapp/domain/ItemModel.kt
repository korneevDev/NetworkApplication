package ru.mik0war.netapp.domain

sealed interface ItemModel{

    data class Success(
        private val name: String,
        private val id: Int
    ) : ItemModel

    data class Error(
        private val message: String
    ) : ItemModel
}
