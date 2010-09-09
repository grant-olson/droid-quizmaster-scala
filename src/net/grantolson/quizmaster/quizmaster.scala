package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

class quizScore extends Activity with layoutHelp {
  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    val table = makeTable()
    table.addView(addRow(makeText("Your score was " + quizInfo.score + " out of " + quizInfo.totalQuestions + ".\n")))
    table.addView(addRow(makeButton("Play again", { view: View =>
        val myIntent:Intent = new Intent(this, classOf[startquizmaster])
        this.startActivity(myIntent) } ))) 

    setContentView(table)
  }
}


class quizQuestion extends Activity with layoutHelp {
  def askYesNoQuestion(vg:ViewGroup,question:YesNoQuestion) : Unit = {
    vg.addView(makeText(question.question))
 
    vg.addView(makeAnswerButton(quizInfo.yesText,Yes(), question.rightAnswer, { q => askNextQuestion(q) }))
    vg.addView(makeAnswerButton(quizInfo.noText,No(), question.rightAnswer,{ q => askNextQuestion(q) }))
  }

  def askMultipleChoiceQuestion(vg:ViewGroup,question:MultipleChoiceQuestion) : Unit = {
    vg.addView(makeText(question.question))
    
    vg.addView(makeAnswerButton(question.A, A(), question.rightAnswer,{ q => askNextQuestion(q) }))
    vg.addView(makeAnswerButton(question.B, B(), question.rightAnswer,{ q => askNextQuestion(q) }))
    vg.addView(makeAnswerButton(question.C, C(), question.rightAnswer,{ q => askNextQuestion(q) }))
    vg.addView(makeAnswerButton(question.D, D(), question.rightAnswer,{ q => askNextQuestion(q) }))
  }


  def askNextQuestion(question:QuestionType) : Unit = {
    val table = makeTable()

    table.addView(makeText(quizInfo.name))

    table.addView(makeText(quizInfo.yankFlashText()))

    question match {
      case yn:YesNoQuestion => askYesNoQuestion(table, yn)
      case m:MultipleChoiceQuestion => askMultipleChoiceQuestion(table, m)
    }

    table.addView(makeText("\n\nQuestion " + quizInfo.currentQuestion + " of " + quizInfo.totalQuestions))
    
    setContentView(table)
  }

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    quizInfo.getNextQuestion() match {
      case Some(question) => askNextQuestion(question)
      case None =>
        val myIntent:Intent = new Intent(this, classOf[startquizmaster])
        this.startActivity(myIntent)
    }
  }
}

class startquizmaster extends Activity with layoutHelp {


  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    val table = makeTable()

    table.addView(addRow(makeQuizButton("Star Trek", starTrek.quiz)))
    table.addView(addRow(makeQuizButton("Movie", movies.quiz)))
    setContentView(table)
  }


}

