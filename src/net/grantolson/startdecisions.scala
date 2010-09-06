package net.grantolson;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle


abstract class Answers
case class Yes() extends Answers
case class No() extends Answers

abstract class QuestionType
case class YesNoQuestion(question: String, rightAnswer: Answers) extends QuestionType

abstract class QuestionList
case class YesNoList(name: String, yesText: String, noText: String, questions: List[YesNoQuestion]) extends QuestionList

class startdecisions extends Activity {

  val star_trek_quiz = YesNoList("Star Trek Character or Star Trek Actor?", "Star Trek Character",
    			         "Star Trek Actor", List[YesNoQuestion](
				   YesNoQuestion("Armin Shimmerman", Yes()),
				   YesNoQuestion("William Riker", No())
			         ))

  def addRow(v:View):TableRow = {
    val tr = new TableRow(this)
    tr.addView(v)
    tr
  }
  
  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)

    for (question <- star_trek_quiz.questions) {
      val vg = new TableLayout(this)
      
      val title = new TextView(this)
      title.setText(star_trek_quiz.name)
      vg.addView(addRow(title))

      val q = new TextView(this)
      q.setText(question.question)
      vg.addView(addRow(q))

      val yesButton = new Button(this)
      yesButton.setText(star_trek_quiz.yesText)
      yesButton.setOnClickListener(new View.OnClickListener {def onClick(v: View) {}} )
      vg.addView(addRow(yesButton))

      val noButton = new Button(this)
      noButton.setText(star_trek_quiz.noText)
      noButton.setOnClickListener(new View.OnClickListener {def onClick(v: View) {}} )
      vg.addView(addRow(noButton))

      setContentView(vg)

    }
  }

}

