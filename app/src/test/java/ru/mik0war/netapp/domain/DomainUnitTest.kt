package ru.mik0war.netapp.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DomainUnitTest {

    private lateinit var repository: Repository
    private lateinit var interactor: ItemInteractor

    @Before
    fun setUp() {
        repository = mockk()
        interactor = ItemInteractor(repository)
    }

    // --- getList tests ---

    @Test
    fun `getList should return list of success models when repository succeeds`() = runTest {
        // given
        val expectedList = listOf(
            ItemModel.Success("item1", 1),
            ItemModel.Success("item2", 2)
        )
        coEvery { repository.getList() } returns expectedList

        // when
        val result = interactor.getList()

        // then
        TestCase.assertEquals(expectedList, result)
        coVerify(exactly = 1) { repository.getList() }
    }

    @Test
    fun `getList should return list with one error model when repository throws exception with message`() =
        runTest {
            // given
            val errorMessage = "Network error"
            coEvery { repository.getList() } throws RuntimeException(errorMessage)

            // when
            val result = interactor.getList()

            // then
            TestCase.assertEquals(1, result.size)
            val errorItem = result[0]
            TestCase.assertEquals(ItemModel.Error(errorMessage), errorItem)
            coVerify(exactly = 1) { repository.getList() }
        }

    @Test
    fun `getList should return list with one error model with default message when exception message is null`() =
        runTest {
            // given
            coEvery { repository.getList() } throws RuntimeException()

            // when
            val result = interactor.getList()

            // then
            TestCase.assertEquals(1, result.size)
            val errorItem = result[0]
            TestCase.assertEquals(ItemModel.Error("Unexpected error"), errorItem)
            coVerify(exactly = 1) { repository.getList() }
        }

    // --- getItem tests ---

    @Test
    fun `getItem should return success model when repository succeeds`() = runTest {
        // given
        val id = 42
        val expectedItem = ItemModel.Success("item", id)
        coEvery { repository.getItem(id) } returns expectedItem

        // when
        val result = interactor.getItem(id)

        // then
        TestCase.assertEquals(expectedItem, result)
        coVerify(exactly = 1) { repository.getItem(id) }
    }

    @Test
    fun `getItem should return error model when repository throws exception with message`() =
        runTest {
            // given
            val id = 99
            val errorMessage = "Item not found"
            coEvery { repository.getItem(id) } throws RuntimeException(errorMessage)

            // when
            val result = interactor.getItem(id)

            // then
            TestCase.assertEquals(ItemModel.Error(errorMessage), result)
            coVerify(exactly = 1) { repository.getItem(id) }
        }

    @Test
    fun `getItem should return error model with default message when exception message is null`() =
        runTest {
            // given
            val id = 1
            coEvery { repository.getItem(id) } throws RuntimeException()

            // when
            val result = interactor.getItem(id)

            // then
            TestCase.assertEquals(ItemModel.Error("Unexpected error"), result)
            coVerify(exactly = 1) { repository.getItem(id) }
        }

    // --- createItem tests ---

    @Test
    fun `createItem should return true when repository succeeds`() = runTest {
        // given
        val item = ItemModel.Success("new item", 0)
        coEvery { repository.createItem(item) } returns Unit

        // when
        val result = interactor.createItem(item)

        // then
        TestCase.assertTrue(result)
        coVerify(exactly = 1) { repository.createItem(item) }
    }

    @Test
    fun `createItem should return false when repository throws exception`() = runTest {
        // given
        val item = ItemModel.Success("new item", 0)
        coEvery { repository.createItem(item) } throws RuntimeException("Failed")

        // when
        val result = interactor.createItem(item)

        // then
        TestCase.assertEquals(false, result)
        coVerify(exactly = 1) { repository.createItem(item) }
    }
}