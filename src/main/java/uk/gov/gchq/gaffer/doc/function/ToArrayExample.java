package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.impl.function.ToArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ToArrayExample extends FunctionExample {


    public static void main(final String[] args) {
        new ToArrayExample().runAndPrint();
    }

    public ToArrayExample() {
        super(ToArray.class, "Converts an Object to a List");
    }

    @Override
    public void runExamples() {
        toArray();
    }

    public void toArray() {
        // ---------------------------------------------------------
        final ToArray function = new ToArray();
        // ---------------------------------------------------------

        Set<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        runExample(function,
                null,
                "test", null, 30L, new TypeSubTypeValue("t", "st", "v"), Arrays.asList("a", "b", "c"), set
        );
    }
}
