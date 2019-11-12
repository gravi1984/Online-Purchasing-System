package mapper;

import domain.DomainObject;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: CoffeeWeb
 * @description: Data Mapper
 * @author: DennyLee
 * @create: 2019-09-02 00:25
 **/
public abstract class DataMapper {
    //insert a domain object
    public abstract boolean insert(DomainObject domainObject);
    //delet a domain object
    public abstract boolean delete(DomainObject domainObject);
    //update a domain object
    public abstract boolean update(DomainObject domainObject);

    //get and create mapper instance of a object
    public static DataMapper getMapper(Object obj) {
        DataMapper dm = null;
        try {
            //change to mapper.DataMapper format and create new instance by name
            dm = (DataMapper) Class.forName("mapper." + obj.getClass().getSimpleName() + "Mapper").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dm;


    }

}
