package daoimpl;

import dao.Orders_Dao;
import model.Orders;
import util.XJdbc;
import util.XQuery;

import java.util.List;

public class Orders_Daoimpl implements Orders_Dao {
    
    private String sqlCreate = "INSERT INTO Orders (UserId, Total, Status, Address) VALUES (?, ?, ?, ?)";
    private String sqlUpdate = "UPDATE Orders SET Status=? WHERE OrderId=?";
    private String sqlDelete = "DELETE FROM Orders WHERE OrderId=?";
    private String sqlFindById = "SELECT * FROM Orders WHERE OrderId=?";
    private String sqlFindAll = "SELECT * FROM Orders";
    private String sqlFindByUser = "SELECT * FROM Orders WHERE UserId=? ORDER BY OrderDate DESC";
    
    @Override
    public void create(Orders entity) {
        XJdbc.executeUpdate(sqlCreate, entity.getUserId(), entity.getTotal(), entity.getStatus(), entity.getAddress());
    }

    @Override
    public void update(Orders entity) {
        XJdbc.executeUpdate(sqlUpdate, entity.getStatus(), entity.getOrderId());
    }

    @Override
    public void deleteById(Integer integer) {
        XJdbc.executeUpdate(sqlDelete, integer);
    }

    @Override
    public List<Orders> findAll() {
        return XQuery.getBeanList(Orders.class, sqlFindAll);
    }

    @Override
    public Orders findById(Integer integer) {
        return XQuery.getSingleBean(Orders.class, sqlFindById, integer);
    }
    
    public List<Orders> findByUserId(int userId) {
        return XQuery.getBeanList(Orders.class, sqlFindByUser, userId);
    }
    
    public int getLastInsertId(int userId, long total, String address) {
        String sql = "SELECT TOP 1 OrderId FROM Orders WHERE UserId = ? AND Total = ? AND Address = ? ORDER BY OrderId DESC";
        Integer id = XJdbc.getValue(sql, userId, total, address);
        return id != null ? id : 0;
    }
}
