package com.abrahamlay.base.state

/**
 * Created by Abraham Lay on 10/06/20.
 */
class NetworkState(val status: Status, val message: String) {

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
    }
}