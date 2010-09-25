package net.grantolson.quizmaster;

import android.app.Activity
import android.media.MediaPlayer
import android.os.Vibrator
import android.content.Context

trait sounds extends Activity {

  def beep() {
    val mp: MediaPlayer = MediaPlayer.create(this, R.raw.beep)
    mp.start()
  }

  def buzz() {
    val mp: MediaPlayer = MediaPlayer.create(this, R.raw.buzz)
    mp.start()

    val v:Vibrator = getSystemService(Context.VIBRATOR_SERVICE).asInstanceOf[Vibrator]
    v.vibrate(250)
  }
}
