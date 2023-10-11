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
            Instruction(
                1,
                getString(R.string.instructions_whats_about),
                text = R.string.instructions_whats_about_detail
            ),
            Instruction(
                2,
                getString(R.string.instructions_how_many_players),
                text = R.string.instructions_how_many_players_detail
            ),
            Instruction(
                3,
                getString(R.string.instructions_how_to_play),
                layout = R.layout.inst_how_to_play
            ),

            /******/
            Instruction(
                4,
                getString(R.string.instructions_my_card),
                text = R.string.instructions_my_card_details
            ),

            Instruction(
                5,
                getString(R.string.instructions_decks),
                layout = R.layout.inst_decks
            ),

            /******/
            Instruction(
                6,
                getString(R.string.instructions_how_many_questions),
                text = R.string.instructions_how_many_questions_details
            ),
            Instruction(
                7,
                getString(R.string.instructions_how_many_opportunities),
                layout = R.layout.inst_how_many_opportunities
            ),
            Instruction(
                8,
                getString(R.string.instructions_when_ends),
                text = R.string.instructions_when_ends_details
            )
        )

        return instructions.sortedBy { it.order }
    }

    private fun getString(reference: Int): String {
        return context.getString(reference)
    }
}