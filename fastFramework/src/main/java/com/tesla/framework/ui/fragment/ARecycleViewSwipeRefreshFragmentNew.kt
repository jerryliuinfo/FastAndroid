package com.tesla.framework.ui.fragment

/**
 * Created by Jerry on 2022/3/12.
 */
/*
abstract class ARecycleViewSwipeRefreshFragmentNew<T>:BaseBindingFragment<CommUiRecycleviewSwiperefreshNewBinding>(CommUiRecycleviewSwiperefreshNewBinding::inflate),
    BaseQuickAdapter.RequestLoadMoreListener {

    protected lateinit var mAdapter:BaseQuickAdapter<T, BaseViewHolder>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mAdapter = createAdapter()

        mBinding.recycleview.apply {
            setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                onScrollChange(v,scrollX,scrollY,oldScrollX,oldScrollY)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    onRecycleViewScrollStateChanged(recyclerView, newState)
                }
            })
            adapter = mAdapter
        }
        mAdapter.run {
            setOnLoadMoreListener(this@ARecycleViewSwipeRefreshFragmentNew, mBinding.recycleview)
            setOnItemClickListener { adapter, view, position ->
                onItemClick(adapter, view, data[position])
            }
            setOnItemChildClickListener { adapter, view, position ->
                onItemChildClick(adapter,view,data[position])
            }
        }
        setupSwipeRefreshLayout()

    }

    open fun onItemChildClick(
        adapter: BaseQuickAdapter<Any, BaseViewHolder>, view: View, t: T
    ) {

    }

    open fun onItemClick(adapter: BaseQuickAdapter<Any, BaseViewHolder>?, view: View?, t: T) {

    }

    abstract fun createAdapter():BaseQuickAdapter<T, BaseViewHolder>


    override fun onLoadMoreRequested() {

    }



    open fun onRefreshData() {
    }

    open fun onScrollChange(
        v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int,
        oldScrollY: Int
    ) {

    }

    open fun onRecycleViewScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

    }


    */
/**
     * 默认是LinearLayoutManager
     *
     * @return
     *//*

    protected open fun configLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(activity)
    }

    */
/**
     * 获取第一个可见的position
     * @return
     *//*

    protected open fun getFirstVisiblePosition(): Int {
        if (mBinding.recycleview.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = mBinding.recycleview.layoutManager as LinearLayoutManager
            return linearLayoutManager.findFirstVisibleItemPosition()
        }
        return 0
    }

    protected fun setupSwipeRefreshLayout() {
        mBinding.swipeRefreshLayout.apply {
            setOnRefreshListener{
                onRefreshData()
            }
            setColorSchemeResources(
                    R.color.holo_blue_bright,
                    R.color.holo_green_light,
                    R.color.holo_orange_light,
                    R.color.holo_red_light
                )
        }


    }



    open fun showRefreshing() {
        mBinding.swipeRefreshLayout.run {
            if (!isRefreshing){
                isRefreshing = true
            }
        }

    }

    open fun dismissRefreshing() {
        mBinding.swipeRefreshLayout.run {
            if (isRefreshing){
                isRefreshing = false
            }
        }
    }
}*/
