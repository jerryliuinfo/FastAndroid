package com.apache.fastandroid.demo.recycleview.zhy.viewholder

import com.apache.fastandroid.R
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ChatMessage
import com.apache.fastandroid.demo.recycleview.zhy.base.ItemViewDelegate
import com.apache.fastandroid.demo.recycleview.zhy.base.ViewHolder

/**
 * Created by zhy on 16/6/22.
 */
class MsgComingItemDelagate : ItemViewDelegate<ChatMessage> {



    override val itemViewLayoutId: Int
        get() = R.layout.base_adapter_main_chat_from_msg



     override fun isForViewType(item: ChatMessage, position: Int): Boolean {
         return item.isComMeg
     }

     override fun convert(holder: ViewHolder, chatMessage: ChatMessage, position: Int) {
         holder.setText(R.id.chat_from_content, chatMessage.content)
         holder.setText(R.id.chat_from_name, chatMessage.name)
         holder.setImageResource(R.id.chat_from_icon, chatMessage.icon)
     }
}