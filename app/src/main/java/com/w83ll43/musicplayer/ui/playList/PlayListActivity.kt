package com.w83ll43.musicplayer.ui.playList

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.ActivityPlayListBinding
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
import java.util.*

/**
 * ViewModel用来存储数据
 * binding 绑定视图
 */
class PlayListActivity : BaseActivity() {
    private var mMiniLoadingDialog: MiniLoadingDialog? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[PlayListViewModel::class.java]
    }
    private lateinit var adapter: KotlinDataAdapter<Song>
    private lateinit var _binding: ActivityPlayListBinding
    private val binding: ActivityPlayListBinding
        get() = _binding

    private val shareViewModel by lazy {
        MainActivity.mainActivity?.let { ViewModelProvider(it)[SharedViewModel::class.java] }!!
    }

//    private var playerController = StarrySky.with()

    override fun initBinding() {
        super.initBinding()
        _binding = ActivityPlayListBinding.inflate(layoutInflater)
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
            .setData(viewModel.playSongList)
            .addBindView { itemView, itemData, index ->
                //绑定数据
                val songTitle: TextView = itemView.findViewById(R.id.songTitle)
                val songSubTitle: TextView = itemView.findViewById(R.id.songSubTitle)
                val songIndex: TextView = itemView.findViewById(R.id.songIndex)
                songTitle.text = itemData.name
                songSubTitle.text = "${itemData.ar[0].name} - ${itemData.al.name}"
                songIndex.text = "${index + 1}"
                // 请求音乐源，将当前音乐信息保存到 shareViewModel 中 便于下个界面请求歌词等等
                itemView.setOnClickListener {
                    val gson = Gson()
                    val obj = gson.fromJson(gson.toJson(itemData), NowPlayInfo::class.java)
                    shareViewModel.setNowPlayerSong(obj)
                    shareViewModel.setPlayerSongId(obj.id.toString())
                    shareViewModel.playerSongUrlLiveData.observe(this@PlayListActivity, Observer {
                        val item = it.getOrNull()
                        if (item != null) {
                            shareViewModel.playerSongUrl.value = item
                        }
                    })
                    val intent = Intent(this@PlayListActivity, PlayerActivity::class.java)
                    startActivity(intent)
                }
            }
            .create()
        binding.songDetailListRecyclerView.adapter = adapter
    }

    override fun initData() {
        super.initData()
        val songsId: String? = intent.getStringExtra("id")
        val bgUrl: String? = intent.getStringExtra("bgUrl")
        val title: String? = intent.getStringExtra("title")
        if (songsId != null) {
            mMiniLoadingDialog?.show()
            viewModel.getPlayListTrack(songsId)
            Glide.with(this).load(bgUrl).into(binding.songDetailImage)
            binding.appbarLayoutToolbar.title = title
        }
    }

//    override fun initListener() {
//        super.initListener()
//        binding.fabScrolling.setOnClickListener {
//            val musicUrls = viewModel.playMusicUrl
//            val songs = viewModel.playSongList
//            musicUrls.forEachIndexed { index, musicUrl ->
//                val data = musicUrl.data[0]
//                if (data?.url != null) {
//                    val info = SongInfo()
//                    info.apply {
//                        songName = songs[index].name  //音乐标题
//                        artist = "${songs[index].ar[0].name} - ${songs[index].al.name}"   //作者
//                        songCover = songs[index].al.picUrl  //音乐封面
//                        songId = data.id.toString()
//                        songUrl = data.url
//                    }
//                    shareViewModel.mediaPlayerList.add(info)
//                }
//            }
//            playerController.playMusic(shareViewModel.mediaPlayerList, 0)
//        }
//    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserver() {
        super.initObserver()
        viewModel.playSongListLiveData.observe(this, Observer {
            val list = it.getOrNull()
            if (list != null) {
                viewModel.playSongList.addAll(list)
//                var songIds = ""
//                list.forEach { song ->
//                    val songId = song.id.toString()
//                    songIds = "$songIds$songId,"
//                }
//                songIds = songIds.substring(0, songIds.length - 1)
//                viewModel.getSongMusicUrl(songIds)
                adapter.notifyDataSetChanged()
            }
            mMiniLoadingDialog?.hide()
        })
//        viewModel.playSongLiveData.observe(this, Observer {
//            val musicUrls = it.getOrNull()
//            if (musicUrls != null) {
//                viewModel.playMusicUrl.addAll(musicUrls)
//            }
//        })
    }

    override fun initBar() {
//        super.initBar()
//        不加super 不会执行父类方法中的函数体
        StatusBarUtils.setStatusBarDarkMode(this)
    }

    override fun onDestroy() {
        mMiniLoadingDialog?.dismiss()
        super.onDestroy()
    }
}