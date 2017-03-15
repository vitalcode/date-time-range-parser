package uk.vitalcode.dateparser.token

class DayTest extends TokenTest {

  "parsing string containing valid day of month value" in {

    "6" should beToken(Day(6))
    "17" should beToken(Day(17))
    "21" should beToken(Day(21))
    "08" should beToken(Day(8))
  }

  "parsing string containing invalid day of month value" in {

    "41" shouldNot beToken[Day]
    "Sunday" shouldNot beToken[Day]
    "2017" shouldNot beToken[Day]
    "3:00PM" shouldNot beToken[Day]
  }
}
