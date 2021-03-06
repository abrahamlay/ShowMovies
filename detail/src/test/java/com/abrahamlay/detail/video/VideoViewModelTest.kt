package com.abrahamlay.detail.video

import com.abrahamlay.base.constant.Constants
import com.abrahamlay.detail.BaseHomeTestClass
import com.abrahamlay.domain.interactors.GetVideos
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
class VideoViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: VideoViewModel

    @Mock
    lateinit var command: GetVideos

    @Before
    fun setUp() {
        viewModel = VideoViewModel(command, testCoroutineRule.testCoroutineScope)
    }

    @Test
    fun givenMovieId_whenGetVideo_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //given
            val movieId = 1

            //when
            viewModel.triggerFetchVideo(movieId)

            //then
            BDDMockito.then(command).should()
                .addParam(GetVideos.Params(Constants.API_KEY, movieId))

        }
    }
}