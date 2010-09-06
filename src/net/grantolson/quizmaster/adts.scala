package net.grantolson.quizmaster

object adts {

abstract class Answers
case class Yes() extends Answers
case class No() extends Answers

abstract class QuestionType
case class YesNoQuestion(question: String, rightAnswer: Answers) extends QuestionType

abstract class QuestionList
case class YesNoList(name: String, yesText: String, noText: String, questions: List[YesNoQuestion]) extends QuestionList


}
