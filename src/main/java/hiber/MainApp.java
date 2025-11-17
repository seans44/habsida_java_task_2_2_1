package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Toyota", 2018);
      Car car2 = new Car("Honda", 2018);
      Car car3 = new Car("Ford", 2018);
      Car car4 = new Car("Fiat", 2018);

      User user1 = new User("A", "B", "qwr@naver.com");
      user1.setCar(car1);

      // создать всех (4)

      userService.add(user1);
      // добавить всех (4)

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         // model
          // series
         System.out.println();
      }

      Optional<User> user2 = userService.findUserByCarModel("Fiat", 1);

      System.out.println(user2);



      context.close();
   }
}
