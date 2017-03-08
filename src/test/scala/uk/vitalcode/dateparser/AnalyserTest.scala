package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.Analyser.analyse
import uk.vitalcode.dateparser.DateTokenAggregator.indexTokenList

class AnalyserTest extends FreeSpec with ShouldMatchers {

  "Analyse single date token list" in {
    Seq(
      List(Date(12, 2, 2017), TimeRange(10, 11), TimeRange(14, 15)) -> List(
        DateTimeInterval.from(2017, 2, 12, 10, 0).to(2017, 2, 12, 11, 0),
        DateTimeInterval.from(2017, 2, 12, 14, 0).to(2017, 2, 12, 15, 0)
      )
    ).foreach(assert)
  }

  private def assert(testExpectations: (List[DateToken], List[DateTimeInterval])) = {
    analyse(indexTokenList(testExpectations._1)) shouldBe testExpectations._2
  }
}
