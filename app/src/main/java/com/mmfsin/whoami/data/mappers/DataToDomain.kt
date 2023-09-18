package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.data.models.QuestionDTO
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.Question

fun List<DeckDTO>.toDeckList() = this.map { it.toDeck() }

fun DeckDTO.toDeck() = Deck(id, image, name)

fun List<CardDTO>.toCardList() = this.map { it.toCard() }

fun CardDTO.toCard() = Card(id, deckId, image, name, discard)

fun List<QuestionDTO>.toQuestionList() = this.map { it.toQuestion() }

fun QuestionDTO.toQuestion() = Question(question = question)