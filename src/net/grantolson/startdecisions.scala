package net.grantolson;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle


abstract class DecisionType

case class Title(name: String) extends DecisionType
case class Button(name: String, activity: String) extends DecisionType
case class Decision(text: String, buttons: List[Button] ) extends DecisionType


trait Decisions {
  val title:String
  val decisions:List[DecisionType]

  def buildViewGroup(context:Activity):ViewGroup = {
    val vg = new LinearLayout(context)

    for (d <- decisions) d match {
	case Button(name,activity) => 
	  val button = new android.widget.Button(context)
	  button.setText(name)
	  vg.addView(button)
	
	case Title(name) =>
	  val text = new TextView(context)
	  text.setText(name)
	  vg.addView(text)
	
	case Decision(text,buttons) => 
	  val decision = new TextView(context)
	  decision.setText(text)
	  vg.addView(decision)
    }
    vg
  }

}

class startdecisions extends Activity with Decisions {

  val title = "START HERE"
  val decisions = List[DecisionType](Title("name"),Button("button","foo"),Decision("Foo",List[Button](Button("yes","yes"))))

  override def onCreate(savedInstanceState:Bundle) : Unit = {
    super.onCreate(savedInstanceState)
    setContentView(buildViewGroup(this))
  }
}

