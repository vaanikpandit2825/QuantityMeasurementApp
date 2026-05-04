public class QuantityMeasurementApp {

    // Standalone-style enum (kept in same file for convenience)
    enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        // Convert → base unit (feet)
        public double convertToBaseUnit(double value) {
            return value * toFeetFactor;
        }

        // Convert from base unit → this unit
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toFeetFactor;
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
            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Quantity(converted, targetUnit);
        }

        // Add with target unit (UC7 + UC8)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null)
                throw new IllegalArgumentException("Other cannot be null");
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double totalBase =
                    this.unit.convertToBaseUnit(this.value) +
                            other.unit.convertToBaseUnit(other.value);

            double result = targetUnit.convertFromBaseUnit(totalBase);
            return new Quantity(result, targetUnit);
        }

        // Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double thisBase = this.unit.convertToBaseUnit(this.value);
            double otherBase = other.unit.convertToBaseUnit(other.value);

            return Double.compare(thisBase, otherBase) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Main method (tests)
    public static void main(String[] args) {

        System.out.println("==== UC8 (Single File) ====\n");

        // Conversion
        System.out.println("Convert: " +
                new Quantity(1.0, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCH));

        // Addition
        System.out.println("Add: " +
                new Quantity(1.0, LengthUnit.FEET)
                        .add(new Quantity(12.0, LengthUnit.INCH), LengthUnit.FEET));

        // Equality
        System.out.println("Equals: " +
                new Quantity(36.0, LengthUnit.INCH)
                        .equals(new Quantity(1.0, LengthUnit.YARD)));

        // Cross-unit addition
        System.out.println("Yard + Feet: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));

        // CM conversion
        System.out.println("CM → Inch: " +
                new Quantity(2.54, LengthUnit.CENTIMETER)
                        .convertTo(LengthUnit.INCH));

        // Enum direct test
        System.out.println("Enum Test (Inch → Feet): " +
                LengthUnit.INCH.convertToBaseUnit(12.0));
    }
}