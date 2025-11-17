package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public Optional<User> findUserByCarModel(String carModel, int series) {
      String hql="SELECT u FROM User u JOIN u.car cm WHERE cm.model =:model and cm.series=:series";
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery(hql,User.class);
      query.setParameter("model",carModel);
      query.setParameter("series",series);
      try {
         User user=query.getSingleResult();
         return Optional.of(user);
      } catch (Exception e) {
         return Optional.empty();
      }

   }

}
