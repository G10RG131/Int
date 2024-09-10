import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {

    private final List<T> items = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public void put(T item) {
        lock.lock();
        try {
            items.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        lock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }
            return items.remove(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
