package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

// --- //
      SessionFactory sessionFactory = context.getBean(SessionFactory.class);
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      session.createQuery("delete from User").executeUpdate();
      session.createQuery("delete from Car").executeUpdate();

      session.getTransaction().commit();
      session.close();
// ---- //

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      user1.setCar(new Car("Toyota", 10));
      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      user2.setCar(new Car("BMW", 5));
      User user3 = new User("User3", "Lastname3", "user3@mail.ru");
      user3.setCar(new Car("Audi", 8));
      User user4 = new User("User4", "Lastname4", "user4@mail.ru");
      user4.setCar(new Car("Fiat", 9));

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      System.out.println("=== Все пользователи ===");
      for (User user : userService.listUsers()) {
         System.out.println("User: " + user.getFirstName()
                 + ", Car: " + user.getCar().getModel()
                 + " " + user.getCar().getSeries());
      }

      System.out.println("\n=== Поиск по машине ===");
      Optional<User> found = userService.findUserByCar("BMW", 5);
      System.out.println("Найден пользователь: " + (found.isPresent() ? found.get().getFirstName() : "none"));

      context.close();

   }
}
