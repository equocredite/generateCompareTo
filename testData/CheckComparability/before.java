class MyClass {
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
    ComparableClass comparableObject;<caret>
}