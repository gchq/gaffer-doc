package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.types.TypeValue;
import uk.gov.gchq.gaffer.types.function.ToTypeSubTypeValue;

import java.util.function.Function;

public class ToTypeSubTypeValueExample extends FunctionExample {

    public ToTypeSubTypeValueExample() {
        super(ToTypeSubTypeValue.class, "Converts a value into a TypeSubTypeValue");
    }

    public static void main(final String[] args) {
        new ToTypeSubTypeValueExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        toTypeSubTypeValue();
    }

    private void toTypeSubTypeValue() {
        Function toTypeSubTypeValue = new ToTypeSubTypeValue();

        super.runExample(
                toTypeSubTypeValue,
                null,
                "aString", 100L, 25, new TypeValue("type1", "value1"), null
        );
    }
}
