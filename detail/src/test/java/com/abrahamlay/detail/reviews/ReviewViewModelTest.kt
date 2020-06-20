package com.abrahamlay.detail.reviews

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetReviews
import com.abrahamlay.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Abraham Lay on 14/06/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ReviewViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: ReviewViewModel

    @Mock
    lateinit var command: GetReviews

    @Before
    fun setUp() {
        viewModel = ReviewViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetReview_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //given
            val movieId = 1

            //when
            viewModel.refreshReview(movieId)

            //then
            viewModel.productLiveData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}