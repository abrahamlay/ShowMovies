package com.abrahamlay.detail

import com.abrahamlay.domain.interactors.GetDetailMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Abraham Lay on 27/08/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var command: GetDetailMovie

    @Before
    fun setUp() {
        viewModel = DetailViewModel(command, testCoroutineRule.testCoroutineScope)
    }

    @Test
    fun givenMovieId_whenGetVideo_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //given
            val movieId = 1

            //when
            viewModel.refreshDetail(movieId)

            //then
            BDDMockito.then(command).should()
                .addParam(GetDetailMovie.Params(API_KEY, movieId))

        }
    }
}