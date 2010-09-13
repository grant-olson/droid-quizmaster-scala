package net.grantolson.quizmaster

import android.app.Activity
import android.os.Bundle
import java.util.{Timer,TimerTask,Date}

trait countdown extends Activity {

  var timer:Timer = null
  var countdown = 0

  def createTimer(c:Int, tickAction: Int => Unit, stopAction: Unit => Unit) {
    timer = new Timer("quiztimer")
    countdown = c
    
    timer.scheduleAtFixedRate(new TimerTask() {
      val runnable = new Runnable()
	{
	  def run() {
	    countdown = countdown - 1
	    if (countdown <= 0) {
    	      timer.cancel()
	      stopAction()
	    } else {
	      tickAction(countdown)
	    }
	  }
	} 

      def run() { runOnUiThread(runnable) }
    } , new Date(), 1000 )

  }

}
