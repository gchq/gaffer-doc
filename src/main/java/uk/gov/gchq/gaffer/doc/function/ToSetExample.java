package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.impl.function.ToSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ToSetExample extends FunctionExample {


    public static void main(final String[] args) {
        new ToListExample().runAndPrint();
    }

    public ToListExample() {
        super(ToSet.class, "Converts an Object to a Set");
    }

    @Override
    public void runExamples() {
        toList();
    }

    public void toList() {
        // ---------------------------------------------------------
        final ToSet function = new ToSet();
        // ---------------------------------------------------------


        String[] strArray = {"a", "b", "c"};
        runExample(function,
                null,
                "test", null, 30L, new TypeSubTypeValue("t", "st", "v"), strArray, Arrays.asList("test1", "test2")
        );
    }
}
