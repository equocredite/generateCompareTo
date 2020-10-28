## generateCompareTo
#### An IntelliJ IDEA plugin for automatically generating a compareTo method for classes
___
When inside a class context, adds a new `Generate -> compareTo()` action to the RMB menu that either generates a new `compareTo()` method or replaces an existing one.

![generate group](./screenshots/generate_group.png)

The order it induces is a lexicographic order based on all non-static fields of our class that are of comparable types, i.e. either primitive or which themselves implement the interface `Comparable`.

In the following dialog, you can customize the behaviour:
* Select the fields to use
* For each of them, in which order -- ascending or descending.
* Specify nullability for each field of a non-primitive type. The default is deduced from the presence or absence of `@NotNull`/`@NonNull` annotations. 
** For  nullable fields `null` will be considered the least value of that type. 

![dialog](./screenshots/dialog.png)
