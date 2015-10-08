public class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;
    private int sleepTime;
    
    RunnableDemo(String name, int st) {
        threadName = name;
        System.out.println("Creating " + threadName);
        sleepTime = st;
    }
    
    public void run() {
        System.out.println("Running " + threadName);
        try {
            for (int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
        System.out.println("Thread " + threadName + " exiting.");
    }
    
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
