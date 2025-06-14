package com.mmfsin.estereotipia.data.mappers

import com.mmfsin.estereotipia.data.models.CardDTO
import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.data.models.IdentityDTO
import com.mmfsin.estereotipia.data.models.QuestionDTO
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.domain.models.Identity
import com.mmfsin.estereotipia.domain.models.Question

/** DECK */
fun DeckDTO.toDeck() = Deck(
    id = id,
    name = name,
    cards = cards,
    numOfCards = numOfCards(cards),
    icon = icon,
    isCustom = isCustom
)

private fun numOfCards(cards: String): Int = cards.split(",").size

fun List<DeckDTO>.toDeckList() = this.map { it.toDeck() }

/** CARD */
fun CardDTO.toCard() = Card(
    id = id,
    image = image,
    name = name
)

fun List<CardDTO>.toCardList() = this.map { it.toCard() }

/** QUESTION */
fun QuestionDTO.toQuestion() = Question(question = question)

fun List<QuestionDTO>.toQuestionList() = this.map { it.toQuestion() }

/** IDENTITIES */
fun IdentityDTO.toIdentity() = Identity(
    text = text,
    text1 = text1,
    text2 = text2,
    text3 = text3
)

fun List<IdentityDTO>.toIdentityList() = this.map { it.toIdentity() }