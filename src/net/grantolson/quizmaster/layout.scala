package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.Color

import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

trait layout extends Activity {
  var currentTable: Option[TableLayout] = None
  var currentRow: Option[List[View]] = None

  private val MAX_WIDTH = 320 // this isn't queryable in droid 1.5

  def startLayout(): Unit = {
    val tl = new TableLayout(this)
    tl.setStretchAllColumns(true)

    tl.setBackgroundResource(R.drawable.background)

    currentTable = Some(tl)

    addImageRow(R.drawable.quizmaster)

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
 
  def addImage(resourceId:Int): Unit = {
    val imageView = new ImageView(this)
    imageView.setImageResource(resourceId)
    addToRow(imageView)
  }

  def addImageRow(resourceId:Int): Unit = {
    startRow()
    addImage(resourceId)
    endRow()
  }

  def addText(text: String, face:Typeface = Typeface.DEFAULT, style:Int = Typeface.NORMAL, color:Int = Color.WHITE): TextView = {
    val textBox = new TextView(this)
    textBox.setSingleLine(false)
    textBox.setMaxWidth(MAX_WIDTH)
    textBox.setTypeface(android.graphics.Typeface.create(face,style))
    textBox.setText(text)
    textBox.setTextColor(color)
    addToRow(textBox)
    textBox
  }

  def addTextRow(text: String, face:Typeface = Typeface.DEFAULT, style: Int = Typeface.NORMAL, color:Int = Color.WHITE): TextView = {
    startRow()
    val textBox = addText(text,face,style,color)
    endRow()
    textBox
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
