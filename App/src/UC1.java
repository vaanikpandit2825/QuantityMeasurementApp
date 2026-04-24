public class QuantityMeasurementApp {

    // Inner class for Feet
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {

            // Same reference (Reflexive)
            if (this == obj) return true;

            // Null or different type
            if (obj == null || getClass() != obj.getClass()) return false;

            // Cast to Feet
            Feet other = (Feet) obj;

            // Compare values safely
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method (acts as test runner)
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("==== UC1: Feet Measurement Equality Tests ====\n");

        // Test 1: Same Value
        System.out.println("testEquality_SameValue: " + f1.equals(f2)); // true

        // Test 2: Different Value
        System.out.println("testEquality_DifferentValue: " + f1.equals(f3)); // false

        // Test 3: Null Comparison
        System.out.println("testEquality_NullComparison: " + f1.equals(null)); // false

        // Test 4: Same Reference
        System.out.println("testEquality_SameReference: " + f1.equals(f1)); // true

        // Test 5: Non-Numeric / Different Type
        System.out.println("testEquality_NonNumericInput: " + f1.equals("test")); // false

        // Example Output
        System.out.println("\nExample:");
        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + f1.equals(f2) + ")");
    }
}