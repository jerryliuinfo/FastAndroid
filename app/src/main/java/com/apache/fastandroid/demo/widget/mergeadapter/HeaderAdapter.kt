package masteryi.me.mergeadapterdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apache.fastandroid.R

/**
 * @author Ethan Lee
 */
class HeaderAdapter(context: Context) : BaseAdapter(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false)
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }
}