package com.abrahamlay.base.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


/**
 * Created by Abraham Lay on 2020-06-09.
 */
abstract class BaseListFragment<DATA, VM : BaseViewModel> : BaseFragment<VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    protected var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    protected var mItemList: List<DATA>? = null

}