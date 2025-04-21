package com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ItemPhraseBinding
import com.mmfsin.estereotipia.domain.models.GamePhrase

class PhrasesListAdapter(
    private val questions: List<GamePhrase>,
) : RecyclerView.Adapter<PhrasesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPhraseBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(question: GamePhrase, position: Int) {
            binding.apply {
                tvNumber.text = c.getString(R.string.questions_position, position.toString())
                tvQuestion.text = question.question

                question.answer?.let { answer -> etAnswered.setText(answer) }
            }
        }
    }

    fun updateQuestionAnswer(id: String, answer: String) {
        var position: Int? = null
        questions.forEachIndexed() { i, question ->
            if (question.id == id) {
                question.answer = answer
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_phrase, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position], position + 1)
    }

    override fun getItemCount(): Int = questions.size
}