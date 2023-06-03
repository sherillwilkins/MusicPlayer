package com.w83ll43.musicplayer.ui.home.mine

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.FragmentMineBinding
import com.w83ll43.musicplayer.logic.dao.UserDao
import com.w83ll43.musicplayer.ui.common.BaseFragment
import java.io.File

/**
 * com.xuexiang.xui.widget.textview.supertextview.SuperTextView
 * 没有 text 属性的 setter 方法 不能用数据绑定
 */
class MineFragment : BaseFragment() {

    val takePhoto = 1
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    private var user = UserDao.getUser()
    private lateinit var _binding: FragmentMineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        _binding.user = user
        mineFragment = this
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun initListener() {
        super.initListener()
        _binding.photo.setOnClickListener {
            // 创建 File 对象 用于存储拍照后的图片
            outputImage = File(activity?.externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(context!!, "com.w83ll43.musicplayer.fileprovider", outputImage)
            } else {
                Uri.fromFile(outputImage)
            }
            // 启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            activity?.startActivityForResult(intent, takePhoto)
        }
    }

    companion object {
        var mineFragment: MineFragment? = null
    }
}