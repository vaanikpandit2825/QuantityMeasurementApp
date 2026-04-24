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

        // UC6: Add (result in first operand unit)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // UC7: Add with explicit target unit
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Other quantity cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double totalFeet =
                    this.unit.toFeet(this.value) +
                            other.unit.toFeet(other.value);

            double result = targetUnit.fromFeet(totalFeet);
            return new Quantity(result, targetUnit);
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

    // Main method (UC7 test cases)
    public static void main(String[] args) {

        System.out.println("==== UC7: Addition with Target Unit ====\n");

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        // Target = FEET
        System.out.println("Feet Target: " + q1.add(q2, LengthUnit.FEET)); // 2 ft

        // Target = INCH
        System.out.println("Inch Target: " + q1.add(q2, LengthUnit.INCH)); // 24 in

        // Target = YARD
        System.out.println("Yard Target: " + q1.add(q2, LengthUnit.YARD)); // ~0.667 yd

        // Target = CM
        System.out.println("CM Target: " +
                new Quantity(1.0, LengthUnit.INCH)
                        .add(new Quantity(1.0, LengthUnit.INCH), LengthUnit.CENTIMETER)); // ~5.08 cm

        // Cross examples
        System.out.println("Yard + Feet → Yard: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));

        System.out.println("Inch + Yard → Feet: " +
                new Quantity(36.0, LengthUnit.INCH)
                        .add(new Quantity(1.0, LengthUnit.YARD), LengthUnit.FEET));

        // Zero case
        System.out.println("Zero Case: " +
                new Quantity(5.0, LengthUnit.FEET)
                        .add(new Quantity(0.0, LengthUnit.INCH), LengthUnit.YARD));

        // Negative case
        System.out.println("Negative Case: " +
                new Quantity(5.0, LengthUnit.FEET)
                        .add(new Quantity(-2.0, LengthUnit.FEET), LengthUnit.INCH));

        // Commutativity
        Quantity a = q1.add(q2, LengthUnit.YARD);
        Quantity b = q2.add(q1, LengthUnit.YARD);

        System.out.println("Commutativity: " + a.equals(b)); // true

        // Error handling
        try {
            q1.add(q2, null);
        } catch (Exception e) {
            System.out.println("Null Target Test Passed: " + e.getMessage());
        }
    }
}