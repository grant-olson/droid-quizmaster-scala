package net.grantolson;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent

abstract class Answers
case class Yes() extends Answers
case class No() extends Answers

abstract class QuestionType
case class YesNoQuestion(question: String, rightAnswer: Answers) extends QuestionType

abstract class QuestionList
case class YesNoList(name: String, yesText: String, noText: String, questions: List[YesNoQuestion]) extends QuestionList


object quizInfo {
  val star_trek_quiz = YesNoList("Star Trek Character or Star Trek Actor?", "Star Trek Character",
    			         "Star Trek Actor", List[YesNoQuestion](
				   YesNoQuestion("Armin Shimmerman", Yes()),
				   YesNoQuestion("William Riker", No()),
				   YesNoQuestion("Gates McFadden", No()),
				   YesNoQuestion("Tasha Yar", Yes()),
				   YesNoQuestion("B'Elanna Torres", Yes()),
				   YesNoQuestion("Harry Kim", Yes())
			         ))

  var remaining_questions = star_trek_quiz.questions
  var score = 0

  def getNextQuestion():Option[YesNoQuestion] = {
    remaining_questions match {
      case head :: tail =>
	remaining_questions = tail; return Some(head)
      case Nil => None
    }
  }
}

class quizQuestion extends Activity {
  def addRow(v:View):TableRow = {
    val tr = new TableRow(this)
    tr.addView(v)
    tr
  }

  def askNextQuestion(question:YesNoQuestion) : Unit = {
    val vg = new TableLayout(this)
     
    val title = new TextView(this)
    title.setText(quizInfo.star_trek_quiz.name)
    vg.addView(addRow(title))

    val q = new TextView(this)
    q.setText(question.question)
    vg.addView(addRow(q))

    val yesButton = new Button(this)
    yesButton.setText(quizInfo.star_trek_quiz.yesText)
    yesButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	question.rightAnswer match {
	  case Yes() => quizInfo.score += 1
	  case No() => ()
	}
        val myIntent:Intent = new Intent(vg.getContext(), classOf[quizQuestion])
        vg.getContext().startActivity(myIntent)
	
      }
    } )
    vg.addView(addRow(yesButton))

    val noButton = new Button(this)
    noButton.setText(quizInfo.star_trek_quiz.noText)
    noButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	question.rightAnswer match {
	  case No() => quizInfo.score += 1
	  case Yes() => ()
	}
        val myIntent:Intent = new Intent(vg.getContext(), classOf[quizQuestion])
        vg.getContext().startActivity(myIntent)
	
      }
    } )
    vg.addView(addRow(noButton))

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

