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
            /** GROUP 1 **/
            Instruction(
                order = 1,
                group = 1,
                title = getString(R.string.instructions_whats_about),
                text = R.string.instructions_whats_about_detail
            ), Instruction(
                order = 2,
                group = 1,
                title = getString(R.string.instructions_how_many_players),
                text = R.string.instructions_how_many_players_detail
            ), Instruction(
                order = 3,
                group = 1,
                title = getString(R.string.instructions_how_to_play),
                layout = R.layout.inst_how_to_play
            ),

            /** GROUP 2 **/
            Instruction(
                order = 4,
                group = 2,
                title = getString(R.string.instructions_my_card),
                text = R.string.instructions_my_card_details
            ), Instruction(
                order = 5,
                group = 2,
                title = getString(R.string.instructions_decks),
                layout = R.layout.inst_decks
            ),

            /** GROUP 3 **/
            Instruction(
                order = 6,
                group = 3,
                title = getString(R.string.instructions_how_many_questions),
                text = R.string.instructions_how_many_questions_details
            ), Instruction(
                order = 7,
                group = 3,
                title = getString(R.string.instructions_how_many_opportunities),
                layout = R.layout.inst_how_many_opportunities
            ), Instruction(
                order = 8,
                group = 3,
                title = getString(R.string.instructions_when_ends),
                text = R.string.instructions_when_ends_details
            )
        )

        return instructions.sortedBy { it.order }
    }

    private fun getString(reference: Int): String {
        return context.getString(reference)
    }
}