package com.example.langlearnkt.viewmodels

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.langlearnkt.R
import com.example.langlearnkt.data.Lesson
import com.example.langlearnkt.data.ParagraphData
import com.example.langlearnkt.data.TitleParagraphTask

class TitleParagraphTaskViewModel(
    context: Context
): ViewModel() {
    var task: TitleParagraphTask = TitleParagraphTask(
        listOf(
            ParagraphData(getString(context, R.string.test_paragraph1),"A", "Title 1"),
            ParagraphData(getString(context, R.string.test_paragraph2),"B", "Title 2"),
            ParagraphData(getString(context, R.string.test_paragraph3),"C", "Title 3"),
            ParagraphData(getString(context, R.string.test_paragraph1),"D", "Title 4"),
            ParagraphData(getString(context, R.string.test_paragraph2),"E", "Title 5"),
            ParagraphData(getString(context, R.string.test_paragraph3),"F", "Title 6")
        )
    )

    private val _paragraphs = MutableLiveData<List<ParagraphData>>(task.paragraphs)
    val paragraphs : LiveData<List<ParagraphData>> =_paragraphs

    private val _titleBank = MutableLiveData<List<TitleBankItem>>(
        task.paragraphs.shuffled().map { x-> TitleBankItem( x.title) }
    )
    val titleBank: LiveData<List<TitleBankItem>> = _titleBank

    private val _selectedTitle = MutableLiveData<TitleBankItem>()
    val selectedTitle: LiveData<TitleBankItem> = _selectedTitle

    fun onTitleClick(title: TitleBankItem)
    {
        _selectedTitle.value = title
    }

    data class TitleBankItem(
        val title: String,
        val active: Boolean = true
    )

}

