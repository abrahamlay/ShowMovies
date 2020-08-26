package com.abrahamlay.detail.reviews


import com.abrahamlay.base.constant.Constants
import com.abrahamlay.detail.BaseHomeTestClass
import com.abrahamlay.domain.interactors.GetReviews
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
        viewModel = ReviewViewModel(command, testCoroutineRule.testCoroutineScope)
    }

    @Test
    fun givenMovieId_whenGetReview_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //given
            val movieId = 1
            val map = hashMapOf<String, Any>()
            map[GetReviews.Params.PAGE_KEY] = 1

            //when
            viewModel.getReviewData(movieId, 1, {}, {})
            //then
            BDDMockito.then(command).should()
                .addParam(GetReviews.Params(Constants.API_KEY, movieId, map))
        }
    }
}