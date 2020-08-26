package com.abrahamlay.detail.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.VideoModel
import com.abrahamlay.domain.interactors.GetVideos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class VideoViewModel : BaseViewModel {

    private var repositoryImpl: GetVideos

    @Inject
    constructor(repositoryImpl: GetVideos) : super(null) {
        this.repositoryImpl = repositoryImpl
    }

    constructor(
        repositoryImpl: GetVideos,
        testScope: CoroutineScope?
    ) : super(testScope) {
        this.repositoryImpl = repositoryImpl
    }

    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<List<VideoModel>>>()
    val videoData: LiveData<ResultState<List<VideoModel>>> = mutableRepo

    suspend fun fetchVideo(movieId: Int?) {
        repositoryImpl.addParam(GetVideos.Params(Constants.API_KEY, movieId ?: this.movieId))
            .execute(coroutineScope)
            .toResult()
            .run(mutableRepo::postValue)
    }

    fun triggerFetchVideo(movieId: Int) {
        GlobalScope.launch { fetchVideo(movieId) }
    }
}