package net.grantolson.quizmaster.quizzes

import net.grantolson.quizmaster.adts._

object starTrek {
  val quiz = YesNoList("Star Trek Character or Star Trek Actor?", "Star Trek Character",
		       "Star Trek Actor", List[YesNoQuestion](
			 YesNoQuestion("Armin Shimmerman", Yes()),
			 YesNoQuestion("William Riker", No()),
			 YesNoQuestion("Gates McFadden", No()),
			 YesNoQuestion("Tasha Yar", Yes()),
			 YesNoQuestion("B'Elanna Torres", Yes()),
			 YesNoQuestion("Harry Kim", Yes())
		       ))

}
