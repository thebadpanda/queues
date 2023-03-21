import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        int k = args.length > 0 ? Integer.parseInt(args[0]) : 5;

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // File file = new File("distinct.txt");
        // try (Scanner scanner = new Scanner(file).useDelimiter("\\s")) {
        //     while (scanner.hasNext()) {
        //         String data = scanner.next();
        //         queue.enqueue(data);
        //         // System.out.println(data);
        //     }
        // }
        // catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        if (k > queue.size()) k = queue.size();

        for (int i = 0; i < k; i++) {
            String randomItem = queue.dequeue();
            System.out.println(randomItem);
        }
    }
}
