class NullableString implements Comparable<NullableString> {
    private String foo;

    @Override
    public int compareTo(NullableString that) {
        if (this.foo == null && that.foo == null) {
            return 0;
        } else if (this.foo == null) {
            return -1;
        } else if (that.foo == null) {
            return 1;
        } else {
            return this.foo.compareTo(that.foo);
        }
    }
}