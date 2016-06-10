package hibernate_example;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author GHajba
 *
 */
public class Main {

    public static void main(String[] args) {
        
		
        
		//USER INPUT = APP CLASS
		final Book book = new Book("9781617291999 ", "Java 8 in Action", "Raoul-Gabriel Urma, Mario Fusco, and Alan Mycroft");
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
        final Session session1 = factory.openSession();
		
		final List<Book> books1 = session1.createQuery("from Book").list();
        System.out.println("\n----\n");
        System.out.println(MessageFormat.format("Storing {0} books in the database", books1.size()));
        for (final Book b : books1) {
            System.out.println(b);
        }
        System.out.println("\n----\n");
        session1.close();
        factory.close();
    }
}
