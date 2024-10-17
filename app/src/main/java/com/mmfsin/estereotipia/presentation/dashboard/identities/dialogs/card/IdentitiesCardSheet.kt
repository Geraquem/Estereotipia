package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.card

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseBottomSheet
import com.mmfsin.estereotipia.databinding.BsheetCardIdentitiesBinding
import com.mmfsin.estereotipia.domain.models.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesCardSheet(
    private val identity: Identity
) : BaseBottomSheet<BsheetCardIdentitiesBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        BsheetCardIdentitiesBinding.inflate(inflater)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)
    }

    override fun setUI() {
        binding.apply {
            identity.text?.let { topText ->
                tvTopText.text = topText
                tvTopText.isVisible = true
            } ?: run { tvTopText.isVisible = false }

            tvOptionOne.text = identity.text1
            tvOptionTwo.text = identity.text2
            tvOptionThree.text = identity.text3
        }
    }
}