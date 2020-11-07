class MyClass implements Comparable<MyClass> {
    @Override
    public int compareTo(MyClass that) {
        if (this.comparableObject == null && that.comparableObject == null) {
            return 0;
        } else if (this.comparableObject == null) {
            return -1;
        } else if (that.comparableObject == null) {
            return 1;
        } else {
            return this.comparableObject.compareTo(that.comparableObject);
        }
    }

    private static class NonComparableClass {}
    private static class ComparableClass implements Comparable<ComparableClass> {
        private final int x;

        public ComparableClass(int x) {
            this.x = x;
        }

        public compareTo(ComparableClass that) {
            return x < that.x;
        }
    }

    NonComparableClass nonComparableObject;
    ComparableClass comparableObject;
}