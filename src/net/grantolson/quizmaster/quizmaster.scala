package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

object quizInfo {
  var currentQuiz:Quiz = net.grantolson.quizmaster.quizzes.starTrek.quiz
  var remainingQuestions = currentQuiz match {
    case yn:YesNoQuiz => yn.questions
    case mc:MultipleChoiceQuiz => mc.questions
  }
  var score = 0
  var currentQuestion = 1
  var totalQuestions = 1

  var flashText:Option[String] = None
  
  def reset(quiz:Quiz): Unit = {
    currentQuiz = quiz
    remainingQuestions = currentQuiz match {
      case yn:YesNoQuiz => yn.questions
      case mc:MultipleChoiceQuiz => mc.questions
    }
    score = 0
    currentQuestion = 1
    totalQuestions = remainingQuestions.length
  }

  def yankFlashText():String = {
    flashText match {
      case None => ""
      case Some(x) =>
	flashText = None;x
    }
  }

  def getNextQuestion():Option[QuestionType] = {
    remainingQuestions match {
      case head :: tail =>
	remainingQuestions = tail; return Some(head)
      case Nil => None
    }
  }

  def name():String = currentQuiz match {
    case yn:YesNoQuiz => yn.name
    case mc:MultipleChoiceQuiz => mc.name
  }

  def yesText():String = currentQuiz match {
    case yn:YesNoQuiz => yn.yesText
  }

  def noText():String = currentQuiz match {
    case yn:YesNoQuiz => yn.noText
  }
}
trait layoutHelp extends Activity {
  def addRow(v:View):TableRow = {
    val tr = new TableRow(this)
    tr.addView(v)
    tr
  }

  def makeText(text: String): TableRow = {
    val textBox = new TextView(this)
    textBox.setText(text)
    
    addRow(textBox)
  }

  def makeAnswerButton(text: String, currentType: Answers, rightAnswer: Answers, nextAction:  QuestionType => Unit) = {
    val button = new Button(this)
    button.setText(text)
    val me = this // 'this' in the anon class won't work
    button.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	quizInfo.currentQuestion += 1
	if (currentType == rightAnswer)
	 {
	   quizInfo.score += 1
	   quizInfo.flashText = Some("\n" + goodFeedback.getFeedback() + "\n\n")
	 }
	else
	  { quizInfo.flashText = Some("\n" + badFeedback.getFeedback() + "\n\n") }
        quizInfo.getNextQuestion match {
	  case None =>
	    val myIntent:Intent = new Intent(me, classOf[quizScore])
            me.startActivity(myIntent)
	  case Some(question) => nextAction(question)
	}
	
      }
    } )
    addRow(button)
  }

  def makeTable(): TableLayout = {
    new TableLayout(this)
  }

  def makeQuizButton(text:String, quiz:Quiz) = {
    val button = new android.widget.Button(this)
    button.setText("Play " + text + " Trivia")
    val me = this
    button.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	quizInfo.reset(quiz)
        val myIntent:Intent = new Intent(me, classOf[quizQuestion])
        me.startActivity(myIntent)
      }
    } )
    button
  }
			 

}

class quizScore extends Activity with layoutHelp {
  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    val table = makeTable()
    table.addView(addRow(makeText("Your score was " + quizInfo.score + " out of " + quizInfo.totalQuestions + ".\n")))
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

