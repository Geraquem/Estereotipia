package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.CardDTO
import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.data.models.MyDeckDTO
import com.mmfsin.whoami.data.models.QuestionDTO
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.domain.models.Question

/** DECK */
fun DeckDTO.toDeck() = Deck(id, image, name, cards)

fun List<DeckDTO>.toDeckList() = this.map { it.toDeck() }

/** CARD */
fun CardDTO.toCard() = Card(id, image, name, discard)

fun List<CardDTO>.toCardList() = this.map { it.toCard() }

/** QUESTION */
fun QuestionDTO.toQuestion() = Question(question = question)

fun List<QuestionDTO>.toQuestionList() = this.map { it.toQuestion() }

/** MY DECK */
fun MyDeckDTO.toMyDeck() = MyDeck(id, name, cards)

fun List<MyDeckDTO>.toMyDeckList() = this.map { it.toMyDeck() }
