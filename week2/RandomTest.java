public class RandomTest {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.print(StdRandom.uniform(5) + " ");
        }
        System.out.println();
    }
}