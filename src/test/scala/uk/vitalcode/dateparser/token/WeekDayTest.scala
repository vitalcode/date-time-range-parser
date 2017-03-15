package uk.vitalcode.dateparser.token

import java.time.DayOfWeek

class WeekDayTest extends TokenTest {

  "parsing string containing valid week day value" - {

    "Saturday" should beToken(WeekDay(DayOfWeek.SATURDAY))
    "Thursday" should beToken(WeekDay(DayOfWeek.THURSDAY))
    "Fri" should beToken(WeekDay(DayOfWeek.FRIDAY))
  }

  "parsing string containing invalid week day value" - {

    "March" shouldNot beToken[WeekDay]
    "2017" shouldNot beToken[WeekDay]
    "14:04" shouldNot beToken[WeekDay]
    "6:00AM" shouldNot beToken[WeekDay]
  }
}
