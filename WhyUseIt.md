## Simple code reuse ##

## The Problem ##

Let's assume you have three activities, _A_, _B_ and _C_. They have the following functionality:
  * _A_ - has a wake lock, displays standard dialog at start,
  * _B_ - has a wake lock, sends runtime statistics,
  * _C_ - displays standard dialog at start, sends runtime statistics.

![http://wiki.actimo.googlecode.com/git/images/actimo_intro_start.png](http://wiki.actimo.googlecode.com/git/images/actimo_intro_start.png)

Or in other words, they have the following common functionality:
  * wake lock: _A_,_B_.
  * standard dialog: _A_,_C_.
  * runtime statistics: _B_,_C_.

### Standard approaches ###

In such a situation, you have two solutions:
  1. Create a utility class with appropriate static methods.
  1. Create a superclass for all three activities. Vary functionality using e.g. constructor arguments.

![http://wiki.actimo.googlecode.com/git/images/actimo_intro_bad.png](http://wiki.actimo.googlecode.com/git/images/actimo_intro_bad.png)

Solution 1 is really the road to madness. You may have reduced the lines of code in each of your classes, but you still have to remember to do make the appropriate calls in each activity lifecycle method - forget about one, and that's a bug waiting to happen. Also, this solution only works for functionality that doesn't really require any state information to be stored.

Solution 2 is a bit better. You don't have to remember the call sequence, all is in one place. On the other, the common superclass is in danger of quickly being [deified](http://en.wikipedia.org/wiki/God_object). Also, when reading and changing this type of code, you are forced to wade through a veritable sea of ifs.

Of course, this type of structure could be much improved by containing the three different types of functionality in separate classes. And... this is the basic idea behind Actimo.

### In Actimo ###

In Actimo, you let all _A_,_B_,_C_ extend _FeatureActivity_. However, the three common types of functionality (statistics, standard dialog, and wake lock), are contained in separate classes, extending _AbstractFeature_. The diagram illustrates this type of separation.


![http://wiki.actimo.googlecode.com/git/images/actimo_intro_android.png](http://wiki.actimo.googlecode.com/git/images/actimo_intro_android.png)

Now, you've got all your functionality separated neatly into maintainable packets of code. To use them, the only thing you have to remember is to implement _initializeFeatures()_ in your activities, by calling _addFeature()_ for every one you want to include.


### Preserving existing inheritance ###

Let's say we have a situation like the above, but _C_ is already extending _MapActivity_ (from the Google Maps API). In such a situation we have to keep _MapActivity_ in Cs inheritance hierarchy.

In such a case we use the included [Eclipse template](http://code.google.com/p/actimo/downloads/detail?name=templates.xml) to create a "_FeatureMapActivity_" class, which extends _MapActivity_ and is extended, in turn, by _C_. Everything else works without problems.


![http://wiki.actimo.googlecode.com/git/images/actimo_intro_activity_extend.png](http://wiki.actimo.googlecode.com/git/images/actimo_intro_activity_extend.png)


More information may be found on the [Usage page](Usage.md).