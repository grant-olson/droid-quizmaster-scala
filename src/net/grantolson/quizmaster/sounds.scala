package net.grantolson.quizmaster;

import android.app.Activity
import android.media.MediaPlayer

trait sounds extends Activity {

  def beep() {
    val mp: MediaPlayer = MediaPlayer.create(this, R.raw.beep)
    mp.start()
  }

  def buzz() {
    val mp: MediaPlayer = MediaPlayer.create(this, R.raw.buzz)
    mp.start()
  }
}
