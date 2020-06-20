package com.abrahamlay.detail.video

import com.abrahamlay.BaseHomeTestClass
import com.abrahamlay.MockitoHelper
import com.abrahamlay.domain.interactors.GetVideos
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
class VideoViewModelTest : BaseHomeTestClass() {
    //SUT
    lateinit var viewModel: VideoViewModel

    @Mock
    lateinit var command: GetVideos

    @Before
    fun setUp() {
        viewModel = VideoViewModel(command)
    }

    @Test
    fun givenMovieId_whenGetVideo_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //given
            val movieId = 1

            //when
            viewModel.refreshVideo(movieId)

            //then
            viewModel.videoData.observeForTesting {
                BDDMockito.then(command).should()
                    .execute(MockitoHelper.anyObject(), MockitoHelper.anyObject())
            }
        }
    }
}