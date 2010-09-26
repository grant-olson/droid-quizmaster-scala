package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import android.graphics.Typeface
import android.graphics.Color

import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

abstract class quizBase extends Activity with layout with menu {}

class quizScore extends quizBase {

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    startLayout()

    addTextRow("Your score was " + quizInfo.score + ".\n You answered " + quizInfo.rightAnswers + " out of " + quizInfo.totalQuestions + ".\n", style=Typeface.BOLD)

    addButtonRow("Play again", { view: View =>
        val myIntent:Intent = new Intent(this, classOf[quizStartMenu])
        this.startActivity(myIntent) } )

    endLayout()
  }
}


class quizQuestion extends quizBase with countdown with sounds {

  val COUNTDOWN = 8

  def getNextQuestion(nextAction: QuestionType => Unit) {
    quizInfo.getNextQuestion match {
      case None =>
	val myIntent:Intent = new Intent(this, classOf[quizScore])
        this.startActivity(myIntent)
      case Some(question) => nextAction(question)
    }
  }


  def answerAction ( currentType: Answers, rightAnswer: Answers, nextAction: QuestionType => Unit )  = {
    timer.cancel()
    quizInfo.currentQuestion += 1
    if (currentType == rightAnswer) {
      quizInfo.score += countdown
      quizInfo.rightAnswers += 1
      beep()
      quizInfo.flashText = Some(goodFeedback() + "\n", Color.GREEN)
    } else {
      buzz()
      quizInfo.flashText = Some(badFeedback() + "\n", Color.RED)
    }
    getNextQuestion(nextAction)
  }

  def askYesNoQuestion(question:YesNoQuestion) : Unit = {
    addTextRow(question.question)

    addButtonRow(quizInfo.yesText, { v:View => answerAction(Yes(), question.rightAnswer, { q => askNextQuestion(q) } ) })
    addButtonRow(quizInfo.noText, { v:View => answerAction(No(), question.rightAnswer, { q => askNextQuestion(q) } ) })
  }

  def askMultipleChoiceQuestion(question:MultipleChoiceQuestion) : Unit = {

    addTextRow(question.question)
    
    addButtonRow(question.A, { v => answerAction(A(), question.rightAnswer, { q => askNextQuestion(q) }) })
    addButtonRow(question.B, { v => answerAction(B(), question.rightAnswer, { q => askNextQuestion(q) }) })
    addButtonRow(question.C, { v => answerAction(C(), question.rightAnswer, { q => askNextQuestion(q) }) })
    addButtonRow(question.D, { v => answerAction(D(), question.rightAnswer, { q => askNextQuestion(q) }) })
  }


  def askNextQuestion(question:QuestionType) : Unit = {
    startLayout()

    quizInfo.yankFlashText() match {
      case Some( (s:String,color:Int) ) =>
        addTextRow(s, face=Typeface.SANS_SERIF, style=Typeface.BOLD_ITALIC, color=color)
      case None => ()
    }

    question match {
      case yn:YesNoQuestion => askYesNoQuestion(yn)
      case m:MultipleChoiceQuestion => askMultipleChoiceQuestion(m)
    }


    val countdownTextView = addTextRow(COUNTDOWN.toString())

    createTimer(COUNTDOWN,
		{i:Int =>
		 if (i < COUNTDOWN - 1 && i > 0) tick()
		 countdownTextView.setText(i.toString)} ,
		{ _ =>
		  buzz()
		  quizInfo.flashText = Some(timeoutFeedback() + "\n", Color.RED)
		  getNextQuestion({ q => askNextQuestion(q) }) })

    addTextRow("\n\n" + quizInfo.name + " - Question " + quizInfo.currentQuestion + " of " + quizInfo.totalQuestions, style=Typeface.BOLD)

    endLayout()
  }

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    quizInfo.getNextQuestion() match {
      case Some(question) => askNextQuestion(question)
      case None =>
        val myIntent:Intent = new Intent(this, classOf[quizStartMenu])
        this.startActivity(myIntent)
    }
  }
}

class quizAbout extends quizBase {
  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    startScrollableLayout()
    startRow()
    addText("Quizmaster", style=Typeface.BOLD)
    endRow()

    startRow()
    addText("More info at http://github.com/grant-olson/droid-quizmaster-scala")
    endRow()

    startRow()
    addText("""
Copyright (c) 2010, Grant T. Olson
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

    * The name of the author(s) may not be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
""")
    endRow()

    endScrollableLayout()
  }
}

class quizStartMenu extends quizBase {

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    startLayout()
    startRow()
    addText("Welcome to QuizMaster!", style=Typeface.BOLD)
    endRow()

    startRow()
    addButton("Play Star Trek Trivia!",
	      { v:View => quizInfo.reset(starTrek.quiz)
	       val myIntent:Intent = new Intent(this, classOf[quizQuestion])
	       this.startActivity(myIntent)
	     })
    endRow()

    startRow()
    addButton("Play Movie Trivia!",
	      { v:View => quizInfo.reset(movies.quiz)
	       val myIntent:Intent = new Intent(this, classOf[quizQuestion])
	       this.startActivity(myIntent)
	     })
    endRow()

    endLayout()
  }


}

