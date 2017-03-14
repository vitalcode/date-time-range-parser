package uk.vitalcode.dateparser.token

class YearTest extends TokenTest {

  "parsing string containing valid year value" - {

    "should be a number in 1900-2099 interval" in {
      "1900" should beToken[Year](Year(1900))
      "2099" should beToken[Year](Year(2099))
      "2017" should beToken[Year](Year(2017))

      "2100" shouldNot beToken[Year](Year(2100))
      "1801" shouldNot beToken[Year](Year(1801))
      "12" shouldNot beToken[Year]
    }

    "may contain other leading or trailing characters" in {
      "leading2018" should beToken[Year](Year(2018))
      "2019bc" should beToken[Year](Year(2019))
      "2020AD" should beToken[Year](Year(2020))
      "leading2000trailing" should beToken[Year](Year(2000))
    }
  }

  "parsing string that does not contain valid year value" in {
    "November" shouldNot beToken[Year]
    "Monday" shouldNot beToken[Year]
  }
}
