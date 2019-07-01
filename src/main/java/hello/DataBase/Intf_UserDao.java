package hello.DataBase;

import hello.Domain.User;

public interface Intf_UserDao {

    boolean saveUser(User user);
    boolean findUser(User user);





}
