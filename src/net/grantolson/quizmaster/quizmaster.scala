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

  def answerAction ( currentType: Answers, rightAnswer: Answers, nextAction: QuestionType => Unit )  = {
    quizInfo.currentQuestion += 1
    if (currentType == rightAnswer) {
      quizInfo.score += 1
      quizInfo.flashText = Some("\n" + goodFeedback() + "\n\n")
    } else {
      quizInfo.flashText = Some("\n" + badFeedback() + "\n\n")
    }
    quizInfo.getNextQuestion match {
      case None =>
	val myIntent:Intent = new Intent(this, classOf[quizScore])
        this.startActivity(myIntent)
      case Some(question) => nextAction(question)
    }
  }

  def askYesNoQuestion(question:YesNoQuestion) : Unit = {
    startRow()
    addText(question.question)
    endRow()

    startRow()
    addButton(quizInfo.yesText, { v:View => answerAction(Yes(), question.rightAnswer, { q => askNextQuestion(q) } ) })
    endRow()

    startRow()
    addButton(quizInfo.noText, { v:View => answerAction(No(), question.rightAnswer, { q => askNextQuestion(q) } ) })
    endRow()
 
  }

  def askMultipleChoiceQuestion(question:MultipleChoiceQuestion) : Unit = {

    startRow()
    addText(question.question)
    
    startRow()
    addButton(question.A, { v => answerAction(A(), question.rightAnswer, { q => askNextQuestion(q) }) })
    endRow()

    startRow()
    addButton(question.B, { v => answerAction(B(), question.rightAnswer, { q => askNextQuestion(q) }) })
    endRow()

    startRow()
    addButton(question.C, { v => answerAction(C(), question.rightAnswer, { q => askNextQuestion(q) }) })
    endRow()

    startRow()
    addButton(question.D, { v => answerAction(D(), question.rightAnswer, { q => askNextQuestion(q) }) })
    endRow()
  }


  def askNextQuestion(question:QuestionType) : Unit = {
    startLayout()

    startRow()
    addText(quizInfo.name)
    endRow()

    startRow()
    addText(quizInfo.yankFlashText())
    endRow()

    question match {
      case yn:YesNoQuestion => askYesNoQuestion(yn)
      case m:MultipleChoiceQuestion => askMultipleChoiceQuestion(m)
    }

    startRow()
    addText("\n\nQuestion " + quizInfo.currentQuestion + " of " + quizInfo.totalQuestions)
    endRow()

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

