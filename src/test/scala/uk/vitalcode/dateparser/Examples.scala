package uk.vitalcode.dateparser

import java.time.LocalDateTime

class Examples extends ExamplesSupport {

  override protected val currentDate: LocalDateTime = LocalDateTime.of(2017, 5, 6, 0, 0)

  "6 May 2017" in expected(
    DateTimeInterval.from(2017, 5, 6))

  "Monday, January 9th 2017 from 3:00 PM" in expected(
    DateTimeInterval.from(2017, 1, 9, 15, 0))

  "Friday, March 17th 2017 from 7:00 PM to 8:30 PM" in expected(
    DateTimeInterval.from(2017, 3, 17, 19, 0).to(2017, 3, 17, 20, 30))

  "Sept. 10, 2017 12:00pm" in expected(
    DateTimeInterval.from(2017, 9, 10, 12, 0))

  "Sept. 10, 2017 11:00am, 3:00pm" in expected(
    DateTimeInterval.from(2017, 9, 10, 11, 0),
    DateTimeInterval.from(2017, 9, 10, 15, 0))

  "Saturday 24 Jun 6:45pm (doors) | 11pm (curfew)" in expected(
    DateTimeInterval.from(2017, 6, 24, 18, 45).to(2017, 6, 24, 23, 0))

  "February, 23 17:25 - 18:05" in expected(
    DateTimeInterval.from(2018, 2, 23, 17, 25).to(2018, 2, 23, 18, 5))

  "Date:Sat 08 Jul, Time:8pm" in expected(
    DateTimeInterval.from(2017, 7, 8, 20, 0))

  "Date:Wed 2 May Time:5.45pm" in expected(
    DateTimeInterval.from(2018, 5, 2, 17, 45))

  "Time:Sun 27 Nov" in expected(
    DateTimeInterval.from(2017, 11, 27, 0, 0))

  "(1 Jan 2017 - 3 Jan 2017) 11:00 13:00" in expected(
    DateTimeInterval.from(2017, 1, 1, 11, 0).to(2017, 1, 1, 13, 0),
    DateTimeInterval.from(2017, 1, 2, 11, 0).to(2017, 1, 2, 13, 0),
    DateTimeInterval.from(2017, 1, 3, 11, 0).to(2017, 1, 3, 13, 0))

  "(1 Jan 2017 - 3 Jan 2017) Tuesday 11:00 13:00" in expected(
    DateTimeInterval.from(2017, 1, 3, 11, 0).to(2017, 1, 3, 13, 0))

  "(1 Jan 2016 - 4 Jan 2016) Monday 11:00 13:00 Tuesday 14:00 15:00 Friday 16:05 17:20 Sunday 19:30 20:45" in expected(
    DateTimeInterval.from(2016, 1, 1, 16, 5).to(2016, 1, 1, 17, 20),
    DateTimeInterval.from(2016, 1, 3, 19, 30).to(2016, 1, 3, 20, 45),
    DateTimeInterval.from(2016, 1, 4, 11, 0).to(2016, 1, 4, 13, 0))

  "(3 Feb 2017) Friday 19:30 21:30" in expected(
    DateTimeInterval.from(2017, 2, 3, 19, 30).to(2017, 2, 3, 21, 30))

  "Select date Tue 19 September 12:00pm Tue 19 September 2:00pm Tue 19 Sep 4:00pm (last few)" in expected(
    DateTimeInterval.from(2017, 9, 19, 12, 0),
    DateTimeInterval.from(2017, 9, 19, 14, 0),
    DateTimeInterval.from(2017, 9, 19, 16, 0))
}

