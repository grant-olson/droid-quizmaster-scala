package net.grantolson.quizmaster.quizzes

import net.grantolson.quizmaster.adts._

object starTrek {
  val quiz = YesNoQuiz("Star Trek Character or Star Trek Actor?", "Star Trek Character",
		       "Star Trek Actor", List[YesNoQuestion](
			 YesNoQuestion("Armin Shimmerman", No()),
			 YesNoQuestion("William Riker", Yes()),
			 YesNoQuestion("Gates McFadden", No()),
			 YesNoQuestion("Tasha Yar", Yes()),
			 YesNoQuestion("B'Elanna Torres", Yes()),
			 YesNoQuestion("Harry Kim", Yes()),
			 YesNoQuestion("Rene Auberjonois", No()),
			 YesNoQuestion("Jadzia Daz", Yes()),
			 YesNoQuestion("Cirroc Lofton", No()),
			 YesNoQuestion("Gul Dukat", Yes()),
			 YesNoQuestion("Nana Visitor", No()),
			 YesNoQuestion("Connor Trinneer", No()),
			 YesNoQuestion("Hoshi Sato", Yes())
		       ))

}
