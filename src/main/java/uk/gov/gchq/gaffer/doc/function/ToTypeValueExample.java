package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.types.TypeValue;
import uk.gov.gchq.gaffer.types.function.ToTypeValue;

import java.util.function.Function;

public class ToTypeValueExample extends FunctionExample {

    public ToTypeValueExample() {
        super(ToTypeValue.class, "Converts a value into a TypeValue");
    }

    public static void main(final String[] args) {
        new ToTypeValueExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        toTypeValue();
    }

    private void toTypeValue() {
        Function ToTypeValue = new ToTypeValue();

        super.runExample(
                ToTypeValue,
                null,
                "aString", 100L, 25, new TypeValue("type1", "value1"), null
        );
    }
}
