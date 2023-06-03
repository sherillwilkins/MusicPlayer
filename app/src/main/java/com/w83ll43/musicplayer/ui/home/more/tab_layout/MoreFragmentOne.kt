package com.w83ll43.musicplayer.ui.home.more.tab_layout

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.FragmentMoreOneBinding
import com.w83ll43.musicplayer.logic.model.RecommendPlayList
import com.w83ll43.musicplayer.ui.common.BaseFragment
import com.w83ll43.musicplayer.ui.common.adapter.KotlinDataAdapter
import com.w83ll43.musicplayer.ui.playList.PlayListActivity

class MoreFragmentOne : BaseFragment() {
    private lateinit var adapter: KotlinDataAdapter<RecommendPlayList.Result>
    private lateinit var _binding: FragmentMoreOneBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MoreFragmentOneViewModel::class.java]
    }

    val binding: FragmentMoreOneBinding
        get() = _binding

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreOneBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun initData() {
        super.initData()
        val layoutManager = GridLayoutManager(activity, 2)
        _binding.topRecyclerView.layoutManager = layoutManager
        adapter = KotlinDataAdapter.Builder<RecommendPlayList.Result>()
            .setData(viewModel.playList)
            .setLayoutId(R.layout.layout_item_song_card)
            .addBindView { itemView, itemData ,_->
                val image = itemView.findViewById(R.id.iv_song_image) as ImageView
                val userName = itemView.findViewById(R.id.tv_user_name) as TextView
                val read = itemView.findViewById(R.id.tv_read) as TextView
                val itemSong = itemView.findViewById(R.id.item_song_card) as CardView
                userName.text = itemData.name
                read.text = "歌曲数:${itemData.trackCount}"
                Glide.with(this).load(itemData.picUrl).apply(
                    RequestOptions.bitmapTransform(RoundedCorners(30))
                ).into(image)

                itemSong.setOnClickListener {
                    val intent = Intent(context, PlayListActivity::class.java).apply {
                        putExtra("id", itemData.id.toString())
                        putExtra("bgUrl", itemData.picUrl)
                        putExtra("title", itemData.name)
                    }
                    startActivity(intent)
                }
            }
            .create()
        binding.topRecyclerView.adapter = adapter
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.getRecommendPlayList()
        viewModel.playListLiveData.observe(this, Observer {
            val list = it.getOrNull()
            if (list != null) {
                viewModel.playList.addAll(list.result)
                adapter.notifyDataSetChanged()
            }
            binding.moreFragmentRefresh.isRefreshing = false
        })
        binding.moreFragmentRefresh.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                viewModel.getRecommendPlayList()
            }
        }
    }
}