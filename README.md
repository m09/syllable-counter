# Syllable Counter

![CI Status](https://github.com/m09/syllable-counter/workflows/CI/badge.svg)
![Deploy Status](https://github.com/m09/syllable-counter/workflows/Deploy/badge.svg)
[![Maven Central version](https://img.shields.io/maven-central/v/eu.crydee/syllable-counter.svg)](https://search.maven.org/search?q=g:eu.crydee%20a:syllable-counter)
[![codecov](https://codecov.io/gh/m09/syllable-counter/branch/master/graph/badge.svg)](https://codecov.io/gh/m09/syllable-counter)

Uses a fallback method—based on the [NLTK readability plugin][nltk] by Thomas Jakobsen <thomj05@student.uia.no> and Thomas Skardal <thomas04@student.uia.no>. This NLTK plugin is itself based on the algorithm implemented in the [Lingua::EN::Syllable perl module][perl] by Greg Fast <gdf@imsa.edu>.

Thanks to them for making their work available.

[nltk]: https://github.com/nltk/nltk_contrib/blob/master/nltk_contrib/readability/syllables_en.py
[perl]: http://search.cpan.org/~neilb/Lingua-EN-Syllable-0.26/

## Requirements

To use this Java library, you need Java 8 and Maven 3.

## Installation

Refer to the [Maven Central page](https://search.maven.org/search?q=g:eu.crydee%20a:syllable-counter) to find the installation instructions for your build tool or to download the jar directly.

## Usage

The usage is trivial. To retrieve the number of syllables of a word, use the `count` method:

```java
import eu.crydee.syllablecounter.SyllableCounter;

...

SyllableCounter sc = new SyllableCounter();
int myCount = sc.count("facility");
// myCount holds 4
```
