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

}
