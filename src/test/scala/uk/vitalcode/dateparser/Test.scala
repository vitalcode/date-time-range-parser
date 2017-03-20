package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.token.{Day, Month, Year}

class Test extends FreeSpec with Matchers {


  "Type matching with for-comprehension part 2" in {
    val list = Month(4, 0) :: Month(2, 1) :: Day(12, 2) :: Day(16, 3) :: Day(20, 4) :: Year(2017, 5) :: Nil

    // month :: :: day :: :: year

    val r = for {
      Month(m, mi) <- list
      Day(d, di) <- list
      Year(y, yi) <- list
      if mi < di; if di < yi
    } yield (m, d, y)


    r should have size 6
  }
}
