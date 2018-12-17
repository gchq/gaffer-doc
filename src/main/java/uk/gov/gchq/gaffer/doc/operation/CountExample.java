package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.impl.Count;
import uk.gov.gchq.gaffer.operation.impl.get.GetAllElements;

public class CountExample extends OperationExample {

    public static void main(final String[] args) {
        new CountExample().runAndPrint();
    }

    public CountExample() {
        super(Count.class, "Counts the number of items");
    }

    @Override
    protected void runExamples() {
        countAllElements();
    }

    private void countAllElements() {
        OperationChain countAllElements = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Count<>())
                .build();

        runExample(countAllElements, null);
    }
}
