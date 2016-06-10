package hibernate_example;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import org.hibernate.HibernateException;

/**
 * @author GHajba
 *
 */
public class Main {

    public static void main(String[] args) {
        
		
        
		//USER INPUT = APP CLASS
		final Book book = new Book("Java 8 in Action", "Raoul-Gabriel Urma, Mario Fusco, and Alan Mycroft");
        //END OF THE APP CLASS
		
		
		//SERVICE PROCESSING V1
		final Configuration configuration = new Configuration().configure();
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        final SessionFactory factory = configuration.buildSessionFactory(builder.build());
        final Session session = factory.openSession();
		//SERVICE CLASS CONTINUED	
		
		//DAO processing = DAO CLASS
		session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
		//I PLAN TO RETURN THE SESSION OBJECT BACK TO SERVICE
		//END OF THE DAO CLASS
		
		
		//SERVICE PROCESSING V2 WITH THE SESSION TAKEN BACK BY DAO CLASS 
			
			//THIS IS FOR DISPLAYING ALL THE LIST
        final List<Book> books = session.createCriteria(Book.class).list();
        System.out.println("\n----\n");
        System.out.println(MessageFormat.format("Storing {0} books in the database", books.size()));
        for (final Book b : books) {
            System.out.println(b);
        }
        System.out.println("\n----\n");
        session.close();
        //factory.close();
		//END OF THE SERVICE CLASS
		
		
		//TESTING PURPOSE ONLY!
		
			//ADDING THE DATA
        final Session session2 = factory.openSession();
		session2.beginTransaction();
        session2.save(book);
        session2.getTransaction().commit();
		
		
		final Session session1 = factory.openSession();
		
		
		final List<Book> books1 = session1.createQuery("from Book where id=3").list();
        System.out.println("\n----\n");
        System.out.println(MessageFormat.format("Storing {0} books in the database", books1.size()));
        for (final Book b : books1) {
            System.out.println(b);
        }
        System.out.println("\n----\n");
        session1.close();
        
		
		//getting the specific book by ID: SOLELY FOR UPDATING AND DELETING MESSAGE BECAUSE OF ID 
		//								   ALL WILL LATER UPDATE THE VALUES THROUGH SETTER METHODS
		Session sessionq = factory.openSession();
		Transaction tx = null;
		Book b = null; //reference for book that has been retrieved to be displayed later
		try{
			tx = sessionq.beginTransaction();
			String hql = "from Book where id = :id";
			Query query = sessionq.createQuery(hql);
			query.setParameter("id",4);
			b = (Book) query.uniqueResult();
		}catch(RuntimeException e){
			e.printStackTrace();
		}finally{
			sessionq.close();
		}
		
		//UPDATE THE BOOKS
		int id1 = 5;//this is the argument for the method
		Session sessionww = factory.openSession();
		Transaction tx11 = null;
		Book libro1=null;//this is the argument for the method
		libro1 = (Book)sessionww.get(Book.class, id1); // suppose that we get this Book for modification
		//then we will update the data inside the book
		libro1.setTitle("spangsbab");
		libro1.setAuthor("Bertong Spongha");
		
		try{
			tx11 = sessionww.beginTransaction();
			System.out.println(libro1);
			sessionww.update(libro1);
			tx11.commit();
		}catch (HibernateException e) {
			if (tx11!=null) tx11.rollback();
			e.printStackTrace();
		}finally {
			sessionww.close();
		}
		//return libro;
		
		//DELETION OF BOOK BY ID
		int id = 6; //this is the argument for the method
		Session sessionw = factory.openSession();
		Transaction tx1 = null;
		Book libro=null;
		try{
			tx1 = sessionw.beginTransaction();
			libro = (Book)sessionw.get(Book.class, id);
			System.out.println(libro);
			sessionw.delete(libro);
			tx1.commit();
		}catch (Exception e) {
			if (tx1!=null) tx1.rollback();
			System.out.println(tx1);
			e.printStackTrace();
			//sessionw.close();
		}finally {
			sessionw.close();
		}
		//return libro;
		
		factory.close();
    }
}
