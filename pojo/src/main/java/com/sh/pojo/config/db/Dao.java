package com.sh.pojo.config.db;

import com.sh.pojo.account.AccountDao;
import com.sh.pojo.account.AccountRepository;
import com.sh.pojo.account.security.repository.UserDao;
import com.sh.pojo.account.security.repository.UserRepository;

import java.util.Objects;


public class Dao implements DaoFactory{

    private static final Dao DAO_FACTORY = new Dao();
    private Dao() {}

    public static <T> T getInstance(Object obj) {
        if(Objects.equals(AccountRepository.class, obj)) {
            return (T) accountRepository();
        }

        if(Objects.equals(UserRepository.class, obj)) {
            return (T) userRepository();
        }
        throw new NullPointerException();
    }

    private static AccountRepository accountRepository(){
        return new AccountDao(makeConnection());
    }

    private static UserRepository userRepository(){
        return new UserDao(makeConnection());
    }

    private static ConnectionMaker makeConnection() {
        ConnectionMaker makeConnection = new LocalConnectionMaker();
        return makeConnection;
    }
}
