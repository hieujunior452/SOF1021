package model;

public class OrderDetails {
    private int OrderDetailId;
    private int OrderId;
    private int PhoneId;
    private int Quantity;
    private long Price;

    public OrderDetails() {}

    public int getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getPhoneId() {
        return PhoneId;
    }

    public void setPhoneId(int phoneId) {
        PhoneId = phoneId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }
}
