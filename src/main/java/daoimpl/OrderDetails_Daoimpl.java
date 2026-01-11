package daoimpl;

import dao.OderDetails;
import model.OrderDetails;
import util.XJdbc;
import util.XQuery;

import java.util.List;

public class OrderDetails_Daoimpl implements OderDetails {

    private String sqlCreate = "INSERT INTO OrderDetails (OrderId, PhoneId, Quantity, Price) VALUES (?, ?, ?, ?)";
    private String sqlUpdate = "UPDATE OrderDetails SET Quantity=?, Price=? WHERE OrderDetailId=?";
    private String sqlDelete = "DELETE FROM OrderDetails WHERE OrderDetailId=?";
    private String sqlFindById = "SELECT * FROM OrderDetails WHERE OrderDetailId=?";
    private String sqlFindAll = "SELECT * FROM OrderDetails";
    private String sqlFindByOrderId = "SELECT * FROM OrderDetails WHERE OrderId=?";

    @Override
    public void create(OrderDetails entity) {
        XJdbc.executeUpdate(sqlCreate, entity.getOrderId(), entity.getPhoneId(), entity.getQuantity(), entity.getPrice());
    }

    @Override
    public void update(OrderDetails entity) {
        XJdbc.executeUpdate(sqlUpdate, entity.getQuantity(), entity.getPrice(), entity.getOrderDetailId());
    }

    @Override
    public void deleteById(Integer integer) {
        XJdbc.executeUpdate(sqlDelete, integer);
    }

    @Override
    public List<OrderDetails> findAll() {
        return XQuery.getBeanList(OrderDetails.class, sqlFindAll);
    }

    @Override
    public OrderDetails findById(Integer integer) {
        return XQuery.getSingleBean(OrderDetails.class, sqlFindById, integer);
    }
    
    public List<OrderDetails> findByOrderId(int orderId) {
        return XQuery.getBeanList(OrderDetails.class, sqlFindByOrderId, orderId);
    }
}
