public class QuantityMeasurementApp {

    // Step 1: Extended Enum (Feet as base unit)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084); // 1 cm ≈ 0.0328084 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Generic Quantity Class (same as UC3)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
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
    }

    // Main method (All UC4 test cases)
    public static void main(String[] args) {

        System.out.println("==== UC4: Extended Unit Support Tests ====\n");

        // Yard tests
        System.out.println("Yard to Yard Same: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(1.0, LengthUnit.YARD))); // true

        System.out.println("Yard to Feet (1 yd = 3 ft): " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET))); // true

        System.out.println("Yard to Inches (1 yd = 36 in): " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(36.0, LengthUnit.INCH))); // true

        System.out.println("Yard Different Value: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(2.0, LengthUnit.YARD))); // false

        // Centimeter tests
        System.out.println("CM to CM Same: " +
                new Quantity(2.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(2.0, LengthUnit.CENTIMETER))); // true

        System.out.println("CM to Inches (1 cm ≈ 0.393701 in): " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(0.393701, LengthUnit.INCH))); // true

        System.out.println("CM to Feet Non-Equal: " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(1.0, LengthUnit.FEET))); // false

        // Transitive property
        boolean a = new Quantity(1.0, LengthUnit.YARD)
                .equals(new Quantity(3.0, LengthUnit.FEET));

        boolean b = new Quantity(3.0, LengthUnit.FEET)
                .equals(new Quantity(36.0, LengthUnit.INCH));

        boolean c = new Quantity(1.0, LengthUnit.YARD)
                .equals(new Quantity(36.0, LengthUnit.INCH));

        System.out.println("Transitive Property (Yard→Feet→Inch): " + (a && b && c)); // true

        // Edge cases
        Quantity q = new Quantity(1.0, LengthUnit.YARD);
        System.out.println("Null Comparison: " + q.equals(null)); // false
        System.out.println("Same Reference: " + q.equals(q)); // true

        try {
            new Quantity(1.0, null);
        } catch (Exception e) {
            System.out.println("Invalid Unit Test Passed: " + e.getMessage());
        }

        // Example output
        System.out.println("\nExample:");
        System.out.println("Input: Quantity(1.0, YARD) and Quantity(3.0, FEET)");
        System.out.println("Output: Equal (" +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET)) + ")");
    }
}