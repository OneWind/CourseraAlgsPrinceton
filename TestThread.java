public class TestThread {
    public static void main(String args[]) {
        RunnableDemo R1 = new RunnableDemo("Thread-1", 50);
        R1.start();
        
        RunnableDemo R2 = new RunnableDemo("Thread-2", 10);
        R2.start();
    }
}