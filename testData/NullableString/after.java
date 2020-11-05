class NullableString implements Comparable<NullableString> {
    private String foo;

    @Override
    public int compareTo(NullableString that) {
        if (this.foo == null && that.foo == null) {
            // pass
        } else if (this.foo == null) {
            return -1;
        } else if (that.foo == null) {
            return 1;
        } else if (this.foo.compareTo(that.foo) != 0) {
            return (this.foo.compareTo(that.foo) < 0 ? -1 : 1);
        }

        return 0;
    }
}