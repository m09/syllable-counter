Overview
--------

The API is extremely simple. To retrieve the number of syllables of a word,
use the `count` static method:

    import eu.crydee.syllablecounter.SyllableCounter;

    ...
    
    int myCount = SyllableCounter.count("facility");
    // myCount holds 4
    
By default, the syllable counter caches up to 20 000 words. You can
modify this behavior with the `setCacheSize` method. A positive value
will set a different upper limit while a negative value will disable
the cache.
