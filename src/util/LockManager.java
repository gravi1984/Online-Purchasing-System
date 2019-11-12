package util;

import java.util.HashMap;
import java.util.Map;
import util.ReadWriteLock;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-02 13:04
 **/
public class LockManager {
    private final Map<Object, ReadWriteLock> lockMap;

    private static LockManager lockManager;

    private LockManager(){
        lockMap = new HashMap<>();
    }

    public synchronized static LockManager getInstance(){
        if (LockManager.lockManager == null){
            LockManager.lockManager = new LockManager();
        }
        return LockManager.lockManager;
    }

    private ReadWriteLock getReadWriteLock(Object toLock){
        ReadWriteLock lock = lockMap.get(toLock);
        if (lock == null){
            lockMap.putIfAbsent(toLock,new ReadWriteLock());
            lock = lockMap.get(toLock);
        }
        return lock;
    }

    public synchronized void acquireReadLock(Object toLock) throws InterruptedException{
        getReadWriteLock(toLock).lockRead();
    }

    public synchronized void acquireWriteLock(Object toLock) throws InterruptedException{
        getReadWriteLock(toLock).lockWrite();
    }

    public synchronized void releaseReadLock(Object toLock){
        getReadWriteLock(toLock).unlockRead();
    }
    public synchronized void releaseWriteLock(Object toLock){
        getReadWriteLock(toLock).unlockWrite();
    }

    public synchronized void releaseAllLocks() throws InterruptedException{
        for (Map.Entry<Object, ReadWriteLock> entry: lockMap.entrySet()){
            entry.getValue().unlock();
        }
    }
}
