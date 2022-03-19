package com.apache.fastandroid.demo.room

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentJetpackRoomBinding
import com.apache.fastandroid.demo.sunflower.dao.PlantDao
import com.apache.fastandroid.generated.callback.OnClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.databinding.LayoutEdittextBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

/**
 * Created by Jerry on 2021/3/17.
 */
class RoomDemoFragment: BaseVBFragment<FragmentJetpackRoomBinding>(FragmentJetpackRoomBinding::inflate) {

    private lateinit var plantDao: AccountDao
    private lateinit var mAdapter: PlantAdapter
    private val atomicInteger = AtomicInteger()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        plantDao = AccountDatabase.getInstance()
        mAdapter = PlantAdapter(plantDao)
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = mAdapter

        }
        viewBinding.btnInsert.setOnClickListener {
            operation {
                val plants = Array<Account>(5){
                    val id = atomicInteger.addAndGet(1)
                    Account("name:$id", "desc:$id", id.toLong(), (id).toLong())
                }
                plantDao.insert(plants.toList())
            }
        }
        viewBinding.btnDeleteAll.setOnClickListener {
            operation {
                plantDao.deleteAll()
            }
        }

        viewBinding.btnQueryByCondition.setOnClickListener {
            lifecycleScope.launch {
                val queryList = withContext(Dispatchers.IO){
                    return@withContext plantDao.queryByAge(5)
                }
                mAdapter.setNewData(queryList)
            }
        }


        plantDao.queryAllLiveData().observe(this){
            Logger.d("queryAllLiveData onChange:${it}")
            updateView(it)
        }

    }

    private fun operation(block:() -> Unit){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                block.invoke()
            }
        }

    }

    private fun  updateView(list:List<Account>){
        mAdapter.setNewData(list)
    }

    private  class PlantAdapter(val plantDao: AccountDao):BaseQuickAdapter<Account,BaseViewHolder>(R.layout.item_plant){
        override fun convert(helper: BaseViewHolder, item: Account) {
            helper.setText(R.id.tv_id, item.id.toString())
            helper.setText(R.id.tv_name, item.name)

            helper.setOnClickListener(R.id.btn_delete
            ) { v -> showDeleteDialog(item, v) }

            helper.setOnClickListener(R.id.btn_edit
            ) { v -> showEditDialog(item, v) }

        }

        private fun showDeleteDialog(item:Account, view:View){
            val builder = MaterialAlertDialogBuilder(view.context).setTitle("确认删除")
                .setPositiveButton("确认"
                ) { dialog, which ->
                   plantDao.delete(item)
                }
            builder.show()
        }
        private fun showEditDialog(item:Account, view:View){
            val binding = LayoutEdittextBinding.inflate(LayoutInflater.from(view.context))
            val builder = MaterialAlertDialogBuilder(view.context).setView(binding.root)
                .setPositiveButton("确认"
                ) { dialog, which ->
                    item.name = binding.etName.text.toString()
                    plantDao.update(item)
                }
            builder.show()
        }


    }


}