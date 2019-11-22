import container.LockFreeSet;

public class Main {
    public static void main(String[] args) {
        LockFreeSet<String> lockFreeSet = new LockFreeSet();
        System.out.println(lockFreeSet.isEmpty());

        lockFreeSet.add("25th");
        System.out.println(lockFreeSet.isEmpty());
        System.out.println(lockFreeSet.contains("efsd"));

        lockFreeSet.remove("25th");
        System.out.println(lockFreeSet.contains("25th"));
        System.out.println(lockFreeSet.isEmpty());
    }
}
