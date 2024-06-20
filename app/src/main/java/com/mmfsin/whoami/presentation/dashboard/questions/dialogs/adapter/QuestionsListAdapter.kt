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
            }
        }
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