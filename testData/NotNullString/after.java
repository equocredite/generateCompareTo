class NotNullString implements Comparable<NotNullString> {
    @NotNull
    private String foo;

    @Override
    public int compareTo(NotNullString that) {
        return this.foo.compareTo(that.foo);
    }
}