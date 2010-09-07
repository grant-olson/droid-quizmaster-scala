package net.grantolson.quizmaster

object adts {

abstract class Answers

abstract class YesNoAnswers extends Answers
case class Yes() extends YesNoAnswers
case class No() extends YesNoAnswers

abstract class MultipleChoiceAnswers extends Answers

case class A() extends MultipleChoiceAnswers
case class B() extends MultipleChoiceAnswers
case class C() extends MultipleChoiceAnswers
case class D() extends MultipleChoiceAnswers

abstract class QuestionType
case class YesNoQuestion(question: String, rightAnswer: YesNoAnswers) extends QuestionType
case class MultipleChoiceQuestion(question: String, A: String, B: String, C: String, D: String, rightAnswer: MultipleChoiceAnswers) extends QuestionType
  
abstract class QuestionList
case class YesNoList(name: String, yesText: String, noText: String, questions: List[YesNoQuestion]) extends QuestionList
case class MultipleChoiceList(name: String, questions: List[MultipleChoiceQuestion]) extends QuestionList


}
