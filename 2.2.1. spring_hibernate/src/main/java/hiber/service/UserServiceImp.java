package hiber.service;

import org.hibernate.SessionFactory;
import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Autowired
   private SessionFactory sessionFactory;

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
