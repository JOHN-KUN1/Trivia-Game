package com.john.triviagame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.john.triviagame.databinding.ActivityAnimalBinding

class AnimalActivity : AppCompatActivity() {

    lateinit var animalBinding: ActivityAnimalBinding
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var databaseReference: DatabaseReference = database.reference.child("Animal")

    var questionNumber: Int = 1
    var wrongScore = 0
    var correctScore = 0
    lateinit var correctAnswer: String

    lateinit var timer: CountDownTimer
    var gameTimer: Long = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        animalBinding = ActivityAnimalBinding.inflate(layoutInflater)
        val view = animalBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameLogic()
        animalBinding.animalTextViewA.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "a"
            if (userValue == correctAnswer) {
                animalBinding.animalTextViewA.setBackgroundColor(Color.GREEN)
                correctScore++
                animalBinding.textViewCorrectScoreAnimal.text = correctScore.toString()
            } else {
                animalBinding.animalTextViewA.setBackgroundColor(Color.RED)
                wrongScore++
                animalBinding.textViewWrongAnswerScoreAnimal.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)

        }

        animalBinding.animalTextViewB.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "b"
            if (userValue == correctAnswer) {
                animalBinding.animalTextViewB.setBackgroundColor(Color.GREEN)
                correctScore++
                animalBinding.textViewCorrectScoreAnimal.text = correctScore.toString()
            } else {
                animalBinding.animalTextViewB.setBackgroundColor(Color.RED)
                wrongScore++
                animalBinding.textViewWrongAnswerScoreAnimal.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)
        }

        animalBinding.animalTextViewC.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "c"
            if (userValue == correctAnswer) {
                animalBinding.animalTextViewC.setBackgroundColor(Color.GREEN)
                correctScore++
                animalBinding.textViewCorrectScoreAnimal.text = correctScore.toString()
            } else {
                animalBinding.animalTextViewC.setBackgroundColor(Color.RED)
                wrongScore++
                animalBinding.textViewWrongAnswerScoreAnimal.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)

        }

        animalBinding.animalTextViewD.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "d"
            if (userValue == correctAnswer) {
                animalBinding.animalTextViewD.setBackgroundColor(Color.GREEN)
                correctScore++
                animalBinding.textViewCorrectScoreAnimal.text = correctScore.toString()
            } else {
                animalBinding.animalTextViewD.setBackgroundColor(Color.RED)
                wrongScore++
                animalBinding.textViewWrongAnswerScoreAnimal.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)

        }

        animalBinding.buttonNextAnimal.setOnClickListener {
            gameLogic()
        }

        animalBinding.buttonFinishAnimal.setOnClickListener {
            val intent = Intent(this@AnimalActivity, ResultActivity::class.java)
            intent.putExtra("userCorrectScore", correctScore)
            intent.putExtra("userWrongScore", wrongScore)
            startActivity(intent)
            finish()
        }

    }

    fun gameLogic() {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                startTimer(gameTimer)
                enableButtons()
                removeTextViewBackgroundColor()
                if (questionNumber <= 5) {
                    animalBinding.textViewQuestionAnimal.text =
                        snapshot.child(questionNumber.toString()).child("q").value.toString()
                    animalBinding.animalTextViewA.text =
                        snapshot.child(questionNumber.toString()).child("a").value.toString()
                    animalBinding.animalTextViewB.text =
                        snapshot.child(questionNumber.toString()).child("b").value.toString()
                    animalBinding.animalTextViewC.text =
                        snapshot.child(questionNumber.toString()).child("c").value.toString()
                    animalBinding.animalTextViewD.text =
                        snapshot.child(questionNumber.toString()).child("d").value.toString()
                    correctAnswer =
                        snapshot.child(questionNumber.toString()).child("correct").value.toString()

                    animalBinding.progressBarAnimal.visibility = View.INVISIBLE
                    animalBinding.LinearLayoutContentsAnimal.visibility = View.VISIBLE
                    animalBinding.textViewQuestionAnimal.visibility = View.VISIBLE
                    animalBinding.animalTextViewA.visibility = View.VISIBLE
                    animalBinding.animalTextViewB.visibility = View.VISIBLE
                    animalBinding.animalTextViewC.visibility = View.VISIBLE
                    animalBinding.animalTextViewD.visibility = View.VISIBLE
                    animalBinding.linearLayoutButtonsAnimal.visibility = View.VISIBLE
                } else {
                    val intent = Intent(this@AnimalActivity, ResultActivity::class.java)
                    intent.putExtra("userCorrectScore", correctScore)
                    intent.putExtra("userWrongScore", wrongScore)
                    startActivity(intent)
                    finish()
                }

                questionNumber++

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun removeTextViewBackgroundColor() {

        animalBinding.animalTextViewA.setBackgroundColor(Color.WHITE)
        animalBinding.animalTextViewB.setBackgroundColor(Color.WHITE)
        animalBinding.animalTextViewC.setBackgroundColor(Color.WHITE)
        animalBinding.animalTextViewD.setBackgroundColor(Color.WHITE)
    }

    fun getCorrectAnswer(correctAnswer: String) {

        when (correctAnswer) {

            "a" -> animalBinding.animalTextViewA.setBackgroundColor(Color.GREEN)
            "b" -> animalBinding.animalTextViewB.setBackgroundColor(Color.GREEN)
            "c" -> animalBinding.animalTextViewC.setBackgroundColor(Color.GREEN)
            "d" -> animalBinding.animalTextViewD.setBackgroundColor(Color.GREEN)

        }

    }

    fun disableButtons() {

        animalBinding.animalTextViewA.isClickable = false
        animalBinding.animalTextViewB.isClickable = false
        animalBinding.animalTextViewC.isClickable = false
        animalBinding.animalTextViewD.isClickable = false

    }

    fun enableButtons() {
        animalBinding.animalTextViewA.isClickable = true
        animalBinding.animalTextViewB.isClickable = true
        animalBinding.animalTextViewC.isClickable = true
        animalBinding.animalTextViewD.isClickable = true
    }

    fun startTimer(gameTimer: Long) {

        timer = object : CountDownTimer(gameTimer, 1000) {
            override fun onTick(milliUntilFinished: Long) {
                val updatedTimer = milliUntilFinished / 1000
                animalBinding.textViewTimerAnimal.text = updatedTimer.toString()
            }

            override fun onFinish() {
                disableButtons()
                animalBinding.textViewQuestionAnimal.text = "Your Time is UP!"
                wrongScore++
                animalBinding.textViewWrongAnswerScoreAnimal.text = wrongScore.toString()
            }

        }.start()

    }

    fun resetTimer() {

        gameTimer = 60000

    }

}