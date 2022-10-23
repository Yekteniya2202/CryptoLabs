import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class LFSRGenerator {
    private LinkedList<Integer> connectionVector;
    private LinkedList<Integer> registerVector;

    private int rounds = 64;

    public String makeIteration(String initVector, String initConnectionVector) {

        if (initVector.length() != initConnectionVector.length() + 1 || initVector.length() != 17)
            throw new RuntimeException("NON VALID INIT VECTOR AND CONNECTION VECTOR");

        connectionVector = new LinkedList<>(
                Arrays.stream(initConnectionVector.split(""))
                        .map(Integer::parseInt)
                        .toList()
        );

        registerVector = new LinkedList<>(
                Arrays.stream(initVector.split(""))
                        .map(Integer::parseInt)
                        .toList()
        );

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < rounds; i++) {
            System.out.println(this);
            int currentRegister = registerVector.getLast();
            ListIterator<Integer> connectionIterator = connectionVector.listIterator(connectionVector.size());
            ListIterator<Integer> registerIterator = registerVector.listIterator(registerVector.size() - 1);
            while (connectionIterator.hasPrevious() && registerIterator.hasPrevious()) {
                Integer previousConnection = connectionIterator.previous();
                Integer previousRegister = registerIterator.previous();
                if (previousConnection == 1) {
                    currentRegister ^= previousRegister;
                }
            }
            registerVector.addFirst(currentRegister);
            registerVector.removeLast();
            sb.append(currentRegister);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "LFSRGenerator{" + "connectionVector=" + connectionVector + ", registerVector=" + registerVector + '}';
    }

}
