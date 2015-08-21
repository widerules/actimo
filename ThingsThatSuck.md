# Introduction #

Here's a short discussion of things that smell a bit in the current Actimo implementation. These items may be transformed into issues in the future.


# Dialogs - poor support for "oneliners" #

One of the impulses for writing Actimo was the abysmally bad practice of launching Dialogs that are not managed by the Activity - e.g. ProgressDialog.show(). This happens all too often in Market apps - an example, is a mainstream IME app that would crash when the orientation was changed on a progress dialog in such a way that, I kid you not, a reinstall wouldn't help - you had to manually delete the data files from the SD card!

However, Actimo currently fails with providing a really succinct syntax for such a use case. Perhaps more specialized DialogFeatures are in order?

# Specification of features #

It is currently not immediately apparent what Features are present in an Activity. A prototype was made to introduce annotations (i.e. @UseFeature with a class definition), but this has one huge downside - no ability to easily configure the Feature on initialization.

# Feature.getFeature() does not hinder dependence on another Feature's state #

As above - you can even call the state methods of other Features directly, which can lead to a huge mess. The getFeature() method, in the Feature interface, is intended for accessing Features that are stateless in nature - in effect, Activity-scope singletons. A way of limiting the functionality to this case would be nice.

Note that the following problem does not apply to Activity.getFeature().

# Grouping Features #

Something like a FeatureGroup would be nice to have.

# Menu management #

The menu methods need a revamp akin to the one with dialog methods - too much is exposed currently, which can lead to PITA bugs.