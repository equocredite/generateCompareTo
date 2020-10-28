## generateCompareTo
#### An IntelliJ IDEA plugin for automatically generating a compareTo method for classes
Based on [a plugin](https://github.com/jhartman/generateCompareTo) by Joshua Hartman and improved.
___
Adds a new `Generate -> compareTo()` action to the class context menu that either generates a new `compareTo()` method or replaces an existing one.

![generate group](./screenshots/generate_group.png)

The order it induces is a lexicographic order based on all non-static fields of our class that are of comparable types, i.e. either primitive or which themselves implement the interface `Comparable`. For  nullable fields `null` will be treated as the least value of that type. 

In the following dialog, you can customize the behaviour:
* Select the fields to use
* For each of them, in which order -- ascending or descending.
* Specify nullability for each field of a non-primitive type. The default is deduced from the presence or absence of `@NotNull`/`@NonNull` annotations. 
![dialog_](./screenshots/dialog_.png)
