package com.abrahamlay.detail.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.base.subscriber.BaseViewModel
import com.abrahamlay.base.subscriber.DefaultSubscriber
import com.abrahamlay.base.subscriber.ResultState
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.interactors.GetReviews
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Abraham Lay on 09/06/20.
 */
class ReviewViewModel(private val repositoryImpl: GetReviews) : BaseViewModel(),
    ReviewDataSourceDelegate<ReviewModel> {
    private var movieId: Int = 28

    private var executor: Executor = Executors.newFixedThreadPool(5)

    var productLiveData: LiveData<PagedList<ReviewModel>> = MutableLiveData()

    var errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    var networkState: LiveData<NetworkState> = MutableLiveData()


    private var map: HashMap<String, Any> by HashMap<String, Any>()

    init {
        map = HashMap()
    }

    private fun fetchReviews() {
        val factory = ReviewDataSourceFactory(
            movieId,
            INITIAL_PAGE,
            this
        )
        networkState = Transformations.switchMap(
            factory.mutableLiveData
        ) { dataSource -> dataSource.networkState }

        val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(LIMIT_ITEM)
            .setEnablePlaceholders(false)
            .setPageSize(LIMIT_ITEM)
            .build()

        productLiveData =
            LivePagedListBuilder(factory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

    fun refreshReview(movieId: Int) {
        map[GetReviews.Params.PAGE_KEY] =
            INITIAL_PAGE
        this.movieId = movieId
        fetchReviews()
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val LIMIT_ITEM = 20
    }

    override fun getReviewData(
        movieId: Int,
        pagePosition: Int,
        onResult: (List<ReviewModel>) -> Unit,
        onErrorRequest: (Throwable?) -> Unit
    ) {
        map[GetReviews.Params.PAGE_KEY] = pagePosition
        repositoryImpl.execute(object : DefaultSubscriber<List<ReviewModel>>() {
            override fun onError(error: ResultState<List<ReviewModel>>) {
                if (error is ResultState.Error) {
                    if (pagePosition == INITIAL_PAGE) {
                        errorLiveData.value = error.throwable
                    } else {
                        onErrorRequest(error.throwable)
                    }
                }
            }

            override fun onSuccess(data: ResultState<List<ReviewModel>>) {
                if (data is ResultState.Success && data.data.isNotEmpty()) {
                    onResult(data.data)
                } else {
                    onErrorRequest(Throwable("There is no more item"))
                }
            }
        }, GetReviews.Params(Constants.API_KEY, movieId, map))
    }
}