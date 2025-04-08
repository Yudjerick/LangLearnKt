package com.example.langlearnkt.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.langlearnkt.data.OrderTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderTaskViewModel: ViewModel() {
    var orderTask = OrderTask(0,"", "", listOf(
        "we", "are", "the", "champions"), listOf("losers", "a")
    )
        set(value){
            field = value
            _wordBank.value = getBankFromTask(value)
        }

    private val _givenAnswer = MutableStateFlow(listOf<AnswerWord>())
    val givenAnswer: StateFlow<List<AnswerWord>> = _givenAnswer.asStateFlow()

    private val _wordBank = MutableStateFlow(getBankFromTask(orderTask))
    val wordBank: StateFlow<List<BankWord>> = _wordBank.asStateFlow()

    private val _taskStatus = MutableLiveData<TaskStatus>(TaskStatus.Unchecked)
    val taskStatus: LiveData<TaskStatus> = _taskStatus

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

    fun checkAnswer(){
        if(_givenAnswer.value.map { it.bankWord.content } == orderTask.answer){
            _taskStatus.value = TaskStatus.Right
        }
        else{
            _taskStatus.value = TaskStatus.Wrong
        }
    }

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

    enum class TaskStatus{
        Unchecked,
        Right,
        Wrong
    }
}

