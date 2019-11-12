package mapper;

import domain.Clerk;
import domain.DomainObject;
import domain.Manager;
import domain.Staff;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-09-13 23:25
 **/
public class StaffMapper extends DataMapper {

    private ClerkMapper clerkMapper = new ClerkMapper();
    private ManagerMapper managerMapper = new ManagerMapper();

    /**
     * insert a staff to database
     *
     * @param domainObject Manager or Clerk
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        boolean result = false;
        //if domain object is manager, insert to manager table
        if (domainObject instanceof Manager) {
            result = managerMapper.insert(domainObject);
        }
        //if domain object is clerk, insert to clerk table
        else if (domainObject instanceof Clerk) {
            result = clerkMapper.insert(domainObject);
        }
        //return false
        return result;
    }

    /**
     * delete a staff from database
     *
     * @param domainObject Staff
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        String id = domainObject.getId();
        //set manager id to id
        Manager manager = new Manager();
        manager.setStaffId(id);
        //set clerk id to id
        Clerk clerk = new Clerk();
        clerk.setStaffId(id);


        //delete from clerk table
        //return  false
        if (clerkMapper.delete(clerk)) {
            return true;
        }
        //delete from manager table
        else return managerMapper.delete(manager);
    }

    /**
     * update a staff in database
     *
     * @param domainObject Manager or Clerk
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        boolean result = false;
        //update in clerk table if it is clerk
        if (domainObject instanceof Manager) {
            result = managerMapper.update(domainObject);
        }
        // //update in manager mapper if it is manager
        else if (domainObject instanceof Clerk) {
            result = clerkMapper.update(domainObject);
        }
        return result;
    }

    /**
     * find a staff in data base
     *
     * @param domainObject Staff
     * @return a Staff object or null
     */
    public Staff findStaffById(DomainObject domainObject) {
        String id = domainObject.getId();
        Staff staff = null;
        Manager manager = new Manager();
        manager.setStaffId(id);
        Clerk clerk = new Clerk();
        clerk.setStaffId(id);
        //find in manager table
        staff = managerMapper.findManagerById(manager);
        if (staff != null) {
            return staff;
        }
        //find in clerk table
        staff = clerkMapper.findClerkById(clerk);
        return staff;
    }

    public Staff findStaffByName(Staff staff) {
        String username = staff.getStaffUName();
        Staff result = null;
        Manager manager = new Manager();
        manager.setStaffUName(username);
        ;
        Clerk clerk = new Clerk();
        clerk.setStaffUName(username);
        //find in manager table
        result = managerMapper.findManagerByName(manager);
        if (result != null) {
            return result;
        }
        //find in clerk table
        result = clerkMapper.findClerkByName(clerk);
        return result;
    }

}
