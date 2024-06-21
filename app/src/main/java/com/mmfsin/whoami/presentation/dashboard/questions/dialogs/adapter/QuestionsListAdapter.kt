package com.mmfsin.whoami.presentation.dashboard.questions.dialogs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemQuestionBinding
import com.mmfsin.whoami.domain.models.GameQuestion

class QuestionsListAdapter(
    private val questions: List<GameQuestion>,
) : RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemQuestionBinding.bind(view)
        val c: Context = binding.root.context
        fun bind(question: GameQuestion, position: Int) {
            binding.apply {
                tvNumber.text = c.getString(R.string.questions_position, position.toString())
                tvQuestion.text = question.question

                question.answer?.let { answer ->
                    if (answer) selectAnswer(true)
                    else selectAnswer(false)
                } ?: run { selectAnswer(null) }

                tvYes.setOnClickListener { selectAnswer(true) }
                tvNo.setOnClickListener { selectAnswer(false) }
            }
        }

        private fun selectAnswer(answer: Boolean?) {
            binding.apply {
                if (answer == null) {
                    selectedYes.visibility = View.GONE
                    selectedNo.visibility = View.GONE
                } else {
                    if (answer) {
                        selectedYes.visibility = View.VISIBLE
                        selectedNo.visibility = View.GONE
                    } else {
                        selectedYes.visibility = View.GONE
                        selectedNo.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun updateQuestionAnswer(id: String, answer: Boolean) {
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position], position + 1)
    }

    override fun getItemCount(): Int = questions.size
}