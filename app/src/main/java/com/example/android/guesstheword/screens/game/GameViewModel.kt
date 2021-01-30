package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    // The current word
    private val _word = MutableLiveData<String>("")
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = this._score

    private val _eventGameFinish = MutableLiveData(false)
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private val _gameTime = MutableLiveData<Long>()
    val gameTime: LiveData<Long>
        get() = _gameTime

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        override fun onTick(p0: Long) {
            _gameTime.value = p0
        }
        override fun onFinish() {
            _eventGameFinish.value = true
        }
    }

    init {
        resetList()
        nextWord()
        timer.start()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        this._score.value = (this._score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        this._score.value = (this._score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}