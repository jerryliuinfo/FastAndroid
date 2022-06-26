package masteryi.me.mergeadapterdemo

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ethan Lee
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun onBindView(position: Int) {}

}