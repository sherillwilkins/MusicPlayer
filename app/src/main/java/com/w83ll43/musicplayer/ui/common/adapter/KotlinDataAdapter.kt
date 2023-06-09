package com.w83ll43.musicplayer.ui.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class KotlinDataAdapter<T> private constructor() :
    RecyclerView.Adapter<KotlinDataAdapter<T>.MyViewHolder>() {
    //数据
    private var dataList: ArrayList<T>? = null

    //布局id
    private var mLayoutId: Int? = null

    //绑定事件的lambda放发
    private var addBindView: ((itemView: View, itemData: T, index: Int) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(mLayoutId!!, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: -1 //左侧为null时返回-1
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        addBindView?.invoke(holder.itemView, dataList?.get(p1)!!, p1)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder<B> {

        private var adapter: KotlinDataAdapter<B> = KotlinDataAdapter()

        /**
         * 设置数据
         */
        fun setData(lists: ArrayList<B>): Builder<B> {
            adapter.dataList = lists
            return this
        }

        /**
         * 设置布局id
         */
        fun setLayoutId(layoutId: Int): Builder<B> {
            adapter.mLayoutId = layoutId
            return this
        }

        /**
         * 绑定View和数据
         */
        fun addBindView(itemBind: ((itemView: View, itemData: B, index: Int) -> Unit)): Builder<B> {
            adapter.addBindView = itemBind
            return this
        }

        fun create(): KotlinDataAdapter<B> {
            return adapter
        }
    }
}
