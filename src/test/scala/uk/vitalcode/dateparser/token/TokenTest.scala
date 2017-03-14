package uk.vitalcode.dateparser.token

import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FreeSpec, _}

import scala.util.Success

abstract class TokenTest extends FreeSpec with ShouldMatchers {

  trait TokenMatcher[Token] {

    def beToken(right: Option[Token]): Matcher[String]
  }

  private def CreateTokenMatcher[Token](context: String, canParse: (String, Token) => Boolean) = new TokenMatcher[Token] {

    override def beToken(right: Option[Token]): Matcher[String] = new Matcher[String] {
      def apply(left: String): MatchResult = {
        MatchResult(
          right.exists(token => canParse(left, token)),
          s"String [$left] does not result in $context token ${right.getOrElse("")}",
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

  implicit val yearTokenMatcher: TokenMatcher[Year] = CreateTokenMatcher[Year]("Year", (text, token) => {
    Year.of(text, 0) match {
      case Success(year) => year == token
      case _ => false
    }
  })

  implicit val timeTokenMatcher: TokenMatcher[Time] = CreateTokenMatcher[Time]("Time", (text, token) => {
    Time.of(text, 0) match {
      case Success(time) => time == token
      case _ => false
    }
  })
}

