package com.abrahamlay.detail.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.VideoModel
import com.abrahamlay.domain.interactors.GetVideos
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class VideoViewModel @Inject constructor(val repositoryImpl: GetVideos) : BaseViewModel() {
    private var movieId: Int = 28
    private val mutableRepo = MutableLiveData<ResultState<List<VideoModel>>>()
    val videoData: LiveData<ResultState<List<VideoModel>>> = mutableRepo
    private suspend fun fetchVideo() {
        repositoryImpl.addParam(GetVideos.Params(Constants.API_KEY, movieId))
            .execute(viewModelScope)
            .toResult()
            .run(mutableRepo::postValue)
    }

    fun triggerFetchVideo(){
        viewModelScope.launch { fetchVideo() }
    }
}