package com.abrahamlay.detail.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.presentation.BaseViewModel
import com.abrahamlay.base.state.NetworkState
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.domain.interactors.GetReviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by Abraham Lay on 09/06/20.
 */
class ReviewViewModel : BaseViewModel,
    ReviewDataSourceDelegate<ReviewModel> {

    private var repositoryImpl: GetReviews

    @Inject
    constructor(repositoryImpl: GetReviews) : super(null) {
        this.repositoryImpl = repositoryImpl
    }

    constructor(repositoryImpl: GetReviews, testScope: CoroutineScope?) : super(testScope) {
        this.repositoryImpl = repositoryImpl
    }

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
        GlobalScope.launch {
            fetchReviewData(movieId, pagePosition, onResult, onErrorRequest)
        }
    }

    private suspend fun fetchReviewData(
        movieId: Int,
        pagePosition: Int,
        onResult: (List<ReviewModel>) -> Unit,
        onErrorRequest: (Throwable?) -> Unit
    ) {
        map[GetReviews.Params.PAGE_KEY] = pagePosition
        repositoryImpl.addParam(GetReviews.Params(Constants.API_KEY, movieId, map))
            .execute(coroutineScope)
            .toPaginationResult(
                { success ->
                    if (success.isNotEmpty()) {
                        onResult(success)
                    } else {
                        onErrorRequest(Throwable("There is no more item"))
                    }
                }, { error ->
                    if (pagePosition == INITIAL_PAGE) {
                        errorLiveData.value = error
                    } else {
                        onErrorRequest(error)
                    }
                }
            )
    }
}