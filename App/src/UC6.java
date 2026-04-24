public class QuantityMeasurementApp {

    // Enum (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        // Convert to another unit
        public Quantity convertTo(LengthUnit targetUnit) {
            double feet = unit.toFeet(value);
            double converted = targetUnit.fromFeet(feet);
            return new Quantity(converted, targetUnit);
        }

        // Add another quantity (returns result in THIS unit)
        public Quantity add(Quantity other) {
            if (other == null)
                throw new IllegalArgumentException("Other quantity cannot be null");

            double totalFeet = this.unit.toFeet(this.value)
                    + other.unit.toFeet(other.value);

            double result = this.unit.fromFeet(totalFeet);
            return new Quantity(result, this.unit);
        }

        // Static add (optional API)
        public static Quantity add(Quantity q1, Quantity q2) {
            return q1.add(q2); // result in q1's unit
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Main method (UC6 test cases)
    public static void main(String[] args) {

        System.out.println("==== UC6: Addition Tests ====\n");

        // Same unit
        System.out.println("Feet + Feet: " +
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(2.0, LengthUnit.FEET))); // 3 ft

        System.out.println("Inch + Inch: " +
                new Quantity(6.0, LengthUnit.INCH)
                        .add(new Quantity(6.0, LengthUnit.INCH))); // 12 in

        // Cross unit
        System.out.println("Feet + Inches: " +
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH))); // 2 ft

        System.out.println("Inches + Feet: " +
                new Quantity(12.0, LengthUnit.INCH)
                        .add(new Quantity(1.0, LengthUnit.FEET))); // 24 in

        System.out.println("Yard + Feet: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET))); // 2 yd

        System.out.println("CM + Inch: " +
                new Quantity(2.54, LengthUnit.CENTIMETER)
                        .add(new Quantity(1.0, LengthUnit.INCH))); // ~5.08 cm

        // Identity (zero)
        System.out.println("With Zero: " +
                new Quantity(5.0, LengthUnit.FEET)
                        .add(new Quantity(0.0, LengthUnit.INCH)));

        // Negative values
        System.out.println("Negative: " +
                new Quantity(5.0, LengthUnit.FEET)
                        .add(new Quantity(-2.0, LengthUnit.FEET)));

        // Commutativity check
        Quantity a = new Quantity(1.0, LengthUnit.FEET)
                .add(new Quantity(12.0, LengthUnit.INCH));

        Quantity b = new Quantity(12.0, LengthUnit.INCH)
                .add(new Quantity(1.0, LengthUnit.FEET));

        System.out.println("Commutativity: " + a.equals(b)); // true

        // Edge cases
        try {
            new Quantity(1.0, LengthUnit.FEET).add(null);
        } catch (Exception e) {
            System.out.println("Null Test Passed: " + e.getMessage());
        }

        // Large values
        System.out.println("Large Values: " +
                new Quantity(1e6, LengthUnit.FEET)
                        .add(new Quantity(1e6, LengthUnit.FEET)));

        // Small values
        System.out.println("Small Values: " +
                new Quantity(0.001, LengthUnit.FEET)
                        .add(new Quantity(0.002, LengthUnit.FEET)));
    }
}