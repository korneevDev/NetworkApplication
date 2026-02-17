package ru.mik0war.netapp.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.mik0war.netapp.data.cloud.CloudDataSourceImpl
import ru.mik0war.netapp.data.cloud.NetworkAPI
import ru.mik0war.netapp.data.cloud.ServerDTO
import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.utils.ItemMapper

class CloudDataSourceUnitTest {

    private lateinit var api: NetworkAPI
    private lateinit var mapperToServer: ItemMapper<ServerDTO>
    private lateinit var mapperToDomain: ItemMapper<ItemModel>
    private lateinit var dataSource: DataSource

    @Before
    fun setUp() {
        api = mockk()
        mapperToServer = mockk()
        mapperToDomain = mockk()
        dataSource = CloudDataSourceImpl(api, mapperToServer, mapperToDomain)
    }

    @Test
    fun `getList returns list of domain models from API`() = runTest {
        // given
        val serverDto1 = ServerDTO("name1", 1)
        val serverDto2 = ServerDTO("name2", 2)
        val domainModel1 = ItemModel.Success("name1", 1)
        val domainModel2 = ItemModel.Success("name2", 2)

        coEvery { api.getItems() } returns listOf(serverDto1, serverDto2)

        // Настраиваем маппер для каждого DTO
        every { mapperToDomain.map(1, "name1") } returns domainModel1
        every { mapperToDomain.map(2, "name2") } returns domainModel2

        // when
        val result = dataSource.getList()

        // then
        assertEquals(listOf(domainModel1, domainModel2), result)
        coVerify(exactly = 1) { api.getItems() }
        coVerify(exactly = 1) { mapperToDomain.map(1, "name1") }
        coVerify(exactly = 1) { mapperToDomain.map(2, "name2") }
    }

    @Test
    fun `getItem returns domain model from API`() = runTest {
        // given
        val id = 10
        val serverDto = ServerDTO("item10", id)
        val domainModel = ItemModel.Success("item10", id)

        coEvery { api.getItem(id) } returns serverDto
        every { mapperToDomain.map(id, "item10") } returns domainModel

        // when
        val result = dataSource.getItem(id)

        // then
        assertEquals(domainModel, result)
        coVerify(exactly = 1) { api.getItem(id) }
        coVerify(exactly = 1) { mapperToDomain.map(id, "item10") }
    }

    @Test
    fun `createItem calls API with mapped server DTO`() = runTest {
        // given
        val domainItem = ItemModel.Success("new", 42)
        val serverDto = ServerDTO("new", 42)

        every { mapperToServer.map(42, "new") } returns serverDto
        coEvery { api.createItem(serverDto) } returns serverDto

        // when
        dataSource.createItem(domainItem)

        // then
        coVerify(exactly = 1) { mapperToServer.map(42, "new") }
        coVerify(exactly = 1) { api.createItem(serverDto) }
    }

    @Test
    fun `createItem with Error throws IllegalStateException`() = runTest {
        // given
        val errorItem = ItemModel.Error("some error")

        // when
        try{
            dataSource.createItem(errorItem)
        } catch (e: Exception){
            assertTrue(e is IllegalStateException)
        }

        //ther
        coVerify(exactly = 0) { mapperToServer.map(any(), any()) }
        coVerify(exactly = 0) { api.createItem(any()) }
    }
}