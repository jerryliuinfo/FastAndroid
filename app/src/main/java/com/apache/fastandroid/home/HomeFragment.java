package com.apache.fastandroid.home;

/**
 * Created by Jerry on 2021/7/1.
 */
/*public class HomeFragment extends ARecycleViewSwipeRefreshFragmentNew<Article> implements CollectListener {
    public static final String TAG ="HomeFragment";


    public static HomeFragment newInstance(){
        return new HomeFragment();
    }



    private HomeViewModelKt mHomeViewModelKt;

    private ArticleViewModel mArticleViewModel;

    @Override
    protected  void initViewModel(){
        mHomeViewModelKt = new ViewModelProvider(this, InjectorUtil.getHomeModelFactory()).get(HomeViewModelKt.class);
        mArticleViewModel = new ViewModelProvider(this, InjectorUtil.getArticeModelFactory()).get(ArticleViewModel.class);
    }



    @NonNull
    @Override
    public BaseQuickAdapter<Article, BaseViewHolder> createAdapter() {
        return new ArticleAdapter(null, new Function2<View, Integer, Unit>() {
            @Override
            public Unit invoke(View view, Integer integer) {
                return null;
            }
        });


    }

    private int mTopArticlesLoadTimes;

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);

        mHomeViewModelKt.getHomeArticleLiveData().observe(this, new Observer<HomeArticleResponse>() {
            @Override
            public void onChanged(HomeArticleResponse listData) {
                mHomeViewModelKt.getTopArticleLiveData().observe(HomeFragment.this, new Observer<List<Article>>() {
                    @Override
                    public void onChanged(List<Article> topList) {
                        if (isFristPage() && mTopArticlesLoadTimes == 0){
                            mTopArticlesLoadTimes++;
                            if (topList != null && listData != null){
                                List<Article> totalList = new ArrayList<>();
                                totalList.addAll(topList);
                                totalList.addAll(listData.getDatas());
                                handleData(totalList,true);
                            }else if (listData != null){
                                handleData(listData.getDatas(),true);
                            }else {

                            }
                            dismissRefreshing();
                        }
                    }
                });
                //非第一页
                if (!isFristPage()){
                    if (listData != null){
                        handleData(listData.getDatas(),false);
                    }else {
                        getMAdapter().loadMoreFail();
                    }
                }

            }
        });

        mArticleViewModel.getStatus().observe(this, new Observer<CollectBean>() {
            @Override
            public void onChanged(CollectBean item) {
                getItemById(item.getId()).setCollect(item.getStatus());
                getMAdapter().notifyDataSetChanged();
            }
        });


        onRefreshData();

    }


    @Override
    public void onItemClick(@Nullable BaseQuickAdapter<Object, BaseViewHolder> adapter, @Nullable View view, Article article) {
        ArticleDetailActivity.launch(getActivity(),article.getTitle(),article.getLink());
    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter<Object, BaseViewHolder> adapter, @NonNull View view, Article article) {
//        UserInfo.getInstance().collect(getActivity(), article.getId(),HomeFragment.this);

        int id = article.getId();
        if (getItemById(id).getCollect()){
            mArticleViewModel.unCollect(id);
        }else {
            mArticleViewModel.collect(id);
        }
    }

    @Nullable
    @Override
    protected RecyclerView.LayoutManager configLayoutManager() {
        return super.configLayoutManager();
    }

    private Article getItemById(int id){
        List<Article> datas= getMAdapter().getData();
        for (Article article : datas) {
            if (id == article.getId()){
                return article;
            }
        }
        return null;
    }


    private void handleData(List<Article> data, boolean isRefresh){
        dismissRefreshing();

        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);

        } else {
            mAdapter.addData(data);
        }
        // 返回列表为空显示加载完毕
        if (size == 0 ) {
            //只有大于一页，才展示"no more data"
            mAdapter.loadMoreEnd(isFristPage());
        } else {
            mAdapter.loadMoreComplete();
        }
        mAdapter.notifyDataSetChanged();
    }

    private boolean isFristPage(){
        return mCurrentPage == 0;
    }


    private int mCurrentPage = 0;

    @Override
    public void onRefreshData() {
        showRefreshing();
        mCurrentPage = 0;
        mTopArticlesLoadTimes = 0;
        mHomeViewModelKt.loadHomeData(mCurrentPage);

    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        mHomeViewModelKt.loadHomeData(++mCurrentPage);
    }

    @Override
    public void collect(@NotNull int id) {
        if (getItemById(id).getCollect()){
            mArticleViewModel.unCollect(id);
        }else {
            mArticleViewModel.collect(id);
        }

    }


}*/

