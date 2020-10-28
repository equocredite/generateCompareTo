class MyClass implements Comparable<MyClass> {
    @Override
    public int compareTo(MyClass that) {
        if (this.comparableObject == null && that.comparableObject == null) {
            // pass
        } else if (this.comparableObject == null && that.comparableObject != null) {
            return -1;
        } else if (this.comparableObject != null && that.comparableObject == null) {
            return 1;
        } else if (this.comparableObject.compareTo(that.comparableObject) != 0) {
            return (this.comparableObject.compareTo(that.comparableObject) < 0 ? -1 : 1);
        }

        return 0;
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