package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.data.models.QuestionDTO
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.Question

/** DECK */
fun DeckDTO.toDeck() = Deck(
    id = id,
    name = name,
    cards = cards,
    numOfCards = numOfCards(cards),
    isCustom = isCustom
)

private fun numOfCards(cards: String): Int = cards.split(",").size

fun List<DeckDTO>.toDeckList() = this.map { it.toDeck() }

/** CARD */
fun CardDTO.toCard() = Card(id, image, name, discard)

fun List<CardDTO>.toCardList() = this.map { it.toCard() }

/** QUESTION */
fun QuestionDTO.toQuestion() = Question(question = question)

fun List<QuestionDTO>.toQuestionList() = this.map { it.toQuestion() }