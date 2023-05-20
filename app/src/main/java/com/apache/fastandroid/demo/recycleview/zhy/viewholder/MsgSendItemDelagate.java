package com.apache.fastandroid.demo.recycleview.zhy.viewholder;

import com.apache.fastandroid.R;
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ChatMessage;
import com.apache.fastandroid.demo.recycleview.zhy.base.ItemViewDelegate;
import com.apache.fastandroid.demo.recycleview.zhy.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.base_adapter_main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position)
    {
        return !item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position)
    {
        holder.setText(R.id.chat_send_content, chatMessage.getContent());
        holder.setText(R.id.chat_send_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
    }
}
