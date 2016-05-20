Syllable Counter
===
[![Build Status](https://travis-ci.org/m09/syllable-counter.svg?branch=master)](https://travis-ci.org/m09/syllable-counter) [![Maven Central version](https://img.shields.io/maven-central/v/eu.crydee/syllable-counter.svg)](http://search.maven.org/#search|ga|1|a%3A%22syllable-counter%22) [![Coverage Status](https://coveralls.io/repos/m09/syllable-counter/badge.svg?branch=master)](https://coveralls.io/r/m09/syllable-counter?branch=master)

Uses a fallback method—based on the [NLTK readability plugin][nltk] by
Thomas Jakobsen <thomj05@student.uia.no> and Thomas Skardal
<thomas04@student.uia.no>. This NLTK plugin is itself based on the
algorithm implemented in the [Lingua::EN::Syllable perl module][perl]
by Greg Fast <gdf@imsa.edu>.

Thanks to them for making their work available.

[nltk]: https://code.google.com/p/nltk/source/browse/trunk/nltk_contrib/nltk_contrib/readability/syllables_en.py

[perl]: http://search.cpan.org/~neilb/Lingua-EN-Syllable-0.26/

Requirements
------------

To use this Java library, you need Java 8 and Maven 3.

Installation
------------

With Maven, you can just add the following to the `dependencies`
section of your `pom.xml`:

```xml
    <dependency>
      <groupId>eu.crydee</groupId>
      <artifactId>syllable-counter</artifactId>
      <version>4.0.0</version>
    </dependency>
```

If you do not use maven, you can still [download][dl] the jar from
Maven Central and use it as appropriate.

[dl]: http://search.maven.org/remotecontent?filepath=eu/crydee/syllable-counter/4.0.0/syllable-counter-4.0.0.jar

Usage
-----

The usage is trivial. To retrieve the number of syllables of a word,
use the `count` method:

```java
import eu.crydee.syllablecounter.SyllableCounter;

...

SyllableCounter sc = new SyllableCounter();
int myCount = sc.count("facility");
// myCount holds 4
```
