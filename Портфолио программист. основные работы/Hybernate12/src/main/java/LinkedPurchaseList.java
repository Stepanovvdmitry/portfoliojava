import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchaseList

{
    @EmbeddedId
    LinkedPurchaseListKey linkedPurchaseListKey;



    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "subscription_date")
    private Date subscriptionDate;


    public LinkedPurchaseListKey getLinkedPurchaseListKey() {
        return linkedPurchaseListKey;
    }

    public void setLinkedPurchaseListKey(LinkedPurchaseListKey linkedPurchaseListKey) {
        this.linkedPurchaseListKey = linkedPurchaseListKey;}

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Date getSubscriptionDate() {
            return subscriptionDate;
        }

        public void setSubscriptionDate(Date subscriptionDate) {
            this.subscriptionDate = subscriptionDate;

    }
}
