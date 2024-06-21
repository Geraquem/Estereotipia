package com.mmfsin.whoami.domain.mappers

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.CreateDeckCard
import com.mmfsin.whoami.domain.models.GameQuestion
import com.mmfsin.whoami.domain.models.Question
import java.util.UUID

fun Card.toCreateDeckCard() = CreateDeckCard(card = this, selected = false)

fun List<Card>.toCreateDeckCardList() = this.map { it.toCreateDeckCard() }

fun createGameQuestion(question: String) = GameQuestion(
    id = UUID.randomUUID().toString(),
    question = question,
    answer = null
)

fun List<Question>.toGameQuestionList() = this.map { createGameQuestion(it.question) }
