class UseGetters implements Comparable<UseGetters> {
    private String foo;
    private int bar;

    public String getFoo() {
        return foo;
    }

    @Override
    public int compareTo(UseGetters that) {
        if (this.getFoo() == null && that.getFoo() == null) {
            // pass
        } else if (this.getFoo() == null) {
            return -1;
        } else if (that.getFoo() == null) {
            return 1;
        } else {
            int fooComparison = this.getFoo().compareTo(that.getFoo());
            if (fooComparison != 0) {
                return fooComparison < 0 ? -1 : 1;
            }
        }

        if (this.bar != that.bar) {
            return (this.bar < that.bar ? -1 : 1);
        }

        return 0;
    }
}