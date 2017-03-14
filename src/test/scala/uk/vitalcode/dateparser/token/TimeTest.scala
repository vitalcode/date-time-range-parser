package uk.vitalcode.dateparser.token

class TimeTest extends TokenTest {

  "parsing string containing valid time" - {

    "create TimeToken for 24 hours time format string" in {
      "20:05:30" should beToken[Time](Time(20, 5, 30))
      "20:05:30" should beToken[Time](Time(20, 5, 30))
      "20:05" should beToken[Time](Time(20, 5))
      "20:5" should beToken[Time](Time(20, 5))
      "5:5" should beToken[Time](Time(5, 5))
      "05:5" should beToken[Time](Time(5, 5))
      "05:05" should beToken[Time](Time(5, 5))
      "5:5:20" should beToken[Time](Time(5, 5, 20))
      "5:5:05" should beToken[Time](Time(5, 5, 5))
      "5:5:9" should beToken[Time](Time(5, 5, 9))
    }

    "create TimeToken for 24 hours dot separated time format string" in {

      "20.05.30" should beToken[Time](Time(20, 5, 30))
      "5.5" should beToken[Time](Time(5, 5))
      "5.5.05" should beToken[Time](Time(5, 5, 5))
    }

    "create TimeToken for 12 hours time format string" in {

      "08:05PM" should beToken[Time](Time(20, 5))
      "08:05:25PM" should beToken[Time](Time(20, 5, 25))
      "8:5:5PM" should beToken[Time](Time(20, 5, 5))
      "08:5:5PM" should beToken[Time](Time(20, 5, 5))
      "08:05:5PM" should beToken[Time](Time(20, 5, 5))
      "08:5:05PM" should beToken[Time](Time(20, 5, 5))
      "08:5:05AM" should beToken[Time](Time(8, 5, 5))
      "12:0AM" should beToken[Time](Time(0, 0))
      "12:00PM" should beToken[Time](Time(12, 0))
      "08:05:25 PM" should beToken[Time](Time(20, 5, 25))
      "08:05:25 pm" should beToken[Time](Time(20, 5, 25))
      "8:5:5am" should beToken[Time](Time(8, 5, 5))
      "11pm" should beToken[Time](Time(23, 0))
      "6 AM" should beToken[Time](Time(6, 0))
    }

    "create TimeToken for 12 hours dot separated time format string" in {

      "8.05.30 pm" should beToken[Time](Time(20, 5, 30))
      "5.5 am" should beToken[Time](Time(5, 5))
      "5.5.05PM" should beToken[Time](Time(17, 5, 5))
    }

    "create TimeToken for time strings containing non-time related text" in {

      "Time:5.45pm" should beToken[Time](Time(17, 45))
    }
  }
  "parsing strings that do not contain valid time" - {

    "not result in TimeToken" in {

      "November" shouldNot beToken[Time]
      "12" shouldNot beToken[Time]
      "Monday" shouldNot beToken[Time]
    }
  }
}

