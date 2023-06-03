package com.w83ll43.musicplayer.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.w83ll43.musicplayer.R
import com.w83ll43.musicplayer.databinding.ActivityMainBinding
import com.w83ll43.musicplayer.logic.dao.UserDao
import com.w83ll43.musicplayer.ui.common.BaseActivity
import com.w83ll43.musicplayer.ui.common.adapter.ViewPageFragmentAdapter
import com.w83ll43.musicplayer.ui.home.find.FindFragment
import com.w83ll43.musicplayer.ui.home.mine.MineFragment
import com.w83ll43.musicplayer.ui.home.more.MoreFragment
import com.w83ll43.musicplayer.ui.login.LoginActivity
import com.w83ll43.musicplayer.ui.setting.SettingActivity
import com.w83ll43.musicplayer.utils.XToastUtils
import com.xuexiang.xui.widget.imageview.RadiusImageView
import java.io.File

class MainActivity : BaseActivity() {
    private var userInfo = UserDao.getUserInfo()
    private lateinit var adapter: ViewPageFragmentAdapter
    private lateinit var viewPage: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView
    private var fragments: MutableList<Fragment> = ArrayList()
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    val takePhoto = 1
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    fun openDraw() {
        val drawerLayout = binding.drawerLayout
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun initBinding() {
        super.initBinding()
        mainActivity = this
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
    }

    override fun initBar() {
        super.initBar()
        window.statusBarColor = Color.WHITE
    }

    /**
     * 设置 Fragment适配器
     */
    override fun initData() {
        super.initData()
        viewPage = binding.includeMain.viewPager
        bottomNavigationView = binding.includeMain.bottomNavigation
        fragments.apply {
            add(FindFragment())
            add(MoreFragment())
            add(MineFragment())
        }
        adapter = ViewPageFragmentAdapter(fragments, supportFragmentManager)
        viewPage.adapter = adapter
    }

    /**
     * 设置抽屉菜单界面
     */
    override fun initView() {
        super.initView()
        val navigationView = binding.navView
        val headerLayout = navigationView.getHeaderView(0)
        val headImg = headerLayout.findViewById(R.id.nav_header_img) as ImageView
        val name = headerLayout.findViewById(R.id.nav_header_username) as TextView
        val sign = headerLayout.findViewById(R.id.nav_header_sign) as TextView
        name.text = userInfo.userDetail.profile.nickname
        sign.text = userInfo.userDetail.profile.signature
        Glide.with(this).load(userInfo.userDetail.profile.avatarUrl).into(headImg)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_logoOut -> {
                    try {
                        UserDao.logout()
                        val intent = Intent(
                            this,
                            LoginActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } catch (e: Exception) {
                        XToastUtils.error("退出失败");
                    }
                }
                else -> XToastUtils.error("该功能尚未开发");
            }
            false
        }
    }

    override fun initListener() {
        super.initListener()
        // 页面切换
        viewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        // 给 bottomView 设置导航切换监听
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_news ->
                    viewPage.currentItem = 0
                R.id.nav_trending ->
                    viewPage.currentItem = 1
                R.id.nav_profile ->
                    viewPage.currentItem = 2
            }
            false
        }
        // 退出登录


//        val photo = findViewById<SuperTextView>(R.id.photo)
//        photo.setOnClickListener {
//            // 创建 File 对象 用于存储拍照后的图片
//            outputImage = File(externalCacheDir, "output_image.jpg")
//            if (outputImage.exists()) {
//                outputImage.delete()
//            }
//            outputImage.createNewFile()
//            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                FileProvider.getUriForFile(context!!, "com.w83ll43.musicplayer.fileprovider", outputImage)
//            } else {
//                Uri.fromFile(outputImage)
//            }
//            // 启动相机程序
//            val intent = Intent("android.media.action.IMAGE_CAPTURE")
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//            startActivityForResult(intent, takePhoto)
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的照片显示出来
                    imageUri = MineFragment.mineFragment?.imageUri!!
                    val bitmap =
                        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    val imageView = findViewById<RadiusImageView>(R.id.riv_head_pic)
                    imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        outputImage = MineFragment.mineFragment?.outputImage!!
        val exifInterface = ExifInterface(outputImage.path)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        // 将不再需要的 Bitmap 对象回收
        bitmap.recycle()
        return rotatedBitmap
    }

    companion object {
        var mainActivity: MainActivity? = null
    }
}