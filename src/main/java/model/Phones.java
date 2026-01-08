package model;

public class Phones {
    private int PhoneId;
    private String PhoneName;
    private long Price;
    private String Image;
    private String Description;
    private int BrandId;
    private int Stock;

    public Phones() {}

    public int getPhoneId() {
        return PhoneId;
    }

    public void setPhoneId(int phoneId) {
        PhoneId = phoneId;
    }

    public String getPhoneName() {
        return PhoneName;
    }

    public void setPhoneName(String phoneName) {
        PhoneName = phoneName;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }
}
