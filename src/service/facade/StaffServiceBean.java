package service.facade;

import domain.Clerk;
import domain.Manager;
import domain.Staff;
import dto.StaffAssembler;
import dto.StaffDTO;
import service.StaffService;

import java.rmi.RemoteException;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-10 01:25
 **/
public class StaffServiceBean {
    public String findStaffById(String id) throws RemoteException{
        StaffService staffService = new StaffService();
        StaffDTO staffDTO = new StaffDTO();
        Staff manager = new Manager();
        manager.setStaffId(id);
        manager=staffService.findStaffById(manager);

        Staff clerk = new Clerk();
        clerk.setStaffId(id);
        clerk = staffService.findStaffById(clerk);
        if (manager!=null){
             staffDTO = StaffAssembler.createStaffDTO(manager);
        }else if (clerk != null){
             staffDTO = StaffAssembler.createStaffDTO(clerk);
        }
        return StaffDTO.serialize(staffDTO);
    }

    //remote call addCategory service
    public boolean addStaff(String id, String json){
        StaffDTO staffDTO = StaffDTO.deserialize(json);
        return StaffAssembler.createStaff(staffDTO);

    }
    //remote call deleteCategory service
    public boolean deleteCustomer(String id, String json){
        StaffDTO staffDTO = StaffDTO.deserialize(json);
        return StaffAssembler.deleteStaff(staffDTO);

    }
    //remote call updateCategory service
    public boolean updateCustomer(String id, String json){
        StaffDTO staffDTO = StaffDTO.deserialize(json);
        return StaffAssembler.updateStaff(staffDTO);
    }
}
