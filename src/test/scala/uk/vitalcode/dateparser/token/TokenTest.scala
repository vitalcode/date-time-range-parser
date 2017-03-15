package uk.vitalcode.dateparser.token

import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FreeSpec, _}

import scala.util.{Success, Try}

abstract class TokenTest extends FreeSpec with ShouldMatchers {

  trait TokenMatcher[Token] {

    def beToken(right: Option[Token]): Matcher[String]
  }

  protected def CreateTokenMatcher[Token](context: String, parse: String => Try[Token]) = new TokenMatcher[Token] {

    private def canParse(text: String, token: Token): Boolean = {
      parse(text) match {
        case Success(year) => year == token
        case _ => false
      }
    }

    override def beToken(right: Option[Token]): Matcher[String] = new Matcher[String] {
      def apply(left: String): MatchResult = {
        MatchResult(
          right.exists(token => canParse(left, token)),
          s"String [$left] does not result in ${if (right.isEmpty) context else "token"} ${right.getOrElse("token")}",
          s"String [$left] results in token ${right.getOrElse("")} but it must not"
        )
      }
    }
  }

  protected def beToken[Token](implicit tokenMatcher: TokenMatcher[Token]): Matcher[String] = {
    tokenMatcher.beToken(None)
  }

  protected def beToken[Token](right: Token)(implicit tokenMatcher: TokenMatcher[Token]): Matcher[String] = {
    tokenMatcher.beToken(Some(right))
  }

  implicit val yearTokenMatcher: TokenMatcher[Year] = CreateTokenMatcher[Year]("Year", token => Year.of(token, 0))

  implicit val timeTokenMatcher: TokenMatcher[Time] = CreateTokenMatcher[Time]("Time", token => Time.of(token, 0))

  implicit val monthTokenMatcher: TokenMatcher[Month] = CreateTokenMatcher[Month]("Month", token => Month.of(token, 0))

  implicit val weekDayTokenMatcher: TokenMatcher[WeekDay] = CreateTokenMatcher[WeekDay]("WeekDay", token => WeekDay.of(token, 0))

  implicit val dayTokenMatcher: TokenMatcher[Day] = CreateTokenMatcher[Day]("Day", token => Day.of(token, 0))
}

