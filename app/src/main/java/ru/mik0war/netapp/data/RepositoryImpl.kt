package ru.mik0war.netapp.data

import kotlinx.coroutines.withContext
import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.domain.Repository
import ru.mik0war.netapp.utils.DispatcherManager

class RepositoryImpl(
    private val dataSource: DataSource,
    private val dispatcherManager: DispatcherManager
): Repository {
    override suspend fun getList() = withContext(dispatcherManager.io()){
        return@withContext dataSource.getList()
    }

    override suspend fun getItem(id: Int) = withContext(dispatcherManager.io()){
        return@withContext dataSource.getItem(id)
    }

    override suspend fun createItem(item: ItemModel) = withContext(dispatcherManager.io()){
        return@withContext dataSource.createItem(item)
    }
}