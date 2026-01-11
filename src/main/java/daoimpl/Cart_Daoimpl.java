package daoimpl;

import dao.Cart_Dao;
import model.Cart;
import util.XJdbc;
import util.XQuery;

import java.util.List;

public class Cart_Daoimpl implements Cart_Dao {

    private String sqlCreate = "INSERT INTO Cart (UserId, PhoneId, Quantity) VALUES (?, ?, ?)";
    private String sqlUpdate = "UPDATE Cart SET Quantity=? WHERE CartId=?";
    private String sqlDelete = "DELETE FROM Cart WHERE CartId=?";
    private String sqlFindById = "SELECT * FROM Cart WHERE CartId=?";
    private String sqlFindAll = "SELECT * FROM Cart";
    private String sqlFindByUser = "SELECT * FROM Cart WHERE UserId=?";
    private String sqlFindByUserAndPhone = "SELECT * FROM Cart WHERE UserId=? AND PhoneId=?";

    @Override
    public void create(Cart entity) {
        XJdbc.executeUpdate(sqlCreate, entity.getUserId(), entity.getPhoneId(), entity.getQuantity());
    }

    @Override
    public void update(Cart entity) {
        XJdbc.executeUpdate(sqlUpdate, entity.getQuantity(), entity.getCartId());
    }

    @Override
    public void deleteById(Integer integer) {
        XJdbc.executeUpdate(sqlDelete, integer);
    }

    @Override
    public List<Cart> findAll() {
        return XQuery.getBeanList(Cart.class, sqlFindAll);
    }

    @Override
    public Cart findById(Integer integer) {
        return XQuery.getSingleBean(Cart.class, sqlFindById, integer);
    }
    
    public List<Cart> findByUserId(int userId) {
        return XQuery.getBeanList(Cart.class, sqlFindByUser, userId);
    }
    
    public Cart findByUserAndPhone(int userId, int phoneId) {
        return XQuery.getSingleBean(Cart.class, sqlFindByUserAndPhone, userId, phoneId);
    }
}
