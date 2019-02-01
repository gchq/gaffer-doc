package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.impl.function.ToList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ToListExample extends FunctionExample {


    public static void main(final String[] args) {
        new ToListExample().runAndPrint();
    }

    public ToListExample() {
        super(ToList.class, "Converts an Object to a List");
    }

    @Override
    public void runExamples() {
        toList();
    }

    public void toList() {
        // ---------------------------------------------------------
        final ToList function = new ToList();
        // ---------------------------------------------------------


        String[] strArray = {"a", "b", "c"};
        Set<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        runExample(function,
                null,
                "test", null, 30L, new TypeSubTypeValue("t", "st", "v"), strArray, set
                );
    }
}
