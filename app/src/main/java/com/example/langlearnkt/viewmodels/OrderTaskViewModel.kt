package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderTaskViewModel(
    val task: OrderTask
): TaskViewModel() {

    private val _givenAnswer = MutableStateFlow(listOf<AnswerWord>())
    val givenAnswer: StateFlow<List<AnswerWord>> = _givenAnswer.asStateFlow()

    private val _wordBank = MutableStateFlow(getBankFromTask(task))
    val wordBank: StateFlow<List<BankWord>> = _wordBank.asStateFlow()

    fun addWordToAnswer(word: BankWord){
        _givenAnswer.value += AnswerWord(word)
        val updatedBankWord = word.copy(buttonActive = false)
        _wordBank.update { bank ->
            bank.map { if (it.content == word.content) updatedBankWord else it }
        }
    }

    fun removeWordFromAnswer(word: AnswerWord){
        _givenAnswer.value -= word
        val updatedBankWord = word.bankWord.copy(buttonActive = true)
        _wordBank.update { bank ->
            bank.map { if (it.content == word.bankWord.content) updatedBankWord else it }
        }
    }

    override fun checkAnswer(): Boolean =
        _givenAnswer.value.map { it.bankWord.content } == task.answer

    private fun getBankFromTask( task: OrderTask): List<BankWord> {
        return task.answer
            .plus(task.additionalVariants)
            .map { BankWord(it) }
            .shuffled()
    }

    data class AnswerWord(
        val bankWord: BankWord
    )

    data class BankWord(
        val content: String,
        val buttonActive: Boolean = true
    )


}

