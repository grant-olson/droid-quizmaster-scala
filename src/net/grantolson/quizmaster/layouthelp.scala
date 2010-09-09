package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._


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

  def makeButton(text:String, action: View => Unit ) = {
    val button = new android.widget.Button(this)
    button.setText(text)
    button.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	action(v)
      }
    })
    button
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
