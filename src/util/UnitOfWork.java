package util;

import domain.DomainObject;
import mapper.DataMapper;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import mapper.LockingMapper;
import org.junit.Assert;

public class UnitOfWork {
    private static ThreadLocal current = new ThreadLocal();

    private List<DomainObject> newObjects = new ArrayList<DomainObject>();
    private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>();
    private List<DomainObject> deleteObjects = new ArrayList<DomainObject>();

    /**
     * new current
     */
    public static void newCurrent() {
        setCurrent(new UnitOfWork());
    }

    /**
     * get current
     * @return unit of work
     */
    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }

    /**
     * set current
     * @param uow unit of work
     */
    public static void setCurrent(UnitOfWork uow) {
        current.set(uow);
    }

    /**
     * check if object exist in lists
     * @param domainObject domainObject
     * @return result
     */
    public boolean checkExist(DomainObject domainObject) {
        if (dirtyObjects.contains(domainObject))
            return true;
        if (deleteObjects.contains(domainObject))
            return true;
        if (newObjects.contains(domainObject)) {
            return true;
        }
        return false;
    }

    /**
     * Register new
     * @param obj domainObject
     */
    public void registerNew(DomainObject obj) {
        if(!checkExist(obj)) {
            newObjects.add(obj);
        }

    }

    /**
     * Register dirty
     * @param obj domainObject
     */
    public void registerDirty(DomainObject obj) {
        if(!checkExist(obj)) {
            dirtyObjects.add(obj);
        }
    }

    /**
     * Register delete
     * @param obj domainObject
     */
    public void registerDelete(DomainObject obj) {
        if(!checkExist(obj)) {
            deleteObjects.add(obj);
        }
    }

    public void registerClean(DomainObject obj) {
        Assert.assertNotNull("id is null", obj.getId());

    }

    /**
     * commit
     * @return result
     */
    public boolean commit() {
        boolean result;
        try {
            for (DomainObject obj : newObjects) {
                DataMapper dataMapper = DataMapper.getMapper(obj);
                LockingMapper lockingMapper = new LockingMapper(dataMapper);
                result =lockingMapper.insert(obj);

                    return result;

            }
            for (DomainObject obj : dirtyObjects) {
                DataMapper dataMapper = DataMapper.getMapper(obj);
                LockingMapper lockingMapper = new LockingMapper(dataMapper);
                result =lockingMapper.update(obj);

                    return result;

            }
            for (DomainObject obj : deleteObjects) {
                DataMapper dataMapper = DataMapper.getMapper(obj);
                LockingMapper lockingMapper = new LockingMapper(dataMapper);
                result =lockingMapper.delete(obj);

                    return result;

            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
