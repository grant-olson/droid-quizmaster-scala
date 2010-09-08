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

class quizScore extends Activity {
  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    val text = new TextView(this)
    text.setText("Your score was " + quizInfo.score)
    setContentView(text)
  }
}


class quizQuestion extends Activity {
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

  def makeAnswerButton(text: String, currentType: Answers, rightAnswer: Answers) = {
    val button = new Button(this)
    button.setText(text)
    val me = this // 'this' in the anon class won't work
    button.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	quizInfo.currentQuestion += 1
	if (currentType == rightAnswer) { quizInfo.score += 1 }
        quizInfo.getNextQuestion match {
	  case None =>
	    val myIntent:Intent = new Intent(me, classOf[quizScore])
            me.startActivity(myIntent)
	  case Some(question) => askNextQuestion(question)
	}
	
      }
    } )
    addRow(button)
  }

  def askYesNoQuestion(vg:ViewGroup,question:YesNoQuestion) : Unit = {
    vg.addView(makeText(question.question))
    
    vg.addView(makeAnswerButton(quizInfo.yesText,Yes(), question.rightAnswer))
    vg.addView(makeAnswerButton(quizInfo.noText,No(), question.rightAnswer))
  }

  def askMultipleChoiceQuestion(vg:ViewGroup,question:MultipleChoiceQuestion) : Unit = {
    vg.addView(makeText(question.question))
    
    vg.addView(makeAnswerButton(question.A, A(), question.rightAnswer))
    vg.addView(makeAnswerButton(question.B, B(), question.rightAnswer))
    vg.addView(makeAnswerButton(question.C, C(), question.rightAnswer))
    vg.addView(makeAnswerButton(question.D, D(), question.rightAnswer))
  }


  def askNextQuestion(question:QuestionType) : Unit = {
    val vg = new TableLayout(this)

    vg.addView(makeText(quizInfo.name))

    question match {
      case yn:YesNoQuestion => askYesNoQuestion(vg, yn)
      case m:MultipleChoiceQuestion => askMultipleChoiceQuestion(vg, m)
    }

    vg.addView(makeText("\n\nQuestion " + quizInfo.currentQuestion + " of " + quizInfo.totalQuestions))
    
    setContentView(vg)
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

class startquizmaster extends Activity {

  def quizButton(name:String, quiz:Quiz) = {
    val button = new android.widget.Button(this)
    button.setText("Plau " + name + " Trivia")
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
			 

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    val vg = new LinearLayout(this)

    vg.addView(quizButton("Star Trek", starTrek.quiz))
    vg.addView(quizButton("Movie", movies.quiz))
    setContentView(vg)
  }


}

