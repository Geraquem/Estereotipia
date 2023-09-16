package com.mmfsin.whoami.domain.usecases

import android.content.Context
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.models.Instruction
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetInstructionsUseCase @Inject constructor(@ApplicationContext val context: Context) :
    BaseUseCaseNoParams<List<Instruction>>() {

    override suspend fun execute(): List<Instruction> {
        val instructions = listOf(
            Instruction(1, getString(R.string.instructions_whats_about), R.layout.inst_whats_about),
            Instruction(
                2, getString(R.string.instructions_how_many_players), R.layout.inst_how_many_players
            ),
            Instruction(3, getString(R.string.instructions_decks), R.layout.inst_decks),
            Instruction(4, getString(R.string.instructions_how_to_play), R.layout.inst_how_to_play),
            Instruction(5, getString(R.string.instructions_my_card), R.layout.inst_my_card),
            Instruction(
                6,
                getString(R.string.instructions_how_many_questions),
                R.layout.inst_how_many_questions
            ),
            Instruction(
                7,
                getString(R.string.instructions_how_many_opportunities),
                R.layout.inst_how_many_opportunities
            ),
            Instruction(8, getString(R.string.instructions_failing), R.layout.inst_failing),
            Instruction(9, getString(R.string.instructions_when_ends), R.layout.inst_when_ends)
        )

        return instructions.sortedBy { it.order }
    }

    private fun getString(reference: Int): String {
        return context.getString(reference)
    }
}