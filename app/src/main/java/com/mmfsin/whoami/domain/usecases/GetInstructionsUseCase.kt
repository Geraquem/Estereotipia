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
                icon = R.drawable.ic_info,
                title = getString(R.string.instructions_whats_about),
                text = R.string.instructions_whats_about_detail,
                background = R.drawable.bg_instructions_top
            ), Instruction(
                order = 2,
                group = 1,
                icon = R.drawable.ic_players,
                title = getString(R.string.instructions_how_many_players),
                text = R.string.instructions_how_many_players_detail,
                background = R.drawable.bg_instructions_middle
            ), Instruction(
                order = 3,
                group = 1,
                icon = R.drawable.ic_book,
                title = getString(R.string.instructions_how_to_play),
                layout = R.layout.inst_how_to_play,
                background = R.drawable.bg_instructions_bottom
            ),

            /** GROUP 2 **/
            Instruction(
                order = 4,
                group = 2,
                icon = R.drawable.ic_card,
                title = getString(R.string.instructions_my_card),
                text = R.string.instructions_my_card_details,
                background = R.drawable.bg_instructions_top
            ), Instruction(
                order = 5,
                group = 2,
                icon = R.drawable.ic_deck,
                title = getString(R.string.instructions_decks),
                layout = R.layout.inst_decks,
                background = R.drawable.bg_instructions_bottom
            ),

            /** GROUP 3 **/
            Instruction(
                order = 6,
                group = 3,
                icon = R.drawable.ic_question,
                title = getString(R.string.instructions_how_many_questions),
                text = R.string.instructions_how_many_questions_details,
                background = R.drawable.bg_instructions_top
            ), Instruction(
                order = 7,
                group = 3,
                icon = R.drawable.ic_dart,
                title = getString(R.string.instructions_how_many_opportunities),
                layout = R.layout.inst_how_many_opportunities,
                background = R.drawable.bg_instructions_middle
            ), Instruction(
                order = 8,
                group = 3,
                icon = R.drawable.ic_finish,
                title = getString(R.string.instructions_when_ends),
                text = R.string.instructions_when_ends_details,
                background = R.drawable.bg_instructions_bottom
            )
        )

        return instructions.sortedBy { it.order }
    }

    private fun getString(reference: Int): String {
        return context.getString(reference)
    }
}