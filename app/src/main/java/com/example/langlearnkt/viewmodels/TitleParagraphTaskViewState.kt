package com.example.langlearnkt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.langlearnkt.data.entities.ParagraphData
import com.example.langlearnkt.data.entities.TitleParagraphTask

class TitleParagraphTaskViewState(
    val task: TitleParagraphTask
): TaskViewState() {

    private val _paragraphs = MutableLiveData<List<ParagraphData>>(task.paragraphs)
    val paragraphs : LiveData<List<ParagraphData>> =_paragraphs

    private val _titleBank = MutableLiveData<List<TitleBankItem>>(
        task.paragraphs.shuffled().mapIndexed{ i, x-> TitleBankItem(x, i + 1) }
    )
    val titleBank: LiveData<List<TitleBankItem>> = _titleBank

    private val _selectedTitle = MutableLiveData<TitleBankItem?>()
    val selectedTitle: LiveData<TitleBankItem?> = _selectedTitle

    private val _titleParagraphMaps = MutableLiveData<List<TitleParagraphMap>>(
        task.paragraphs.map { x->TitleParagraphMap(x, null) }
    )
    val titleParagraphMaps: LiveData<List<TitleParagraphMap>> = _titleParagraphMaps

    private val _selectedMap = MutableLiveData<TitleParagraphMap?>()
    val selectedMap: LiveData<TitleParagraphMap?> = _selectedMap

    fun onTitleClick(title: TitleBankItem)
    {
        if(_selectedTitle.value == title){
            _selectedTitle.value = null
        }
        else{
            _selectedTitle.value = title
        }
        tryCombineSelected()
    }

    fun onMapClick(mapParam: TitleParagraphMap){
        var map = mapParam
        if(map.numberParagraph != null){
            _titleBank.value = titleBank.value?.map { x ->
                if (x.paragraph == map.numberParagraph)
                    TitleBankItem(x.paragraph, x.number, active = true)
                else x
            }
            val newMap = TitleParagraphMap(
                letterParagraph = map.letterParagraph,
                number = null,
                numberParagraph = null
            )
            map = newMap
            _titleParagraphMaps.value = titleParagraphMaps.value
                ?.map { x ->
                    if (x == mapParam) newMap
                    else x
                }
        }
        if (_selectedMap.value == map){
            _selectedMap.value = null
        }
        else{
            _selectedMap.value = map
        }
        tryCombineSelected()
    }

    private fun tryCombineSelected(){
        if(selectedMap.value!= null && selectedTitle.value != null){
            _titleParagraphMaps.value = titleParagraphMaps.value
                ?.map { x ->
                    if (x == selectedMap.value)
                        TitleParagraphMap(
                            letterParagraph = selectedMap.value!!.letterParagraph,
                            number = selectedTitle.value!!.number,
                            numberParagraph = selectedTitle.value?.paragraph
                        )
                    else x
                }
            _titleBank.value = _titleBank.value
                ?.map { x ->
                    if (x == selectedTitle.value)
                        TitleBankItem(x.paragraph, x.number, active = false)
                    else x
                }
            _selectedMap.value = null
            _selectedTitle.value = null
        }
    }

    data class TitleBankItem(
        val paragraph: ParagraphData,
        val number: Int,
        val active: Boolean = true
    )

    data class TitleParagraphMap(
        val letterParagraph: ParagraphData,
        val number: Int?,
        val numberParagraph: ParagraphData? = null
    )

    override fun checkAnswer(): Boolean {
        for (map in _titleParagraphMaps.value!!){
            if(map.numberParagraph != map.letterParagraph){
                return false
            }
        }
        return true
    }

}

