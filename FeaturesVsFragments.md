# An analogy #

Picture the following situation: there's a person who has to perform some task. She is named Activity.

In this analogy, **[Fragments](http://developer.android.com/guide/topics/fundamentals/fragments.html)** are her subordinates - people who follow her orders to complete this task.

**Features** are her hands, legs, heart, kidneys, etc.

# Comparison #

This (somewhat tongue-in-cheek) analogy demonstrates the similarities and differences between the two frameworks, these being:
  * You can essentially use any of them to implement your functionality (an extra hand is still an extra hand, whether figuratively or literally).
  * Using one does not preclude you from using the other.
  * Features do not exist as independent entities outside of an Activity, Fragments do.
  * Features have a more comprehensive and direct access to the Activity's internals (more method hooks).
  * Fragments are generally better-suited for UI abstraction, due to their back stack integration, ability to define in XML, etc..
  * Features are more lightweight in communicating with the Activity or other Features, Fragments require additional infrastructure.

Overall, you have to decide for yourself whether you need Features, Fragments, or a mixture of both.