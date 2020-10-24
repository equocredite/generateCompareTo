## generateCompareTo
#### An IntelliJ IDEA plugin for automatically generating a compareTo method for classes
___
When inside a class context, adds a new `Generate -> compareTo()` action to the RMB menu that either generates a new `compareTo()` method or replaces an existing one.

![generate group](./screenshots/generate_group.png)

The order it induces is a lexicographic order based on all non-static fields of our class that are of comparable types, i.e. either primitive or which themselves implement the interface `Comparable`.

In the following dialog, you can customize the behaviour:
* Select the fields that you want to be used
* For each of them, in which order -- ascending or descending.
* Additionally, for each field of a non-primitive type, you can choose whether it's nullable -- in that case, `null` will be considered the least value of that type. The default behaviour is deduced from the presence or absence of `@NotNull`/`@NonNull` annotations.

![dialog](./screenshots/dialog.png)
