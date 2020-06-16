package com.abrahamlay.detail.reviews

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.domain.entities.ReviewModel

class ReviewPageKeyedListDataSource(
    private val movieId: Int,
    private val page: Int,
    private val dataSourceDelegate: ReviewDataSourceDelegate<ReviewModel>
) : PageKeyedDataSource<Int, ReviewModel>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ReviewModel>
    ) {
        networkState.postValue(NetworkState.LOADING)
        dataSourceDelegate.getReviewData(movieId, page, {
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReviewModel>) {
        networkState.postValue(NetworkState.LOADING)
        dataSourceDelegate.getReviewData(movieId, params.key, {
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReviewModel>) {
        //no implementation
    }

}
