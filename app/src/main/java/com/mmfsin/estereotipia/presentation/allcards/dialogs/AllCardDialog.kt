package com.mmfsin.estereotipia.presentation.allcards.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogAllCardInfoBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.setGlideImage
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCardDialog(private val cardId: String) : BaseDialog<DialogAllCardInfoBinding>() {

    private val viewModel: AllCardDialogViewModel by viewModels()

    private var card: Card? = null
    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogAllCardInfoBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerCustomViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        if (firstAccess) {
            firstAccess = false
            requireDialog().animateDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCardById(cardId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            card?.let {
                requireContext().setGlideImage(it.image, ivImage, loading.image)
                tvName.text = it.name
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is AllCardDialogEvent.GetCard -> {
                    this.card = event.card
                    setUI()
                }

                is AllCardDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(cardId: String): AllCardDialog {
            return AllCardDialog(cardId)
        }
    }
}