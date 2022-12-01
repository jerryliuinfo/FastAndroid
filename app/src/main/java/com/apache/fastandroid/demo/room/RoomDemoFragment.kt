package com.apache.fastandroid.demo.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentJetpackRoomBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.databinding.LayoutEdittextBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Jerry on 2021/3/17.
 */
class RoomDemoFragment: BaseBindingFragment<FragmentJetpackRoomBinding>(FragmentJetpackRoomBinding::inflate) {

    private lateinit var plantDao: AccountDao
    private lateinit var mAdapter: PlantAdapter
    private val atomicInteger = AtomicInteger()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        plantDao = AccountDatabase.getInstance()
        mAdapter = PlantAdapter(plantDao)
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = mAdapter

        }
        mBinding.btnInsert.setOnClickListener {
            operation {
                val plants = Array<Account>(5){
                    val id = atomicInteger.addAndGet(1)
                    Account("name:$id", "desc:$id", id.toLong(), (id).toLong())
                }
                //Room 的操作本来就是主线安全的，因此没必要切换线程
                plantDao.insert(plants.toList())

                println("insert thread: ${Thread.currentThread().name}")
            }
        }
        mBinding.btnDeleteAll.setOnClickListener {
            operation {
                plantDao.deleteAll()
                println("deleteAll thread: ${Thread.currentThread().name}")
            }
        }

        mBinding.btnQueryByCondition.setOnClickListener {
            lifecycleScope.launch {
                val queryList = withContext(Dispatchers.IO){
                    return@withContext plantDao.queryByAge(5)
                }
                println("btnQueryByCondition thread: ${Thread.currentThread().name}")

                mAdapter.setNewData(queryList.toMutableList())
            }
        }


        plantDao.queryAllLiveData().observe(this){
            Logger.d("queryAllLiveData onChange:${it}")
            updateView(it)
        }

    }

    private fun operation(block:() -> Unit){
        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//                block.invoke()
//            }
            block.invoke()
        }

    }

    private fun  updateView(list:List<Account>){
        mAdapter.setNewData(list.toMutableList())
    }

    private  class PlantAdapter(val plantDao: AccountDao):BaseQuickAdapter<Account,BaseViewHolder>(R.layout.item_plant){
        override fun convert(helper: BaseViewHolder, item: Account) {
            helper.setText(R.id.tv_id, item.id.toString())
            helper.setText(R.id.tv_name, item.name)

          /*  helper.setOnClickListener(R.id.btn_delete
            ) { v -> showDeleteDialog(item, v) }

            helper.setOnClickListener(R.id.btn_edit
            ) { v -> showEditDialog(item, v) }*/

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