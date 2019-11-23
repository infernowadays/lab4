import container.LockFreeSet;

public class WorkerThreads implements Runnable {
    private LockFreeSet<String> lockFreeSet = new LockFreeSet<>();

    WorkerThreads(LockFreeSet<String> lockFreeSet){
        this.lockFreeSet =  lockFreeSet;
    }


    @Override
    public void run() {
    }
}
