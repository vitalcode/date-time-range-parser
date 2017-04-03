# Date Time Range Parser

[![Build Status](https://travis-ci.org/vitalcode/date-time-range-parser.svg?branch=master)](https://travis-ci.org/vitalcode/date-time-range-parser?branch=master)
[![Codecov branch](https://img.shields.io/codecov/c/github/vitalcode/date-time-range-parser/master.svg)](http://codecov.io/github/vitalcode/date-time-range-parser?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/uk.vitalcode/date-time-range-parser_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/uk.vitalcode/date-time-range-parser_2.12)

Human style date time range parser

## Using

SBT
```scala
libraryDependencies += "uk.vitalcode" % "date-time-range-parser_2.12" % "0.0.5"
```

Maven
```xml
<dependency>
    <groupId>uk.vitalcode</groupId>
    <artifactId>date-time-range-parser_2.12</artifactId>
    <version>0.0.5</version>
</dependency>
```

Gradle
```
compile 'uk.vitalcode:date-time-range-parser_2.12:0.0.5'
```

## Examples

    import uk.vitalcode.dateparser.DateTimeInterval

    scala> DateTimeInterval.of("February, 23 17:25 - 18:05")

    res0: List[uk.vitalcode.dateparser.DateTimeInterval] = List(
    interval[from: 2018-02-23T17:25:00 to: 2018-02-23T18:05:00])


    scala> DateTimeInterval.of("1 Jan 2017 - 3 Jan 2017 11:00 13:00")

    res1: List[uk.vitalcode.dateparser.DateTimeInterval] = List(
    interval[from: 2017-01-01T11:00:00 to: 2017-01-01T13:00:00],
    interval[from: 2017-01-02T11:00:00 to: 2017-01-02T13:00:00],
    interval[from: 2017-01-03T11:00:00 to: 2017-01-03T13:00:00])


    scala> DateTimeInterval.of("1 Jan 2016 - 4 Jan 2016 Monday 11:00 13:00 Tuesday 14:00 15:00 Friday 16:05 17:20 Sunday 19:30 20:45")

    res2: List[uk.vitalcode.dateparser.DateTimeInterval] = List(
    interval[from: 2016-01-01T16:05:00 to: 2016-01-01T17:20:00],
    interval[from: 2016-01-03T19:30:00 to: 2016-01-03T20:45:00],
    interval[from: 2016-01-04T11:00:00 to: 2016-01-04T13:00:00])


    scala> DateTimeInterval.of("Thu 15 September 7:45pm 8:50pm Fri 16 September 7:45pm - 20:45 Sat 17 October 7:45pm to 21:10")

    res3: List[uk.vitalcode.dateparser.DateTimeInterval] = List(
    interval[from: 2017-09-15T19:45:00 to: 2017-09-15T20:50:00],
    interval[from: 2017-09-16T19:45:00 to: 2017-09-16T20:45:00],
    interval[from: 2017-10-17T19:45:00 to: 2017-10-17T21:10:00])

  See more examples [here](https://github.com/vitalcode/date-time-range-parser/blob/master/src/test/scala/uk/vitalcode/dateparser/Examples.scala)

# License

Date Time Range Parser is released under the MIT license. See LICENSE for [details](https://github.com/vitalcode/date-time-range-parser/blob/master/LICENSE).