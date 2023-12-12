package com.example.yp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yp.R
import com.example.yp.model.Question

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

    private var countOfCorrectAnswer = 0
    private var countOfIncorrectAnswer = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0

    fun getCurrentIndex(): Int {
        return currentIndex;
    }

    fun setCurrentIndex(value: Int) {
        currentIndex = value
    }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun incrementCorrectAnswer() {
        countOfCorrectAnswer++
    }

    fun incrementInCorrectAnswer() {
        countOfIncorrectAnswer++
    }

    fun getQuestionBank(): List<Question> {
        return questionBank
    }

    fun getCountOfIncorrectAnswer(): Int {
        return countOfIncorrectAnswer
    }

    fun getCountOfCorrectAnswer(): Int {
        return countOfCorrectAnswer
    }

    fun refreshGame() {
        countOfCorrectAnswer = 0
        countOfIncorrectAnswer = 0
        currentIndex = 0
    }

}
