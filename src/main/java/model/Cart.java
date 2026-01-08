package model;

public class Cart {
    private int CartId;
    private int UserId;
    private int PhoneId;
    private int Quantity;

    public Cart() {}

    public int getCartId() {
        return CartId;
    }

    public void setCartId(int cartId) {
        CartId = cartId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
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
}
