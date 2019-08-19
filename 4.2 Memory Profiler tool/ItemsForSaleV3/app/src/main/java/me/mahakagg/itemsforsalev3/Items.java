package me.mahakagg.itemsforsalev3;

// helper class for the items in the list
public class Items {
    // name of the item
    private String itemName;
    // price of the item
    private int itemPrice;
    // thumbnail image
    private final int imageThumbnail;
    // large image to be displayed in dialog box
    private final int imageLarge;

    // constructor
    public Items(String itemName, int itemPrice, int imageThumbnail, int imageLarge) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageThumbnail = imageThumbnail;
        this.imageLarge = imageLarge;
    }

    // getters and setters for the member variables

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getImageThumbnail() {
        return imageThumbnail;
    }

    public int getImageLarge() {
        return imageLarge;
    }
}
