package com.hathway.littlesprout.presentation.dashboard

import com.hathway.littlesprout.fakes.FakeProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `generateNewAdventure populates exactly 4 random categories`() {
        val viewModel = DashboardViewModel(progressRepository = null)
        
        // generated in init block natively
        val adventures = viewModel.adventureItems.value
        assertEquals(4, adventures.size)

        val allAvailableTitles = viewModel.items.value.map { it.title }
        assertTrue(adventures.all { allAvailableTitles.contains(it.title) })
    }

    @Test
    fun `generateNewAdventure resets completed categories`() {
        val viewModel = DashboardViewModel(progressRepository = null)
        val firstCategory = viewModel.adventureItems.value.first().title
        
        viewModel.recordActivityStart(firstCategory)
        assertTrue(viewModel.completedAdventureItems.value.contains(firstCategory))

        viewModel.generateNewAdventure()
        assertTrue(viewModel.completedAdventureItems.value.isEmpty())
    }

    @Test
    fun `recordActivityStart records activity in repository and updates completed items`() = runTest {
        val fakeRepo = FakeProgressRepository()
        val viewModel = DashboardViewModel(progressRepository = fakeRepo)

        val firstCategory = viewModel.adventureItems.value.first().title
        viewModel.recordActivityStart(firstCategory)

        advanceUntilIdle() // let scope.launch block process the save

        val progress = fakeRepo.getProgressFlow().value
        assertEquals(1, progress.size)
        assertEquals("START_$firstCategory", progress.first().activityId)
        assertTrue(viewModel.completedAdventureItems.value.contains(firstCategory))
    }

    @Test
    fun `recordActivityStart acts gracefully with a null repository`() {
        val viewModel = DashboardViewModel(progressRepository = null)
        
        val firstCategory = viewModel.adventureItems.value.first().title
        viewModel.recordActivityStart(firstCategory)

        assertTrue(viewModel.completedAdventureItems.value.contains(firstCategory))
    }
}
