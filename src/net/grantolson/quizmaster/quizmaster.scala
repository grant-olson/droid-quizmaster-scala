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

    startLayout()

    startRow()
    addText("Your score was " + quizInfo.score + " out of " + quizInfo.totalQuestions + ".\n")
    endRow()

    startRow()
    addButton("Play again", { view: View =>
        val myIntent:Intent = new Intent(this, classOf[quizStartMenu])
        this.startActivity(myIntent) } )
    endRow()

    endLayout()
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
        val myIntent:Intent = new Intent(this, classOf[quizStartMenu])
        this.startActivity(myIntent)
    }
  }
}

class quizStartMenu extends Activity with layoutHelp {


  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    startLayout()
    startRow()
    addText("Welcome to QuizMaster!")
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

