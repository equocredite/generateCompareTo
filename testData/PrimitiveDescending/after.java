class PrimitiveDescending implements Comparable<PrimitiveDescending> {
    private int x;
    private double d;

    @Override
    public int compareTo(PrimitiveDescending that) {
        if (this.d != that.d) {
            return (this.d > that.d ? -1 : 1);
        }

        if (this.x != that.x) {
            return (this.x > that.x ? -1 : 1);
        }

        return 0;
    }
}