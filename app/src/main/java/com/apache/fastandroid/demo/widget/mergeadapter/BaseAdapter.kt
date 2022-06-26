package masteryi.me.mergeadapterdemo

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ethan Lee
 */
abstract class BaseAdapter(protected val context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindView(position)
    }
}