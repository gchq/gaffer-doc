package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.types.TypeValue;
import uk.gov.gchq.gaffer.types.function.ToFreqMap;

import java.util.function.Function;

public class ToFreqMapExample extends FunctionExample {

    public ToFreqMapExample() {
        super(ToFreqMap.class, "Creates a new FreqMap and upserts a given value");
    }

    public static void main(final String[] args) {
        new ToFreqMapExample().runAndPrint();
    }


    @Override
    protected void runExamples() {
        toFreqMap();
    }

    private void toFreqMap() {
        Function toFreqMap = new ToFreqMap();
        runExample(
                toFreqMap,
                null,
                "aString", 100L, 20, new TypeValue("type1", "value1"), null
        );
    }
}
