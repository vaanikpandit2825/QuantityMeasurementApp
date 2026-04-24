public class QuantityMeasurementApp {

    // Enum with conversion factors (base = FEET)
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
                throw new IllegalArgumentException("Invalid numeric value");

            this.value = value;
            this.unit = unit;
        }

        // Convert to another unit (instance method)
        public Quantity convertTo(LengthUnit targetUnit) {
            double feetValue = unit.toFeet(value);
            double converted = targetUnit.fromFeet(feetValue);
            return new Quantity(converted, targetUnit);
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // Equality check
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        // Better print
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Static conversion API (as required)
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        double feetValue = source.toFeet(value);
        return target.fromFeet(feetValue);
    }

    // Demo methods (API style)
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") = " + result);
    }

    public static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity result = q.convertTo(to);
        System.out.println(q + " -> " + result);
    }

    public static void demonstrateLengthEquality(Quantity q1, Quantity q2) {
        System.out.println(q1 + " == " + q2 + " ? " + q1.equals(q2));
    }

    // Main method (All test cases)
    public static void main(String[] args) {

        System.out.println("==== UC5: Unit Conversion Tests ====\n");

        // Basic conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH); // 12
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET); // 9
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD); // 1
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH); // ~0.393701

        // Zero & negative
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(-1.0, LengthUnit.FEET, LengthUnit.INCH);

        // Instance conversion
        Quantity q = new Quantity(1.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.INCH);

        // Equality check
        demonstrateLengthEquality(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(12.0, LengthUnit.INCH)
        );

        // Round-trip test
        double original = 5.0;
        double converted = convert(original, LengthUnit.FEET, LengthUnit.INCH);
        double back = convert(converted, LengthUnit.INCH, LengthUnit.FEET);
        System.out.println("Round-trip: " + original + " -> " + back);

        // Error handling
        try {
            convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH);
        } catch (Exception e) {
            System.out.println("NaN Test Passed: " + e.getMessage());
        }

        try {
            convert(1.0, null, LengthUnit.INCH);
        } catch (Exception e) {
            System.out.println("Null Unit Test Passed: " + e.getMessage());
        }
    }
}