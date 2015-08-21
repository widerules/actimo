![http://actimo.googlecode.com/files/actimom.png](http://actimo.googlecode.com/files/actimom.png) 

# What is it? #

Actimo is a simple library that makes it easier to maintain and reuse code in Android Activities. It does so by introducing the possibility of adding modular _Features_, self-contained pieces of code that represent a single functionality.

Not only can _Features_ be reused in any configurations without worrying about the inheritance hierarchy of Activities (well, almost, more on that later), they also make the code more readable.

# So, it's like Fragments? #

In many aspects, yes - but the base concepts are different. [Here's a more detailed discussion](FeaturesVsFragments.md).

# Overview: #

  * Introduces _Features_, objects representing a single functionality.
  * _FeatureActivity_ subclasses may accept any number of _Features_.
    * You can add this functionality to any activity using the provided [template](Eclipse.md).
  * A _Feature_ has most of the lifecycle methods of an Activity (_onCreate()_,_onResume()_ etc.), which are called by the containing Activity as appropriate.
    * A special kind of _Feature_ for dialogs is also provided.
  * For Android 2.1+.


# Why use it? #

In short - to make yours and others' life easier.
  * If you are an app developer - use Actimo to have more modular and maintainable code, reducing the number of bugs you generate and the number of headaches in refactoring.
  * If you are a library developer - consider making additional Feature versions of your custom Activities, removing the burden your users are placed with in choosing and remodeling their inheritance hierarchies.

You can find [the full explanation HERE](WhyUseIt.md).

# How to get it? #

See [THIS](Usage#Installation.md).

# How to use it? #

Have a read through the short [Usage page](Usage.md).

Also, see the [JavaDoc](http://wiki.actimo.googlecode.com/git/apidocs/index.html).


# How was it made? #

Actimo was originally developed as a "labs" mini-project in the author's employer, Eniro.

# More information: #

Consult the [wiki](http://code.google.com/p/actimo/w/list).

![http://actimo.googlecode.com/files/logo3al.png](http://actimo.googlecode.com/files/logo3al.png)