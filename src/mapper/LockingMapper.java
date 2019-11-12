package mapper;

import domain.DomainObject;
import security.AppSession;
import util.LockManager;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-07 18:05
 **/
public class LockingMapper extends DataMapper {
    private DataMapper impl;
    private LockManager lm;
//    private String sessionId;


    public LockingMapper(DataMapper impl) {
        this.impl = impl;
        this.lm = LockManager.getInstance();
//        this.sessionId = AppSession.getSessionId();
    }

    /**
     * insert function don't need to apply for the lock
     * @return
     */
    public boolean insert(DomainObject obj) {
            try {
                lm.acquireWriteLock(obj);
                impl.insert(obj);
                lm.releaseWriteLock(obj);
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return false;
    }

    /**
     * before update, apply for the lock
     */
    public boolean update(DomainObject obj) {
        try {
            lm.acquireWriteLock(obj);
            impl.update(obj);
            lm.releaseWriteLock(obj);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * before delete, apply for the lock
     */
    public boolean delete (DomainObject obj) {
        try {
            lm.acquireWriteLock(obj);
            impl.delete(obj);
            lm.releaseWriteLock(obj);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public DomainObject find (DomainObject obj) {
//        try {
//            lm.acquireReadLock(obj);
//            return impl.find
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }

}
