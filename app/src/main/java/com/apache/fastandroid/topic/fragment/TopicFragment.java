package com.apache.fastandroid.topic.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.R;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class TopicFragment extends ARecycleViewSwipeRefreshFragment<TopicBean,TopicBeans,TopicBean> {

    public static TopicFragment newInstance(){
        TopicFragment fragment = new TopicFragment();
        return fragment;
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadTopicTask(mode).execute();
    }

    @Override
    protected IPaging<TopicBean, TopicBeans> newPaging() {
        return new IndexPaging<>();
    }

    class LoadTopicTask extends APagingTask<Void,Void,TopicBeans>{


        public LoadTopicTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TopicBean> parseResult(TopicBeans topicBeans) {
            return topicBeans.list;
        }

        @Override
        protected TopicBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            int offset = 0;
            if (!TextUtils.isEmpty(nextPage)){
                offset = Integer.parseInt(nextPage);
            }

            try {
                TopicBeans beans =  TopicSDK.newInstance().getTopicsList(null,null,offset,20);
                NLog.d(TAG, "workInBackground beans = %s", beans);
                return beans;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    @Override
    protected IItemViewCreator<TopicBean> configItemViewCreator() {
        return new ItemViewCreator();
    }

    class ItemViewCreator implements IItemViewCreator<TopicBean>{

        @Override
        public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return inflater.inflate(R.layout.topic_item_topic,parent,false);
        }

        @Override
        public IITemView<TopicBean> newItemView(View contentView, int viewType) {
            return new TopicItemView(getActivity(),contentView);
        }
    }

    /*final User user = bean.getUser();
        holder.setText(R.id.username, user.getLogin());
        holder.setText(R.id.node_name, bean.getNode_name());
        holder.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));
        holder.setText(R.id.title, bean.getTitle());

    // 加载头像
    ImageView imageView = holder.get(R.id.avatar);
    String url = user.getAvatar_url();
    String url2 = url;
        if (url.contains("diycode"))    // 添加判断，防止替换掉其他网站掉图片
    url2 = url.replace("large_avatar", "avatar");
        Glide.with(mContext).load(url2).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);

    String state = "评论 " + bean.getReplies_count();
        holder.setText(R.id.state, state);*/


}
