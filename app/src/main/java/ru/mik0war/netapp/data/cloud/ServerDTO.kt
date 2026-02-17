package ru.mik0war.netapp.data.cloud

import com.google.gson.annotations.SerializedName
import ru.mik0war.netapp.utils.ItemMapper
import ru.mik0war.netapp.utils.MappedItem

data class ServerDTO(
    @SerializedName("object_name")
    private val name: String,
    private val id: Int
): MappedItem {

    override fun <T> map(mapper: ItemMapper<T>): T = mapper.map(id, name)
}
