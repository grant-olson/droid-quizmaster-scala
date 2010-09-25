package net.grantolson.quizmaster;

import android.app.Activity
import android.media.MediaPlayer
import android.os.Vibrator
import android.content.Context

trait sounds extends Activity {

  def playSound(id:Int) {
    val mp: MediaPlayer = MediaPlayer.create(this, id)
    mp.start()
  }

  def beep() {
    playSound(R.raw.beep)
  }

  def buzz() {
    playSound(R.raw.buzz)

    val v:Vibrator = getSystemService(Context.VIBRATOR_SERVICE).asInstanceOf[Vibrator]
    v.vibrate(250)
  }

  def tick() {
    playSound(R.raw.tick)
  }
}
