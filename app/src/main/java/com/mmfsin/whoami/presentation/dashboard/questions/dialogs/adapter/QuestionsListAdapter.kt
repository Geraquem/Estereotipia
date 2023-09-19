package com.mmfsin.whoami.presentation.dashboard.questions.dialogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ItemQuestionBinding
import com.mmfsin.whoami.domain.models.Question

class QuestionsListAdapter(
    private val questions: List<Question>,
) : RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemQuestionBinding.bind(view)
        fun bind(question: Question, position: Int) {
            binding.apply {
                val text = (position + 1).toString() + ". " + question.question
                tvQuestion.text = text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    override fun getItemCount(): Int = questions.size
}