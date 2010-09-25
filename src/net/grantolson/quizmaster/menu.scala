package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.content.Intent

trait menu extends Activity {
  override def onCreateOptionsMenu(menu:Menu): Boolean = {
    val inflater = getMenuInflater()
    inflater.inflate(R.menu.main_menu, menu)
    true
  }

  override def onOptionsItemSelected(item:MenuItem): Boolean = {
    item.getItemId() match {
      case R.id.about =>
        val myIntent:Intent = new Intent(this, classOf[quizAbout])
        this.startActivity(myIntent)
	true
      case _ => super.onOptionsItemSelected(item);
    }
  }
}

