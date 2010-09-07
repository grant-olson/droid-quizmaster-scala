package net.grantolson.quizmaster.quizzes

import net.grantolson.quizmaster.adts._

object movies {
  val quiz = MultipleChoiceList("Movie Trivia", List[MultipleChoiceQuestion](
    MultipleChoiceQuestion("Who was the professor from Videodrome?", "Professor X", "Professor Brian O'Blivion", "Professor Einstein", "Professor Ninja", B())
    ))

}
