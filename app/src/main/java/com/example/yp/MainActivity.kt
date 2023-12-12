package com.example.yp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.yp.viewModel.QuizViewModel

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var refreshButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.setCurrentIndex(currentIndex)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        refreshButton = findViewById(R.id.refresh_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            quizViewModel.moveToNext()
            updateQuestion()
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            quizViewModel.moveToNext()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        previousButton.setOnClickListener {
            if (quizViewModel.getCurrentIndex() > 0) {
                quizViewModel.moveToPrevious()
                updateQuestion()
            }
        }
        refreshButton.setOnClickListener {
            refreshGame()
        }

        updateQuestion()
    }

    private fun refreshGame() {
        quizViewModel.refreshGame()
        nextButton.isEnabled = true
        previousButton.isEnabled = true
        falseButton.isEnabled = true
        trueButton.isEnabled = true
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.getCurrentIndex())
    }


    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            quizViewModel.incrementCorrectAnswer()
        } else {
            messageResId = R.string.incorrect_toast
            quizViewModel.incrementInCorrectAnswer()
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        if (quizViewModel.getCurrentIndex() == quizViewModel.getQuestionBank().size - 1) {
            Toast.makeText(
                this,
                "Правильных: ${quizViewModel.getCountOfCorrectAnswer()} не правильных: ${quizViewModel.getCountOfIncorrectAnswer()}",
                Toast.LENGTH_LONG
            ).show()
            offButtons()
        }
    }

    private fun offButtons() {
        nextButton.isEnabled = false
        previousButton.isEnabled = false
        falseButton.isEnabled = false
        trueButton.isEnabled = false
    }
}