package uk.vitalcode.dateparser

import java.time.LocalDateTime

import org.scalatest._
import uk.vitalcode.dateparser.Analyser.analyse

class AnalyserTest extends FreeSpec with ShouldMatchers {

  "Analyse single date token list" in {
    val tokens = Date(12, 2, 2017, 1) :: TimeRange(10, 11, 2) :: TimeRange(14, 15, 3) :: Nil

    analyse(tokens) shouldBe List(
      DateTimeInterval(LocalDateTime.of(2017, 2, 12, 10, 0, 0), LocalDateTime.of(2017, 2, 12, 11, 0, 0)),
      DateTimeInterval(LocalDateTime.of(2017, 2, 12, 14, 0, 0), LocalDateTime.of(2017, 2, 12, 15, 0, 0))
    )
  }
}
