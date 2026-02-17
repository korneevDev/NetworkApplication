package ru.mik0war.netapp.domain

class ItemInteractor(
    private val repository: Repository
) {

    suspend fun getList(): List<ItemModel> = try {
            repository.getList()
        } catch (error: Exception){
            listOf(ItemModel.Error(error.message ?: "Unexpected error"))
        }

    suspend fun getItem(id: Int) : ItemModel = try {
        repository.getItem(id)
    } catch (error: Exception){
        ItemModel.Error(error.message ?: "Unexpected error")
    }

    suspend fun createItem(item: ItemModel) = try {
        repository.createItem(item)
        true
    } catch (_: Exception){
        false
    }
}