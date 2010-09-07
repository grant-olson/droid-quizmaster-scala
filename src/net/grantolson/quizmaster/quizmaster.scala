package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

object quizInfo {
  var currentQuiz = net.grantolson.quizmaster.quizzes.movies.quiz
  var remainingQuestions = currentQuiz.questions
  var score = 0

  def getNextQuestion():Option[QuestionType] = {
    remainingQuestions match {
      case head :: tail =>
	remainingQuestions = tail; return Some(head)
      case Nil => None
    }
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

  def askYesNoQuestion(question:YesNoQuestion) : Unit = {
/*    val vg = new TableLayout(this)
     
    val title = new TextView(this)
    title.setText(quizInfo.currentQuiz.name)
    vg.addView(addRow(title))

    val q = new TextView(this)
    q.setText(question.question)
    vg.addView(addRow(q))

    val yesButton = new Button(this)
    yesButton.setText(quizInfo.currentQuiz.yesText)
    yesButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	question.rightAnswer match {
	  case Yes() => quizInfo.score += 1
	  case No() => ()
	}
        quizInfo.getNextQuestion match {
	  case None =>
	    val myIntent:Intent = new Intent(vg.getContext(), classOf[quizScore])
            vg.getContext().startActivity(myIntent)
	  case Some(question) => askNextQuestion(question)
	}
	
      }
    } )
    vg.addView(addRow(yesButton))

    val noButton = new Button(this)
    noButton.setText(quizInfo.currentQuiz.noText)
    noButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	question.rightAnswer match {
	  case No() => quizInfo.score += 1
	  case Yes() => ()
	}
        quizInfo.getNextQuestion match {
	  case None =>
	    val myIntent:Intent = new Intent(vg.getContext(), classOf[quizScore])
            vg.getContext().startActivity(myIntent)
	  case Some(question) => askNextQuestion(question)
	}
      }
    } )
    vg.addView(addRow(noButton))

    setContentView(vg)
*/
  }

  def makeAnswerButton(text: String, currentType: Answers, rightAnswer: Answers) = {
    val button = new Button(this)
    button.setText(text)
    val me = this // 'this' in the anon class won't work
    button.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
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

  def askMultipleChoiceQuestion(question:MultipleChoiceQuestion) : Unit = {
    val vg = new TableLayout(this)
     
    val title = new TextView(this)
    title.setText(quizInfo.currentQuiz.name)
    vg.addView(addRow(title))

    val q = new TextView(this)
    q.setText(question.question)
    vg.addView(addRow(q))

    vg.addView(makeAnswerButton(question.A, A(), question.rightAnswer))
    vg.addView(makeAnswerButton(question.B, B(), question.rightAnswer))
    vg.addView(makeAnswerButton(question.C, C(), question.rightAnswer))
    vg.addView(makeAnswerButton(question.D, D(), question.rightAnswer))

    setContentView(vg)

  }


  def askNextQuestion(question:QuestionType) : Unit = {
    question match {
      case yn:YesNoQuestion => askYesNoQuestion(yn)
      case m:MultipleChoiceQuestion => askMultipleChoiceQuestion(m)
    }
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

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    val startButton = new android.widget.Button(this)
    startButton.setText("Click Here to Start")
    startButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
        val myIntent:Intent = new Intent(v.getContext(), classOf[quizQuestion])
        v.getContext().startActivity(myIntent)
      }
    } )

    val vg = new LinearLayout(this)
    vg.addView(startButton)

    setContentView(vg)
  }


}

