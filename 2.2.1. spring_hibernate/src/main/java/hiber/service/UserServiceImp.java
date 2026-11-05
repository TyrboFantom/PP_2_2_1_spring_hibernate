package hiber.service;

import org.hibernate.SessionFactory;
import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {


   private final UserDao userDao;
   private final SessionFactory sessionFactory;

   public UserServiceImp(SessionFactory sessionFactory, UserDao userDao) {
      this.sessionFactory = sessionFactory;
      this.userDao = userDao;
   }

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Override
   @Transactional(readOnly = true)
   public User getUserByCar(String model, int series) {
      return sessionFactory.getCurrentSession()
              .createQuery(
                      "FROM User AS user WHERE user.car.model = :model AND user.car.series = :series",
                      User.class
              )
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult();
   }
}
