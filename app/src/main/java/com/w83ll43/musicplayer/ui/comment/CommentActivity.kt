package com.w83ll43.musicplayer.ui.comment

import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.ActivityCommentBinding
import com.w83ll43.musicplayer.logic.model.Comment
import com.w83ll43.musicplayer.ui.common.BaseActivity
import com.w83ll43.musicplayer.ui.common.adapter.KotlinDataAdapter
import com.xuexiang.xui.utils.WidgetUtils
import com.xuexiang.xui.widget.button.shinebutton.ShineButton
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog
import com.xuexiang.xui.widget.imageview.RadiusImageView

class CommentActivity : BaseActivity() {
    private lateinit var mMiniLoadingDialog: MiniLoadingDialog

    private lateinit var adapter: KotlinDataAdapter<Comment>
    private var params: String? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[CommentViewModel::class.java]
    }
    private lateinit var _binding: ActivityCommentBinding
    val binding: ActivityCommentBinding
        get() = _binding

    override fun initBinding() {
        super.initBinding()
        _binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(this, "努力加载中");
        binding.musicCommentList.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {
        super.initData()
        params= intent.getStringExtra("id")
        params?.let {
            mMiniLoadingDialog.show()
            viewModel.getCommentDetail(it)
        }
    }

    override fun initView() {
        super.initView()
        //  使用通用KTAdapter 构建 适配器
        adapter = KotlinDataAdapter.Builder<Comment>()
            .setData(viewModel.commentList)
            .setLayoutId(R.layout.layout_song_commnet)
            .addBindView { itemView, itemData, _ ->
                val head = itemView.findViewById<RadiusImageView>(R.id.song_comment_head)
                val title = itemView.findViewById<TextView>(R.id.song_comment_title)
                val time = itemView.findViewById<TextView>(R.id.song_comment_time)
                val like = itemView.findViewById<ShineButton>(R.id.song_comment_like)
                val comment = itemView.findViewById<TextView>(R.id.song_comment_detail)
                Glide.with(this).load(itemData.user.avatarUrl).into(head)
                title.text = itemData.user.nickname
                time.text = itemData.timeStr
                comment.text = itemData.content
            }
            .create()
        binding.musicCommentList.adapter = adapter
    }

    override fun initListener() {
        super.initListener()
        binding.musicCommentRefresh.apply {
            this.setColorSchemeResources(R.color.colorPrimary)
            this.setOnRefreshListener {
                params?.let { viewModel.getCommentDetail(it) }
//                throw NullPointerException()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.commentInfoLiveData.observe(this) {
            val item = it.getOrNull()
            if (item != null) {
                println("comment ${item.comments}")
                viewModel.commentList.addAll(item.comments)
                adapter.notifyDataSetChanged()
            }
            binding.musicCommentRefresh.isRefreshing = false
            mMiniLoadingDialog.hide()
        }
    }

    override fun initBar() {
        super.initBar()
        window.statusBarColor = Color.WHITE
    }

    override fun onDestroy() {
        super.onDestroy()
        mMiniLoadingDialog.recycle()
    }
}