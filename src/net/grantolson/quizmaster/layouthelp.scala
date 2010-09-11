package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._


trait layoutHelp extends Activity {
  var currentTable: Option[TableLayout] = None
  var currentRow: Option[List[View]] = None

  def startLayout(): Unit = {
    currentTable = Some(new TableLayout(this))
  }
    
  def endLayout(): Unit = {
    val v = currentTable match {
      case Some(tl) => tl
      case None => throw new Exception ("Oops!  Need to call startLayout() first!")
    }
    currentTable = None
    setContentView(v)
  }

  def startRow(): Unit = {
    currentRow = Some(List[View]())
  }

  def endRow(): Unit = {
    val tr = new TableRow(this)
    currentRow match {
      case Some(lv) => lv.reverse.map { v => tr.addView(v) }
      case None => throw new Exception ("Oops!  Need to call startRow() First!")
    }
    
    currentRow = None
    currentTable match {
      case Some(ct) => ct.addView(tr)
      case None => throw new Exception ("Oops!  Need to call startLayout() First!")
    }
  }
    

  def addToRow(v:View): Unit = {
    currentRow = currentRow match {
      case Some(lv) => Some(v :: lv)
      case None => throw new Exception ("Oops!  need to call starRow() first!")
    }
  }

  def addText(text: String): Unit = {
    val textBox = new TextView(this)
    textBox.setText(text)
    addToRow(textBox)
  }

  def addButton(text: String, action: View => Unit): Unit = {
    val button = new Button(this)
    button.setText(text)
    button.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
	action(v)
      }
    })
    addToRow(button)
  }

  // These will be gone
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
	   quizInfo.flashText = Some("\n" + goodFeedback() + "\n\n")
	 }
	else
	  { quizInfo.flashText = Some("\n" + badFeedback() + "\n\n") }
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

}
