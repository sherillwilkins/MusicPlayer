package com.w83ll43.musicplayer.ui.home.more.tab_layout

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.FragmentMoreTwoBinding
import com.w83ll43.musicplayer.extension.load
import com.w83ll43.musicplayer.logic.model.HotTopList
import com.w83ll43.musicplayer.ui.common.BaseFragment
import com.w83ll43.musicplayer.ui.common.adapter.KotlinDataAdapter
import com.w83ll43.musicplayer.ui.common.view.LoadingObserver
import com.w83ll43.musicplayer.ui.playList.PlayListActivity
import com.xuexiang.xui.widget.imageview.RadiusImageView


class MoreFragmentTwo : BaseFragment() {
    private lateinit var _binding: FragmentMoreTwoBinding
    private lateinit var cardAdapter: KotlinDataAdapter<HotTopList.HopTopDetail>

    private val binding: FragmentMoreTwoBinding
        get() = _binding

    private val viewModel by lazy {
        ViewModelProvider(this).get(MoreFragmentTwoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_two, container, false);
        binding.lifecycleOwner = viewLifecycleOwner
        _binding.vm = viewModel
        return _binding.root
    }

    override fun initView() {
        super.initView()
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        super.initData()
        //定义外层recyclerview adapter
        cardAdapter = KotlinDataAdapter.Builder<HotTopList.HopTopDetail>()
            .setLayoutId(R.layout.layout_hotsong_item)
            .setData(viewModel.hotTopList)
            .addBindView { itemView, itemData, index ->
                val name: TextView = itemView.findViewById(R.id.item_hot_name)
                val image: RadiusImageView = itemView.findViewById(R.id.item_hot_image)
                val topRightTip: TextView = itemView.findViewById(R.id.top_right_tip)
                name.text = itemData.name
                image.load(itemData.coverImgUrl)
                topRightTip.text = itemData.updateFrequency
                itemView.setOnClickListener {
                    val intent = Intent(context, PlayListActivity::class.java).apply {
                        putExtra("id", itemData.id.toString())
                        putExtra("bgUrl", itemData.coverImgUrl)
                        putExtra("title", itemData.name)
                    }
                    startActivity(intent)
                }
                //循环内层歌曲
                val hotSongItemList = itemView.findViewById<LinearLayout>(R.id.hot_song_item_list)
                hotSongItemList.removeAllViews()
                for(i in itemData.tracks.indices){
                    // 找到R.layout.layout_hotsong_song_item、指定父布局hotSongItemList
                    val view = LayoutInflater.from(activity).inflate(R.layout.layout_hotsong_song_item, hotSongItemList, false)
                    val subtitle:TextView = view.findViewById(R.id.hot_song_item_subtitle)
                    subtitle.text = "${i+1}.${itemData.tracks[i].first}-${itemData.tracks[i].second}"
                    //赋值后加载
                    hotSongItemList.addView(view)
                }
            }
            .create()
        //绑定layoutManager、adapter
        binding.hotSongList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = cardAdapter
        }
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.getTopList()
        viewModel.hotTopListLiveData.observe(this, Observer {
            val item = it.getOrNull()
            if (item != null) {
                viewModel.setHotTopList(item.list)
                cardAdapter.notifyDataSetChanged()
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, LoadingObserver(requireContext()))

    }
}