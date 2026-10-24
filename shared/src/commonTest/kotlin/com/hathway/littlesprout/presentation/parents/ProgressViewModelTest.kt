package com.hathway.littlesprout.presentation.parents

import com.hathway.littlesprout.domain.model.ActivityProgress
import com.hathway.littlesprout.fakes.FakeProgressRepository
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProgressViewModelTest {

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
    fun `progress aggregations work correctly with an empty progress list`() = runTest {
        val repo = FakeProgressRepository()
        val viewModel = ProgressViewModel(repo)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { 
            viewModel.totalActivitiesExplored.collect {} 
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { 
            viewModel.songsPlayed.collect {} 
        }

        advanceUntilIdle()

        assertEquals(0, viewModel.totalActivitiesExplored.value)
        assertEquals(0, viewModel.songsPlayed.value)
    }

    @Test
    fun `progress aggregations calculate accurately for heavily populated list`() = runTest {
        val repo = FakeProgressRepository()
        repo.setProgress(
            listOf(
                ActivityProgress(activityId = "s1", category = CategoryConstants.SONGS, playCount = 3, completionCount = 0),
                ActivityProgress(activityId = "s2", category = CategoryConstants.SONGS, playCount = 2, completionCount = 0),
                ActivityProgress(activityId = "a1", category = CategoryConstants.ANIMALS, playCount = 5, completionCount = 0),
                ActivityProgress(activityId = "c1", category = CategoryConstants.COLORS, playCount = 1, completionCount = 0)
            )
        )

        val viewModel = ProgressViewModel(repo)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { 
            viewModel.totalActivitiesExplored.collect {} 
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { 
            viewModel.songsPlayed.collect {} 
        }

        advanceUntilIdle()

        // Size of total items inside repo.
        assertEquals(4, viewModel.totalActivitiesExplored.value)
        // Should calculate the combined value of only SONGS plays (3 + 2).
        assertEquals(5, viewModel.songsPlayed.value)
    }
}
