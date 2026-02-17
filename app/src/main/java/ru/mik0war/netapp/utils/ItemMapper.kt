package ru.mik0war.netapp.utils

interface ItemMapper<T> {

    fun map(id: Int, name: String): T
}

interface MappedItem{

    fun <T> map(mapper: ItemMapper<T>): T
}