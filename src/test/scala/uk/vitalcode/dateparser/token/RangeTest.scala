package uk.vitalcode.dateparser.token

class RangeTest extends TokenTest {

  "parsing string containing valid range value" in {

    "-" should beToken(Range())
    "to" should beToken(Range())
    "until" should beToken(Range())
    "|" should beToken(Range())
  }

  "parsing string containing invalid range value" in {

    "after" shouldNot beToken[Range]
    "before" shouldNot beToken[Range]
  }
}
