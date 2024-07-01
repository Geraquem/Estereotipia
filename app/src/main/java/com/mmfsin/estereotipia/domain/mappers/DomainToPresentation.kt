package com.mmfsin.estereotipia.domain.mappers

import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.CreateDeckCard
import com.mmfsin.estereotipia.domain.models.GameQuestion
import com.mmfsin.estereotipia.domain.models.Question
import java.util.UUID

fun Card.toCreateDeckCard() = CreateDeckCard(card = this, selected = false)

fun List<Card>.toCreateDeckCardList() = this.map { it.toCreateDeckCard() }

fun createGameQuestion(question: String) = GameQuestion(
    id = UUID.randomUUID().toString(),
    question = question,
    answer = null
)

fun List<Question>.toGameQuestionList() = this.map { createGameQuestion(it.question) }
