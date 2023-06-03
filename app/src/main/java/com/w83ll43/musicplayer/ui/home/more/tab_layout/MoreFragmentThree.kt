package com.w83ll43.musicplayer.ui.home.more.tab_layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.w83ll43.musicplayer.databinding.FragmentMoreThreeBinding

class MoreFragmentThree : Fragment() {
    private  var _binding: FragmentMoreThreeBinding? = null
    val binding: FragmentMoreThreeBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreThreeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}