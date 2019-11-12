package util;

import domain.*;
import mapper.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Assert;
import service.CategoryService;
import service.StaffService;
import service.facade.OrderServiceBean;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-09-02 21:25
 **/
public class test {
    public static void main(String args[]) {
        try {
//        Date date = new Date();
//        Customer user = new Customer("firstname","lastname","username","password",date,"email",
//            "address");
//        CustomerMapper userMapper = new CustomerMapper();
//        userMapper.insert(user);
//        Cart cart = new Cart(user);
//        new CartService().newCart(cart);
//            Customer customer = new Customer();
//            customer.setUserId("cb63a8da-dec7-4551-a99f-e89cbb64d593");
//            customer = new CustomerService().findUserById(customer);
//            System.out.println(customer);
//            Admin admin = new Admin("username0", "email0", "first1", "last1", "pw1", 1);
//            AdminMapper adminMapper = new AdminMapper();
//            adminMapper.insert(admin);
//            adminMapper.findAdminById(admin);

//            Product product = new Product("product2","info2",20.0,225,1);
//            ProductMapper productMapper = new ProductMapper();
//            productMapper.insert(product);

//            Customer user = new Customer("firstname","lastname","username","password",new Date(),
//                    "email1", "address1");
//            CustomerService userService = new CustomerService();
//            user.setUsername("username");
//            userService.findUserByName(user);
//            userService.insertUser(user);

//            System.out.println(DataMapper.getMapper(user));
//            Customer user =new Customer();
//            user.setUsername("username");
//            user = userService.findUserByName(user);
//            Cart cart = new Cart(user);
//            CartMapper cartMapper = new CartMapper();
//            cartMapper.insert(cart);
//            Cart cart = new Cart(user);

//            System.out.println(user.getId());
//            CartService cartService = new CartService();
//            cartService.insertCart(cart);
//            List<CartDetail> carts = cartService.findCartByUser(user);
//            System.out.println(carts);

//            Category category =new Category();
//            category.setCategoryId("d40a490e-c242-441a-8a44-e1b4550c15ca");
//String username = "manager";
//String password = "password";
//            ByteSource salt = ByteSource.Util.bytes(username);
//            String encryptedPassword = new SimpleHash("MD5", password, salt, 1024).toHex();
//            Manager manager = new Manager(username,encryptedPassword,"manager@email.com");
//            new StaffService().insert(manager);
//            CategoryService categoryService = new CategoryService();
//            System.out.println(categoryService.findCategroyById(category));

//            Manager manager = new Manager("manager", "password", "email");
//            Clerk clerk = new Clerk("clerk", "password1", "firstname1", "lastname1", new Date(),
//                    new Date());
//            CartDetail cartDetail = new CartDetail();
//            cartDetail.setCartDetailId("156eb13a-5c59-4a94-b885-8405551c5ae1");
//            CartService cartService = new CartService();
//            boolean result = cartService.deleteCartDetail(cartDetail);
//            Manager manager = new Manager();
//            manager.setStaffId("41139949-afe6-4e43-bc1d-b1e4b9d41735");
//            Clerk clerk = new Clerk();
//            clerk.setStaffId("6a4d9526-0f05-4cbd-950d-e7baa2e785bf");
//            StaffMapper staffMapper = new StaffMapper();
//            Staff staff = new Staff();
//            staff.setStaffId("b8026488-74c7-460e-bac1-de7de1b3e7fb");

//            Staff staff1 = staffMapper.findStaffById(manager);
//            Staff staff2 = staffMapper.findStaffById(clerk);
//            System.out.println(staff2.getStaffUName());
//            staff2.setStaffUName("clerk1");
//            staffMapper.update(staff2);
//            staff1.setStaffUName("manager1");
//            staff2.setStaffUName("clerk");
//            staffMapper.delete(staff1);
//            staffMapper.delete(staff2);
//            System.out.println(staff1);
//            System.out.println(staff2);
//            staffMapper.insert(manager);
//            staffMapper.insert(clerk);
            System.out.println(new OrderServiceBean().findOrderById("d147ea0b-cb03-420a-b6d6-52058b4a8a3e"));
//            Category category = new Category("Medium Roast");
//            System.out.println(new CategoryService().newCategory(category));
//            System.out.println(staffMapper.delete(staff));
//            System.out.println(productMapper.getAllCategories().get(0).getProductName());
//            ProductService productService = new ProductService();
//            Product product = productService.findProductByName("product2").get(0);

//            CartService cartService = new CartService();
//            cartService.AddToCart(user,product,1);
//            System.out.println(productService.getAll().get(0).getProductName());
//        AdminMapper adminMapper1 = new AdminMapper();
//        List<Admin> result = adminMapper1.findUserById(2);
//        System.out.println(result.get(0).getAdminLname());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull("id is null", "97cbc120-e429-485c-8a92-36de1d810dc7");
    }
}
