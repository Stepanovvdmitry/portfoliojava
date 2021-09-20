import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import  org.hibernate.boot.registry.StandardServiceRegistry;
import  org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.MetadataSource;

import javax.persistence.Query;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        PurchaseList purchaseList = session.get(PurchaseList.class, new PurchaseListKey("Амбражевич Порфирий", "Веб-разработчик c 0 до PRO"));

        System.out.println("student_name: " + purchaseList.getPurchaseListKey().getStudentName());
        System.out.println("course_name: " + purchaseList.getPurchaseListKey().getCourseName());
        System.out.println("price: " + purchaseList.getPrice());
        System.out.println("subscription_date: " + purchaseList.getSubscriptionDate());

        System.out.print("\n\n\n");

        Course course = session.get(Course.class,1);
        System.out.println("id: " + course.getId());
        for(Student student : course.getStudents())
        {
            System.out.println(student.getName());
        }

        System.out.print("\n\n\n");
        Student student1 = session.get(Student.class,1);
        Course course1 = session.get(Course.class,2);
        Subscription subscription = session.get(Subscription.class,new SubscriptionKey(student1,course1));

        System.out.println("student_id: " + subscription.getSubscriptionKey().getStudent().getId());
        System.out.println("course_id: " + subscription.getSubscriptionKey().getCourse().getId());
        System.out.println("subscription_date: " + subscription.getSubscriptionDate());

        System.out.print("\n\n\n");
        Student student = session.get(Student.class,1);
        System.out.println("id: "+ student.getId());
        System.out.println("name: " + student.getName());
        System.out.println("age: " + student.getAge());
        System.out.println("registration_date: "+ student.getRegistrationDate());

        System.out.print("\n\n\n");
        Teacher teacher = session.get(Teacher.class,1);
        System.out.println("id: "+ teacher.getId());
        System.out.println("name: " + teacher.getName());
        System.out.println("age: " + teacher.getAge());
        System.out.println("salary: " + teacher.getSalary());

        List<Subscription> subscriptions = session.createQuery("FROM Subscription").list();




        subscriptions.forEach(s ->{
            LinkedPurchaseListKey key = new LinkedPurchaseListKey(s.getSubscriptionKey().getStudent(),
                    s.getSubscriptionKey().getCourse());
            String studentName = s.getSubscriptionKey().getStudent().getName();
            String courseName = s.getSubscriptionKey().getCourse().getName();
            Date subscriptionDate = s.getSubscriptionDate();
            LinkedPurchaseList element = new LinkedPurchaseList();
            element.setLinkedPurchaseListKey(key);
            element.setStudentName(studentName);
            element.setCourseName(courseName);
            element.setSubscriptionDate(subscriptionDate);
            session.save(element);
        });





        transaction.commit();
        sessionFactory.close();

    }
}
