class NotNullString implements Comparable<NotNullString> {
    @NotNull
    private String foo;

    @Override
    public int compareTo(NotNullString that) {
        if (this.foo.compareTo(that.foo) != 0) {
            return (this.foo.compareTo(that.foo) < 0 ? -1 : 1);
        }

        return 0;
    }
}