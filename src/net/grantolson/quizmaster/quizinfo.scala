package net.grantolson.quizmaster;

import android.app.Activity
import android.view._
import android.widget._
import android.os.Bundle
import android.content.Intent
import net.grantolson.quizmaster.adts._
import net.grantolson.quizmaster.quizzes._

object quizInfo {
  var currentQuiz:Quiz = net.grantolson.quizmaster.quizzes.starTrek.quiz
  var remainingQuestions = currentQuiz match {
    case yn:YesNoQuiz => yn.questions
    case mc:MultipleChoiceQuiz => mc.questions
  }
  var score = 0
  var currentQuestion = 1
  var totalQuestions = 1

  var flashText:Option[String] = None
  
  def reset(quiz:Quiz): Unit = {
    currentQuiz = quiz
    remainingQuestions = currentQuiz match {
      case yn:YesNoQuiz => yn.questions
      case mc:MultipleChoiceQuiz => mc.questions
    }
    score = 0
    currentQuestion = 1
    totalQuestions = remainingQuestions.length
  }

  def yankFlashText():String = {
    flashText match {
      case None => ""
      case Some(x) =>
	flashText = None;x
    }
  }

  def getNextQuestion():Option[QuestionType] = {
    remainingQuestions match {
      case head :: tail =>
	remainingQuestions = tail; return Some(head)
      case Nil => None
    }
  }

  def name():String = currentQuiz match {
    case yn:YesNoQuiz => yn.name
    case mc:MultipleChoiceQuiz => mc.name
  }

  def yesText():String = currentQuiz match {
    case yn:YesNoQuiz => yn.yesText
  }

  def noText():String = currentQuiz match {
    case yn:YesNoQuiz => yn.noText
  }
}

