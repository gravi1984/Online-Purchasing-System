package util;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-02 13:09
 **/
public class ReadWriteLock {

    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writeAccesses = 0;
    private int writeRequests = 0;
    private Thread writtingThread = null;

    synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)){
            wait();
        }
        readingThreads.put(callingThread,getReadAccessCount(callingThread)+1);
    }

    synchronized void lockWrite() throws InterruptedException{
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)){
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writtingThread = callingThread;
    }

    synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        if (!isReader(callingThread)){
            throw new IllegalMonitorStateException("Thread does not hold any lock");
        }
        int accessCount = getReadAccessCount(callingThread);
        if (accessCount == 1){
            readingThreads.remove(callingThread);
        }else {
            readingThreads.put(callingThread,accessCount-1);
        }
        notifyAll();
    }

    synchronized void unlockWrite() {
        Thread callingThread = Thread.currentThread();
        if (!isWriter(callingThread)){
            throw new IllegalMonitorStateException("Thread does not hold any lock");
        }
        writeAccesses--;
        if (writeAccesses ==0){
            writtingThread = null;
        }
        notifyAll();
    }

    synchronized void unlock(){
        Thread callingThread = Thread.currentThread();
        if (!(isReader(callingThread) || isWriter(callingThread))){
            throw new IllegalMonitorStateException("Thread does not hold any lock");
        }
        readingThreads.remove(callingThread);
        writtingThread = null;
        notifyAll();
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        if (isWriter(callingThread)) return true;
        if (hasWriter()) return false;
        if (isReader(callingThread)) return true;
            return !hasWriteRequests();
    }

    private boolean canGrantWriteAccess(Thread callingThread) {
        if (isOnlyReader(callingThread)) return true;
        if (hasReaders()) return false;
        if (writtingThread == null) return true;
        return isWriter(callingThread);
    }

    private int getReadAccessCount(Thread callingThread){
        Integer accessCount = readingThreads.get(callingThread);
        if (accessCount == null) return 0;
        return accessCount;
    }

    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    private boolean isReader(Thread callingThread) {
        return readingThreads.get(callingThread) != null;
    }

    private boolean isOnlyReader(Thread callingThread) {
        return readingThreads.size() == 1 && readingThreads.get(callingThread) != null;
    }

    private boolean hasWriter() {
        return writtingThread != null;
    }

    private boolean isWriter(Thread callingThread) {
        return writtingThread == callingThread;
    }

    private boolean hasWriteRequests() {
        return this.writeRequests > 0;

    }
}
