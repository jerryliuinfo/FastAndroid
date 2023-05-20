package com.apache.fastandroid.demo.recycleview.zhy.adapter;

import android.content.Context;

import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ChatMessage;
import com.apache.fastandroid.demo.recycleview.zhy.MultiItemTypeAdapter;
import com.apache.fastandroid.demo.recycleview.zhy.viewholder.MsgComingItemDelagate;
import com.apache.fastandroid.demo.recycleview.zhy.viewholder.MsgSendItemDelagate;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage>
{
    public ChatAdapterForRv(Context context, List<ChatMessage> datas)
    {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }
}
