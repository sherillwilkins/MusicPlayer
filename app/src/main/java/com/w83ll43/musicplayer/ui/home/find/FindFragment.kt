package com.w83ll43.musicplayer.ui.home.find

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
import com.w83ll43.musicplayer.databinding.FragmentFindBinding
import com.w83ll43.musicplayer.logic.dao.UserDao
import com.w83ll43.musicplayer.logic.model.Playlist
import com.w83ll43.musicplayer.ui.MainActivity
import com.w83ll43.musicplayer.ui.common.BaseFragment
import com.w83ll43.musicplayer.ui.common.adapter.KotlinDataAdapter
import com.w83ll43.musicplayer.ui.playList.PlayListActivity
import com.w83ll43.musicplayer.ui.search.SearchResultActivity
import com.w83ll43.musicplayer.utils.XToastUtils
import com.xiaoyouProject.searchbox.SearchFragment
import com.xiaoyouProject.searchbox.custom.IOnSearchClickListener
import com.xiaoyouProject.searchbox.entity.CustomLink
import com.xuexiang.xui.utils.WidgetUtils
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet
import com.xuexiang.xui.widget.imageview.IconImageView


class FindFragment : BaseFragment() {
    private var userInfo = UserDao.getUserInfo()
    private lateinit var mMiniLoadingDialog: MiniLoadingDialog
    private lateinit var searchFragment: SearchFragment<Any>
    private lateinit var adapter: KotlinDataAdapter<Playlist>
    private lateinit var _binding: FragmentFindBinding
    private val binding: FragmentFindBinding
        get() = _binding

    private val viewModel by lazy {
        ViewModelProvider(this)[FindViewModel::class.java]
    }


    val data: MutableList<CustomLink<Any>> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        super.initView()
        mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(requireContext(), "努力加载中");
        searchFragment = SearchFragment.newInstance();
        binding.findRecyclerView.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun initData() {
        super.initData()
          // 使用通用 KTAdapter 构建 适配器
        adapter = KotlinDataAdapter.Builder<Playlist>()
            .setData(viewModel.playList)
            .setLayoutId(R.layout.layout_item_song_card)
            .addBindView { itemView, itemData ,_->
                val image = itemView.findViewById(R.id.iv_song_image) as ImageView
                val userName = itemView.findViewById(R.id.tv_user_name) as TextView
                val read = itemView.findViewById(R.id.tv_read) as TextView
                val more = itemView.findViewById(R.id.songs_more) as IconImageView
                val itemSong = itemView.findViewById(R.id.item_song_card) as CardView
                userName.text = itemData.name
                read.text = "歌曲数:${itemData.trackCount}"
                Glide.with(this).load(itemData.coverImgUrl).apply(
                    RequestOptions.bitmapTransform(RoundedCorners(30))
                ).into(image)

                more.setOnClickListener {
                    showSimpleBottomSheetList(itemData)
                }

                itemSong.setOnClickListener {
                    val intent = Intent(context, PlayListActivity::class.java).apply {
                        putExtra("id", itemData.id.toString())
                        putExtra("bgUrl", itemData.coverImgUrl)
                        putExtra("avUrl", itemData.creator.avatarUrl)
                        putExtra("author", itemData.creator.nickname)
                        putExtra("title", itemData.name)
                    }
                    startActivity(intent)
                }
            }
            .create()
        binding.findRecyclerView.adapter = adapter
    }

    override fun initObserver() {
        super.initObserver()
        //获取歌单
        mMiniLoadingDialog.show()
        viewModel.getPlayList(userInfo.account.id.toString())
        viewModel.playListLiveData.observe(this, Observer {
            val playList = it.getOrNull()
            if (playList != null) {
                viewModel.playList.clear()
                viewModel.playList.addAll(playList)
                adapter.notifyDataSetChanged()
            }
            mMiniLoadingDialog.hide()
            binding.findFragmentRefresh.isRefreshing = false
        })
        // 搜索
        viewModel.keyWordLiveData.observe(this, Observer { it ->
            data.clear()
            it.getOrNull()?.result?.songs?.forEach {
                viewModel.searchResult.add(it)
                data.add(CustomLink(it.name, it.id))
            }
            // 这里我们设置关键词预测显示的内容
            searchFragment.setLinks(data)
        })
        binding.findFragmentRefresh.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                viewModel.getPlayList(userInfo.account.id.toString())
            }
        }
    }

    override fun initListener() {
        super.initListener()
        binding.toolbarDraw.apply {
            setNavigationOnClickListener {
                MainActivity.mainActivity?.openDraw()
            }
        }
        binding.toolbarDraw.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    searchFragment.showFragment(this.fragmentManager, SearchFragment.TAG);
                }
            }
            false
        }
        searchFragment.setOnSearchClickListener(object : IOnSearchClickListener<Any> {
            /**
             * 点击搜索按钮时触发
             * @param keyword 搜索的关键词
             */
            override fun onSearchClick(keyword: String) {
                println("onSearchClick 执行搜索")
                viewModel.searchKeyword(keyword)
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("keyword", viewModel.getSearchKey)
                startActivity(intent)
            }

            /**
             * 点击关键词预测链接时触发
             * 链接携带的数据是歌曲 ID
             * @param data 链接携带的数据
             */
            override fun onLinkClick(data: Any?) {
                println("onLinkClick $data")
            }

            /**
             * 当搜索框内容改变时触发
             * @param keyword 搜索的关键词
             */
            override fun onTextChange(keyword: String) {
                viewModel.searchKeyword(keyword)
            }
        })
    }

    private fun showSimpleBottomSheetList(itemData: Playlist) {
        BottomSheet.BottomListSheetBuilder(activity)
            .setTitle(itemData.name)
            .addItem("修改资料")
            .addItem("删除")
            .setIsCenter(false)
            .setOnSheetItemClickListener { dialog: BottomSheet, itemView: View?, position: Int, tag: String? ->
                dialog.dismiss()
                XToastUtils.toast("Item " + (position + 1))
            }
            .build()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMiniLoadingDialog.recycle()
    }
}