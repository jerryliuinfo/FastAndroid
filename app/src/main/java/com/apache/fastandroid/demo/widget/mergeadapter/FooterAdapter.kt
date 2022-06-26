package masteryi.me.mergeadapterdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apache.fastandroid.R

/**
 * @author Ethan Lee
 */
class FooterAdapter(context: Context) : BaseAdapter(context) {
    companion object {
        const val STATE_LOADING = 0
        const val STATE_COMPLETE = 1
    }

    var state = STATE_LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {


        val view = when (viewType) {
            STATE_LOADING ->
                LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false)
            else ->
                LayoutInflater.from(context).inflate(R.layout.item_footer_complete, parent, false)
        }
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return state
    }

    fun updateFooterState(state: Int) {
        this.state = state
        notifyItemChanged(0)
    }
}