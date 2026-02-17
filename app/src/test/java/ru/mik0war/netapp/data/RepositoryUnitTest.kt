package ru.mik0war.netapp.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.mik0war.netapp.domain.ItemModel
import ru.mik0war.netapp.utils.DispatcherManager

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryUnitTest {

    private lateinit var dataSource: DataSource
    private lateinit var dispatcherManager: DispatcherManager
    private lateinit var repository: RepositoryImpl

    @Before
    fun setUp() {
        dataSource = mockk()
        dispatcherManager = mockk()
        // Используем UnconfinedTestDispatcher для немедленного выполнения корутин
        val testDispatcher = UnconfinedTestDispatcher()
        every { dispatcherManager.io() } returns testDispatcher
        repository = RepositoryImpl(dataSource, dispatcherManager)
    }

    @Test
    fun `getList delegates to dataSource and returns its result`() = runTest {
        // given
        val expectedList = listOf(ItemModel.Success("item", 1))
        coEvery { dataSource.getList() } returns expectedList

        // when
        val result = repository.getList()

        // then
        assertEquals(expectedList, result)
        coVerify(exactly = 1) { dataSource.getList() }
        coVerify(exactly = 1) { dispatcherManager.io() }
    }

    @Test
    fun `getItem delegates to dataSource with correct id and returns its result`() = runTest {
        // given
        val id = 42
        val expectedItem = ItemModel.Success("item", id)
        coEvery { dataSource.getItem(id) } returns expectedItem

        // when
        val result = repository.getItem(id)

        // then
        assertEquals(expectedItem, result)
        coVerify(exactly = 1) { dataSource.getItem(id) }
        coVerify(exactly = 1) { dispatcherManager.io() }
    }

    @Test
    fun `createItem delegates to dataSource with correct item`() = runTest {
        // given
        val item = ItemModel.Success("new", 0)
        coEvery { dataSource.createItem(item) } returns Unit

        // when
        repository.createItem(item)

        // then

        coVerify(exactly = 1) { dataSource.createItem(item) }
        coVerify(exactly = 1) { dispatcherManager.io() }
    }
}