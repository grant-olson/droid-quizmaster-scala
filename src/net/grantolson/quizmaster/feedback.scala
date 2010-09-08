package net.grantolson.quizmaster

trait randomizedFeedback {
  val options:Array[String] = Array[String]("good")
  val rng = new scala.util.Random()
  
  def getFeedback(): String = {
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
