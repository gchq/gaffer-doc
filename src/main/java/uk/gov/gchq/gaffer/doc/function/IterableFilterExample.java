package uk.gov.gchq.gaffer.doc.function;

import com.google.common.collect.Lists;
import uk.gov.gchq.koryphe.impl.function.IterableFilter;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

public class IterableFilterExample extends FunctionExample {
    public static void main(final String[] args) {
        new IterableFunctionExample().runAndPrint();
    }

    public IterableFilterExample() {
        super(IterableFilter.class, "An IterableFilter is useful for applying a provided function, or functions, to each entry of a supplied Iterable.");
    }

    @Override
    protected void runExamples() {
        iterableFilter();
    }

    public void iterableFilter() {
        // ---------------------------------------------------------
        final IterableFilter<Integer> function = new IterableFilter<>(new IsMoreThan(5));
        // ---------------------------------------------------------

        runExample(function,
                null,
                Lists.newArrayList(1, 2, 3),
                Lists.newArrayList(5, 10, 15),
                Lists.newArrayList(7, 9, 11),
                Lists.newArrayList(1, null, 3),
                null);
    }

}
