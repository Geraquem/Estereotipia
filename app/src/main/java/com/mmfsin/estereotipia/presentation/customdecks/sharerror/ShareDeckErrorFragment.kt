package com.mmfsin.estereotipia.presentation.customdecks.sharerror

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragmentNoVM
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentShareDeckErrorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareDeckErrorFragment : BaseFragmentNoVM<FragmentShareDeckErrorBinding>() {

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentShareDeckErrorBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            (activity as BedRockActivity).setUpToolbar(
                title = getString(R.string.shared_deck_error_toolbar),
                instructionsVisible = false
            )
        }
    }

    override fun setListeners() {
        binding.apply {
            goToSettings.setOnClickListener {
                val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Intent(Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS).apply {
                        data = Uri.parse("package:${requireContext().packageName}")
                    }
                } else {
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:${requireContext().packageName}")
                    }
                }
                startActivity(intent)
            }
        }
    }
}