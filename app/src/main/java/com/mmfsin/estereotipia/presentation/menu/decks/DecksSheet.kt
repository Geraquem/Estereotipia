package com.mmfsin.estereotipia.presentation.menu.decks

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.BsheetDecksBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.menu.decks.adapter.DecksAdapter
import com.mmfsin.estereotipia.presentation.menu.decks.interfaces.IDeckListener
import com.mmfsin.estereotipia.presentation.menu.interfaces.IMenuListener
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecksSheet(val listener: IMenuListener) : BottomSheetDialogFragment(), IDeckListener {

    private val viewModel: DecksViewModel by viewModels()

    private lateinit var binding: BsheetDecksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsheetDecksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)

                val metrics = Resources.getSystem().displayMetrics
                val maxHeight = (metrics.heightPixels * 0.95).toInt()
                it.layoutParams.height = maxHeight
                behavior.peekHeight = maxHeight
                it.requestLayout()

                it.background = getDrawable(requireContext(), R.drawable.bg_top_box)
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDecks()
        observe()
        setListeners()
    }

    private fun setListeners() {}

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DecksEvent.GetDecks -> setUpDecks(event.decks)
                is DecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDecks(decks: List<Deck>) {
        binding.rvDecks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DecksAdapter(decks, this@DecksSheet)
        }
    }

    override fun onDeckClick(deckId: String) {
        listener.startGame(deckId)
        dismiss()
    }

    private fun error() = activity?.showErrorDialog(goBack = false)
}