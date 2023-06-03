package com.w83ll43.musicplayer.ui.search

import android.content.Intent
import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.ActivitySearchResultBinding
import com.w83ll43.musicplayer.logic.dao.SharedViewModel
import com.w83ll43.musicplayer.logic.model.NowPlayInfo
import com.w83ll43.musicplayer.logic.model.Song
import com.w83ll43.musicplayer.ui.MainActivity
import com.w83ll43.musicplayer.ui.common.BaseActivity
import com.w83ll43.musicplayer.ui.common.adapter.KotlinDataAdapter
import com.w83ll43.musicplayer.ui.player.PlayerActivity
import com.xuexiang.xui.utils.StatusBarUtils
import com.xuexiang.xui.utils.WidgetUtils
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog

class SearchResultActivity : BaseActivity() {
    private var mMiniLoadingDialog: MiniLoadingDialog? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[SearchResultViewModel::class.java]
    }

    private lateinit var adapter: KotlinDataAdapter<Song>
    private lateinit var _binding: ActivitySearchResultBinding
    private val binding: ActivitySearchResultBinding
        get() = _binding

    private val shareViewModel by lazy {
        MainActivity.mainActivity?.let { ViewModelProvider(it)[SharedViewModel::class.java] }!!
    }

    override fun initBinding() {
        super.initBinding()
        _binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarUtils.translucent(this, Color.TRANSPARENT)
        mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(this, "努力加载中");
    }

    /**
     * 绑定 adapter 与 layoutManager
     */
    override fun initView() {
        super.initView()
        val layoutManager = LinearLayoutManager(this)
        binding.songDetailListRecyclerView.layoutManager = layoutManager
        adapter = KotlinDataAdapter.Builder<Song>()
            .setLayoutId(R.layout.layout_song_item)
            .setData(viewModel.searchResult)
            .addBindView { itemView, itemData, index ->
                // 绑定数据
                val songTitle: TextView = itemView.findViewById(R.id.songTitle)
                val songSubTitle: TextView = itemView.findViewById(R.id.songSubTitle)
                val songIndex: TextView = itemView.findViewById(R.id.songIndex)
                songTitle.text = itemData.name
                songSubTitle.text = itemData.artists[0].name
                songIndex.text = "${index + 1}"
                // 请求音乐源，将当前音乐信息保存到 shareViewModel 中 便于下个界面请求歌词等等
                itemView.setOnClickListener {
                    val gson = Gson()
                    val obj = gson.fromJson(gson.toJson(itemData), NowPlayInfo::class.java)
                    println("obj $obj")
                    shareViewModel.setNowPlayerSong(obj)
                    shareViewModel.setPlayerSongId(obj.id.toString())
                    shareViewModel.playerSongUrlLiveData.observe(this, Observer {
                        val item = it.getOrNull()
                        if (item != null) {
                            shareViewModel.playerSongUrl.value = item
                        }
                    })
                    val intent = Intent(this, PlayerActivity::class.java)
                    startActivity(intent)
                }
            }
            .create()
        binding.songDetailListRecyclerView.adapter = adapter
    }

    override fun initData() {
        super.initData()
        mMiniLoadingDialog?.show()
        val keyword: String? = intent.getStringExtra("keyword")
        viewModel.searchKeyword(keyword!!)
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.keyWordLiveData.observe(this, Observer { it ->
            it.getOrNull()?.result?.songs?.forEach {
                viewModel.searchResult.add(it)
            }
            adapter.notifyDataSetChanged()
            println(viewModel.searchResult)
            mMiniLoadingDialog?.hide()
        })
    }
}