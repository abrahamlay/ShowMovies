package com.abrahamlay.detail.reviews

interface ReviewDataSourceDelegate<Data> {

    fun getReviewData(
        movieId: Int,
        pagePosition: Int,
        onResult: (List<Data>) -> Unit,
        onErrorRequest: (Throwable?) -> Unit
    )
}
