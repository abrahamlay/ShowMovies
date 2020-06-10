package com.abrahamlay.home.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.domain.interactors.GetDiscoverMoviesByGenre

/**
 * Created by Abraham Lay on 09/06/20.
 */
class DiscoverViewModel(repositoryImpl: GetDiscoverMoviesByGenre) : BaseViewModel() {
    private val mutableRepo = MutableLiveData<ResultState<List<MovieModel>>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val movieData: LiveData<ResultState<List<MovieModel>>> =
        Transformations.switchMap(triggerFetch) {
            fetchMovie(repositoryImpl)
            return@switchMap mutableRepo
        }

    var map: HashMap<String, Any> by HashMap<String, Any>()
    init {
        map = HashMap()
    }

    private fun fetchMovie(repositoryImpl: GetDiscoverMoviesByGenre) {
        repositoryImpl.execute(object : DefaultSubscriber<List<MovieModel>>() {
            override fun onError(error: ResultState<List<MovieModel>>) {
                mutableRepo.postValue(error)
            }

            override fun onSuccess(data: ResultState<List<MovieModel>>) {
                mutableRepo.postValue(data)
            }
        }, GetDiscoverMoviesByGenre.Params(Constants.API_KEY, map))
    }

    fun refreshMovie(genreId: Int) {
        map[GetDiscoverMoviesByGenre.Params.PAGE_KEY] = INITIAL_PAGE
        map[GetDiscoverMoviesByGenre.Params.GENRE_KEY] = genreId
        triggerFetch.value = true
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}