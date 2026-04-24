public class QuantityMeasurementApp {

    // Feet class
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double toInches() {
            return value * 12;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;

            // Compare with Feet
            if (obj instanceof Feet) {
                Feet other = (Feet) obj;
                return Double.compare(this.value, other.value) == 0;
            }

            // Compare with Inches
            if (obj instanceof Inches) {
                Inches other = (Inches) obj;
                return Double.compare(this.toInches(), other.value) == 0;
            }

            return false;
        }
    }

    // Inches class
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        public double toFeet() {
            return value / 12;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;

            // Compare with Inches
            if (obj instanceof Inches) {
                Inches other = (Inches) obj;
                return Double.compare(this.value, other.value) == 0;
            }

            // Compare with Feet
            if (obj instanceof Feet) {
                Feet other = (Feet) obj;
                return Double.compare(this.value, other.toInches()) == 0;
            }

            return false;
        }
    }

    // Static methods (as required)
    public static boolean checkFeetEquality(double a, double b) {
        return new Feet(a).equals(new Feet(b));
    }

    public static boolean checkInchesEquality(double a, double b) {
        return new Inches(a).equals(new Inches(b));
    }

    public static boolean checkFeetInchesEquality(double feet, double inches) {
        return new Feet(feet).equals(new Inches(inches));
    }

    // Main method (test cases)
    public static void main(String[] args) {

        System.out.println("==== UC2 Tests ====\n");

        // Same unit tests
        System.out.println("Feet Same Value: " + checkFeetEquality(1.0, 1.0)); // true
        System.out.println("Feet Different Value: " + checkFeetEquality(1.0, 2.0)); // false

        System.out.println("Inches Same Value: " + checkInchesEquality(1.0, 1.0)); // true
        System.out.println("Inches Different Value: " + checkInchesEquality(1.0, 2.0)); // false

        // Cross-unit test (IMPORTANT UC2 feature)
        System.out.println("Feet vs Inches (1 ft == 12 inch): " + checkFeetInchesEquality(1.0, 12.0)); // true
        System.out.println("Feet vs Inches (1 ft != 10 inch): " + checkFeetInchesEquality(1.0, 10.0)); // false

        // Edge cases
        Feet f = new Feet(1.0);
        System.out.println("Null Comparison: " + f.equals(null)); // false
        System.out.println("Non-Numeric Input: " + f.equals("test")); // false
        System.out.println("Same Reference: " + f.equals(f)); // true

        // Example output
        System.out.println("\nExample:");
        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + checkInchesEquality(1.0, 1.0) + ")");

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + checkFeetEquality(1.0, 1.0) + ")");
    }
}