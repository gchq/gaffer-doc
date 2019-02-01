package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.koryphe.impl.function.MapFilter;
import uk.gov.gchq.koryphe.impl.predicate.AreEqual;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;
import uk.gov.gchq.koryphe.impl.predicate.StringContains;

import java.util.HashMap;

public class MapFilterExample extends FunctionExample {

    public static void main(final String[] args) {
        new MapFilterExample().runAndPrint();
    }

    public MapFilterExample() {
        super(MapFilter.class, "A Function which applies the given predicates to the keys and/or values");
    }

    @Override
    protected void runExamples() {
        filterOnKeys();
        filterOnValues();
        filterOnBoth();
    }

    public void filterOnKeys() {

        // ---------------------------------------------------------
        final MapFilter keyFilter = new MapFilter().keyPredicate(
                new StringContains("a")
        );
        // ---------------------------------------------------------

        HashMap<String, Long> map = new HashMap<>();
        map.put("cat", 3L);
        map.put("dog", 2L);
        map.put("giraffe", 0L);

        runExample(keyFilter, "MapFilter with key predicate", map);

    }

    public void filterOnValues() {

        // ---------------------------------------------------------
        final MapFilter valueFilter = new MapFilter().valuePredicate(
                new IsMoreThan(10)
        );
        // ---------------------------------------------------------

        HashMap<String, Integer> map = new HashMap<>();

        map.put("Pizza", 30);
        map.put("Steak", 12);
        map.put("Casserole", 4);

        runExample(valueFilter, "MapFilter with value predicate", map);

    }

    public void filterOnBoth() {

        // ---------------------------------------------------------
        final MapFilter keyValueFilter = new MapFilter()
                .keyValuePredicate(new AreEqual());
        // ---------------------------------------------------------

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(1, 2);
        map.put(3, 3);
        map.put(6, 4);

        runExample(keyValueFilter, "MapFilter with key-value Predicate", map);
    }


}
