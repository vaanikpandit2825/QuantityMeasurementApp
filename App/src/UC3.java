public class QuantityMeasurementApp {

    // Step 1: Enum for units
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Step 2: Generic Quantity Class
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

        // Convert to base unit (feet)
        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) return true;

            // Null or different type
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            // Compare after converting to feet
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // Main method (All test cases)
    public static void main(String[] args) {

        System.out.println("==== UC3: Generic Quantity Tests ====\n");

        // Same unit equality
        System.out.println("Feet Same Value: " +
                new Quantity(1.0, LengthUnit.FEET)
                        .equals(new Quantity(1.0, LengthUnit.FEET))); // true

        System.out.println("Inch Same Value: " +
                new Quantity(1.0, LengthUnit.INCH)
                        .equals(new Quantity(1.0, LengthUnit.INCH))); // true

        // Cross-unit equality
        System.out.println("Feet to Inches (1 ft == 12 inch): " +
                new Quantity(1.0, LengthUnit.FEET)
                        .equals(new Quantity(12.0, LengthUnit.INCH))); // true

        System.out.println("Inches to Feet (12 inch == 1 ft): " +
                new Quantity(12.0, LengthUnit.INCH)
                        .equals(new Quantity(1.0, LengthUnit.FEET))); // true

        // Different values
        System.out.println("Feet Different Value: " +
                new Quantity(1.0, LengthUnit.FEET)
                        .equals(new Quantity(2.0, LengthUnit.FEET))); // false

        System.out.println("Inch Different Value: " +
                new Quantity(1.0, LengthUnit.INCH)
                        .equals(new Quantity(2.0, LengthUnit.INCH))); // false

        // Null comparison
        Quantity q = new Quantity(1.0, LengthUnit.FEET);
        System.out.println("Null Comparison: " + q.equals(null)); // false

        // Same reference
        System.out.println("Same Reference: " + q.equals(q)); // true

        // Invalid unit test
        try {
            new Quantity(1.0, null);
        } catch (Exception e) {
            System.out.println("Invalid Unit Test Passed: " + e.getMessage());
        }

        // Example output
        System.out.println("\nExample:");
        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: Equal (" +
                new Quantity(1.0, LengthUnit.FEET)
                        .equals(new Quantity(12.0, LengthUnit.INCH)) + ")");
    }
}