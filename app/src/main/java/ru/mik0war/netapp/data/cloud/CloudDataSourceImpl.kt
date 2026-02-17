package ru.mik0war.netapp.data.cloud

import ru.mik0war.netapp.data.DataSource
import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.utils.ItemMapper


class CloudDataSourceImpl(
    private val api: NetworkAPI,
    private val mapperToServer: ItemMapper<ServerDTO>,
    private val mapperToDomain: ItemMapper<ItemModel>
) : DataSource{
    override suspend fun getList() = api.getItems().map { it.map(mapperToDomain) }

    override suspend fun getItem(id: Int) = api.getItem(id).map(mapperToDomain)

    override suspend fun createItem(item: ItemModel) {
        api.createItem(item.map(mapperToServer))
    }
}
