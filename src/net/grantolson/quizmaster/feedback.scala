package net.grantolson.quizmaster

abstract class randomizedFeedback {
  val options:Array[String]
  val rng = new scala.util.Random()
  
  def apply(): String = {
    val random = rng.nextInt(options.length)
    options(random)
  }
}

object goodFeedback extends randomizedFeedback {
  override val options = Array[String]("Excellent...", "Righto!", "Boom-shaka-laka!", "Put another one on the board for Gil!",
			 "Your Kung-Fu is the best.")
}

object badFeedback extends randomizedFeedback {
  override val options = Array[String]("Lame.", "You can do better than that.", "Absolutely WRONG.", "Ignorance is Bliss")
}

object timeoutFeedback extends randomizedFeedback {
  override val options = Array[String]("You're not even trying!", "You can do better than that!", "Just hit a button!  Any button!")
}
