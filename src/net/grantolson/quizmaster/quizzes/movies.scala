package net.grantolson.quizmaster.quizzes

import net.grantolson.quizmaster.adts._

object movies {
  val quiz = MultipleChoiceQuiz("Movie Trivia", List[MultipleChoiceQuestion](
    MultipleChoiceQuestion("Who was the professor from Videodrome?", "Professor X", "Professor Brian O'Blivion", "Professor Einstein", "Professor Ninja", B()),
    MultipleChoiceQuestion("Harvey Keitel uttered the famous line\n 'Show me with your mouth'\n in what independent movie?", "Pulp Fiction", "Mean Streets", "Bad Lieutenant", "Albino Alligator", C()),
    MultipleChoiceQuestion("Laurence Fishburne's first leading role\n was in what movie?", "Apocalypse Now", "The Matrix", "School Daze", "Deep Cover", D())
    ))
}
