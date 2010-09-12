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

class quizScore extends Activity with layout {
  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    startLayout()

    addTextRow("Your score was " + quizInfo.score + " out of " + quizInfo.totalQuestions + ".\n", style=Typeface.BOLD)

    addButtonRow("Play again", { view: View =>
        val myIntent:Intent = new Intent(this, classOf[quizStartMenu])
        this.startActivity(myIntent) } )

    endLayout()
  }
}


class quizQuestion extends Activity with layout {

  def answerAction ( currentType: Answers, rightAnswer: Answers, nextAction: QuestionType => Unit )  = {
    quizInfo.currentQuestion += 1
    if (currentType == rightAnswer) {
      quizInfo.score += 1
      quizInfo.flashText = Some("\n" + goodFeedback() + "\n", Color.RED)
    } else {
      quizInfo.flashText = Some("\n" + badFeedback() + "\n", Color.GREEN)
    }
    quizInfo.getNextQuestion match {
      case None =>
	val myIntent:Intent = new Intent(this, classOf[quizScore])
        this.startActivity(myIntent)
      case Some(question) => nextAction(question)
    }
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

    addTextRow(quizInfo.name, style=Typeface.BOLD)

    quizInfo.yankFlashText() match {
      case Some( (s:String,color:Int) ) =>
        addTextRow(s, face=Typeface.SANS_SERIF, style=Typeface.BOLD_ITALIC, color=color)
      case None => ()
    }

    question match {
      case yn:YesNoQuestion => askYesNoQuestion(yn)
      case m:MultipleChoiceQuestion => askMultipleChoiceQuestion(m)
    }

    addTextRow("\n\nQuestion " + quizInfo.currentQuestion + " of " + quizInfo.totalQuestions, style=Typeface.BOLD)

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

class quizStartMenu extends Activity with layout {


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

