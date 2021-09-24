
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "PurchaseList")
public class PurchaseList  {

    @EmbeddedId
    private PurchaseListKey purchaseKey;

    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public PurchaseList() {

    }

    public PurchaseListKey getPurchaseListKey() {
        return purchaseKey;
    }

    public void setPurchaseListKey(PurchaseListKey purchaseKey) {
        this.purchaseKey = purchaseKey;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }







}




