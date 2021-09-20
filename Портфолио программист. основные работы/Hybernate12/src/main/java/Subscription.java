import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Subscriptions")
public class Subscription {

    @EmbeddedId
    private SubscriptionKey subscriptionKey;


    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Subscription() {
    }

    public SubscriptionKey getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(SubscriptionKey subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }





}
