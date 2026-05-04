import java.util.Objects;

// 🔷 ENUM: WeightUnit
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactorToKg;

    WeightUnit(double factor) {
        this.conversionFactorToKg = factor;
    }

    public double getConversionFactor() {
        return conversionFactorToKg;
    }

    // Convert to base unit (kg)
    public double convertToBaseUnit(double value) {
        return value * conversionFactorToKg;
    }

    // Convert from base unit (kg)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToKg;
    }
}

// 🔷 CLASS: QuantityWeight
class QuantityWeight {

    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // 🔹 Convert
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityWeight(convertedValue, targetUnit);
    }

    // 🔹 Add (default unit)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // 🔹 Add (custom unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    // 🔹 Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    // 🔹 HashCode
    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(baseValue / EPSILON));
    }

    // 🔹 ToString
    @Override
    public String toString() {
        return String.format("Quantity(%.5f, %s)", value, unit);
    }
}

// 🔷 MAIN CLASS
public class Main {
    public static void main(String[] args) {

        QuantityWeight q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight q2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight q3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        // 🔷 Equality
        System.out.println("1 kg == 1000 g: " + q1.equals(q2));
        System.out.println("1 kg == 2.20462 lb: " + q1.equals(q3));

        // 🔷 Conversion
        System.out.println("1 kg → gram: " + q1.convertTo(WeightUnit.GRAM));
        System.out.println("500 g → pound: " +
                new QuantityWeight(500, WeightUnit.GRAM).convertTo(WeightUnit.POUND));

        // 🔷 Addition
        System.out.println("1 kg + 1000 g: " + q1.add(q2));
        System.out.println("1 kg + 1000 g (in grams): " +
                q1.add(q2, WeightUnit.GRAM));

        System.out.println("2 kg + 4 lb (in kg): " +
                new QuantityWeight(2, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(4, WeightUnit.POUND), WeightUnit.KILOGRAM));
    }
}
