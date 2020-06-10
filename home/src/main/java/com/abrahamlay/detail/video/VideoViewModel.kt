package com.abrahamlay.detail.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.VideoModel
import com.abrahamlay.domain.interactors.GetVideos

/**
 * Created by Abraham Lay on 09/06/20.
 */
class VideoViewModel(repositoryImpl: GetVideos) : BaseViewModel() {
    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<List<VideoModel>>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val videoData: LiveData<ResultState<List<VideoModel>>> =
        Transformations.switchMap(triggerFetch) {
            fetchVideo(repositoryImpl)
            return@switchMap mutableRepo
        }

    private fun fetchVideo(repositoryImpl: GetVideos) {
        repositoryImpl.execute(object : DefaultSubscriber<List<VideoModel>>() {

            override fun onError(error: ResultState<List<VideoModel>>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<List<VideoModel>>) {
                mutableRepo.postValue(data)
            }
        }, GetVideos.Params(Constants.API_KEY, movieId))
    }

    fun refreshVideo(movieId: Int) {
        this.movieId = movieId
        triggerFetch.value = true
    }
}