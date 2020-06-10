package com.abrahamlay.home.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.domain.entities.MovieModel

class DiscoverMoviePageKeyedListDataSource(
    private val genreId: Int,
    private val page: Int,
    private val dataSourceDelegate: DiscoverDataSourceDelegate<MovieModel>
) : PageKeyedDataSource<Int, MovieModel>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) {
        networkState.postValue(NetworkState.LOADING)
        dataSourceDelegate.getDiscoverMovieByPageData(genreId, page, {
            val nextPageKey = page + 1
            callback.onResult(it, page, nextPageKey)
            networkState.postValue(NetworkState.LOADED)
        }, {
            networkState.postValue(
                NetworkState(
                    NetworkState.Status.FAILED,
                    it?.message ?: "Unknown Error"
                )
            )
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        networkState.postValue(NetworkState.LOADING)
        dataSourceDelegate.getDiscoverMovieByPageData(genreId, params.key, {
            val nextPageKey = params.key + 1
            callback.onResult(it, nextPageKey)
            networkState.postValue(NetworkState.LOADED)
        }, {
            networkState.postValue(
                NetworkState(
                    NetworkState.Status.FAILED,
                    it?.message ?: "Unknown Error"
                )
            )
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        //no implementation
    }

}
