class ConstantDemand implements Demand {
    final String type;

    ConstantDemand(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstantDemand)) return false;

        ConstantDemand that = (ConstantDemand) o;

        return type.equals(that.type);
    }

    @Override
    public String toString() {
        return "ConstantDemand{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
