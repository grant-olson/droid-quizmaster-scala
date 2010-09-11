package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import android.graphics.Typeface

import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

trait layout extends Activity {
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
 
  def addText(text: String, face:Typeface = Typeface.DEFAULT, style:Int = Typeface.NORMAL): Unit = {
    val textBox = new TextView(this)
    textBox.setTypeface(android.graphics.Typeface.create(face,style))
    textBox.setText(text)
    addToRow(textBox)
  }

  def addTextRow(text: String, face:Typeface = Typeface.DEFAULT, style: Int = Typeface.NORMAL): Unit = {
    startRow()
    addText(text,face,style)
    endRow()
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
  
  def addButtonRow(text: String, action: View => Unit): Unit = {
    startRow()
    addButton(text, action)
    endRow()
  }

}
