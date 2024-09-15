package com.john.triviagame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.john.triviagame.databinding.ActivitySportsBinding

class SportsActivity : AppCompatActivity() {

    lateinit var sportsBinding: ActivitySportsBinding
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var databaseReference : DatabaseReference = database.reference.child("Sports")

    var questionNumber : Int = 1
    var wrongScore = 0
    var correctScore = 0
    lateinit var correctAnswer : String

    lateinit var timer : CountDownTimer
    var gameTimer: Long = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sportsBinding = ActivitySportsBinding.inflate(layoutInflater)
        val view = sportsBinding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gameLogic()
        sportsBinding.textViewA.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "a"
            if (userValue == correctAnswer){
                sportsBinding.textViewA.setBackgroundColor(Color.GREEN)
                correctScore++
                sportsBinding.textViewCorrectScore.text = correctScore.toString()
            }
            else{
                sportsBinding.textViewA.setBackgroundColor(Color.RED)
                wrongScore++
                sportsBinding.textViewWrongAnswerScore.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)

        }

        sportsBinding.textViewB.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "b"
            if (userValue == correctAnswer){
                sportsBinding.textViewB.setBackgroundColor(Color.GREEN)
                correctScore++
                sportsBinding.textViewCorrectScore.text = correctScore.toString()
            }
            else{
                sportsBinding.textViewB.setBackgroundColor(Color.RED)
                wrongScore++
                sportsBinding.textViewWrongAnswerScore.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)
        }

        sportsBinding.textViewC.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "c"
            if (userValue == correctAnswer){
                sportsBinding.textViewC.setBackgroundColor(Color.GREEN)
                correctScore++
                sportsBinding.textViewCorrectScore.text = correctScore.toString()
            }
            else{
                sportsBinding.textViewC.setBackgroundColor(Color.RED)
                wrongScore++
                sportsBinding.textViewWrongAnswerScore.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)

        }

        sportsBinding.textViewD.setOnClickListener {
            disableButtons()
            timer.cancel()
            val userValue = "d"
            if (userValue == correctAnswer){
                sportsBinding.textViewD.setBackgroundColor(Color.GREEN)
                correctScore++
                sportsBinding.textViewCorrectScore.text = correctScore.toString()
            }
            else{
                sportsBinding.textViewD.setBackgroundColor(Color.RED)
                wrongScore++
                sportsBinding.textViewWrongAnswerScore.text = wrongScore.toString()
            }
            getCorrectAnswer(correctAnswer)

        }

        sportsBinding.buttonNext.setOnClickListener {
            gameLogic()
        }

        sportsBinding.buttonFinish.setOnClickListener {
            val intent = Intent(this@SportsActivity,ResultActivity::class.java)
            intent.putExtra("userCorrectScore",correctScore)
            intent.putExtra("userWrongScore",wrongScore)
            startActivity(intent)
            finish()
        }

    }

    fun gameLogic(){

        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                startTimer(gameTimer)
                enableButtons()
                removeTextViewBackgroundColor()
                if (questionNumber <=5){
                    sportsBinding.textViewQuestion.text = snapshot.child(questionNumber.toString()).child("q").value.toString()
                    sportsBinding.textViewA.text = snapshot.child(questionNumber.toString()).child("a").value.toString()
                    sportsBinding.textViewB.text = snapshot.child(questionNumber.toString()).child("b").value.toString()
                    sportsBinding.textViewC.text = snapshot.child(questionNumber.toString()).child("c").value.toString()
                    sportsBinding.textViewD.text = snapshot.child(questionNumber.toString()).child("d").value.toString()
                    correctAnswer = snapshot.child(questionNumber.toString()).child("correct").value.toString()

                    sportsBinding.progressBarSports.visibility = View.INVISIBLE
                    sportsBinding.LinearLayoutContents.visibility = View.VISIBLE
                    sportsBinding.textViewQuestion.visibility = View.VISIBLE
                    sportsBinding.textViewA.visibility = View.VISIBLE
                    sportsBinding.textViewB.visibility = View.VISIBLE
                    sportsBinding.textViewC.visibility = View.VISIBLE
                    sportsBinding.textViewD.visibility = View.VISIBLE
                    sportsBinding.linearLayoutButtons.visibility = View.VISIBLE
                }
                else{
                    val intent = Intent(this@SportsActivity,ResultActivity::class.java)
                    intent.putExtra("userCorrectScore",correctScore)
                    intent.putExtra("userWrongScore",wrongScore)
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

    fun removeTextViewBackgroundColor(){

        sportsBinding.textViewA.setBackgroundColor(Color.WHITE)
        sportsBinding.textViewB.setBackgroundColor(Color.WHITE)
        sportsBinding.textViewC.setBackgroundColor(Color.WHITE)
        sportsBinding.textViewD.setBackgroundColor(Color.WHITE)
    }

    fun getCorrectAnswer(correctAnswer:String){

        when(correctAnswer){

            "a" -> sportsBinding.textViewA.setBackgroundColor(Color.GREEN)
            "b" -> sportsBinding.textViewB.setBackgroundColor(Color.GREEN)
            "c" -> sportsBinding.textViewC.setBackgroundColor(Color.GREEN)
            "d" -> sportsBinding.textViewD.setBackgroundColor(Color.GREEN)

        }

    }

    fun disableButtons(){

        sportsBinding.textViewA.isClickable = false
        sportsBinding.textViewB.isClickable = false
        sportsBinding.textViewC.isClickable = false
        sportsBinding.textViewD.isClickable = false

    }

    fun enableButtons(){
        sportsBinding.textViewA.isClickable = true
        sportsBinding.textViewB.isClickable = true
        sportsBinding.textViewC.isClickable = true
        sportsBinding.textViewD.isClickable = true
    }

    fun startTimer(gameTimer : Long){

        timer = object : CountDownTimer(gameTimer,1000){
            override fun onTick(milliUntilFinished: Long) {
                val updatedTimer = milliUntilFinished/1000
                sportsBinding.textViewTimer.text = updatedTimer.toString()
            }

            override fun onFinish() {
                disableButtons()
                sportsBinding.textViewQuestion.text = "Your Time is UP!"
                wrongScore++
                sportsBinding.textViewWrongAnswerScore.text = wrongScore.toString()
            }

        }.start()

    }

    fun resetTimer(){

        gameTimer = 60000

    }

}