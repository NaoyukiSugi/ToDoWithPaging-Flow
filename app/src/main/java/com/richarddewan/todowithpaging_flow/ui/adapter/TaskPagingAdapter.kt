package com.richarddewan.todowithpaging_flow.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todowithpaging_flow.data.paging.PagingTask
import com.richarddewan.todowithpaging_flow.databinding.TaskListViewBinding

class TaskPagingAdapter : PagingDataAdapter<PagingTask.Task, TaskPagingAdapter.TaskViewHolder>(
        diffCallback = diffUtil
) {

    class TaskViewHolder(val binding: TaskListViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: PagingTask.Task) {
            binding.lbTaskId.text = data.id
            binding.lbUserId.text = data.userId
            binding.lbTitle.text = data.title
            binding.lbBody.text = data.body
            binding.lbNote.text = data.note
            binding.lbStatus.text = data.status
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PagingTask.Task>() {
            override fun areItemsTheSame(oldItem: PagingTask.Task, newItem: PagingTask.Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PagingTask.Task, newItem: PagingTask.Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}
