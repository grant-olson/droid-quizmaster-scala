package net.grantolson.quizmaster.quizzes

import net.grantolson.quizmaster.adts._

object movies {
  val quiz = MultipleChoiceQuiz("Movie Trivia", List[MultipleChoiceQuestion](
    MultipleChoiceQuestion("Who was the professor from Videodrome?", "Professor X", "Professor Brian O'Blivion", "Professor Einstein", "Professor Ninja", B()),
    MultipleChoiceQuestion("Harvey Keitel uttered the famous line 'Show me with your mouth' in what independent movie?", "Pulp Fiction", "Mean Streets", "Bad Lieutenant", "Albino Alligator", C()),
    MultipleChoiceQuestion("Laurence Fishburne's first leading role was in what movie?", "Apocalypse Now", "The Matrix", "School Daze", "Deep Cover", D()),
    MultipleChoiceQuestion("What movie remake copied the original shot-for-shot?", "Psycho", "Ben Hur", "The Thing", "The Ring", A()),
    MultipleChoiceQuestion("The time-travel movie Primer has how many timelines?", "0", "1", "2", "Many", D())
    ))
}
