package com.sh.pojo.config.db;

import com.sh.pojo.account.AccountDao;
import com.sh.pojo.account.AccountRepository;

public class DaoFactory {

    private static final DaoFactory DAO_FACTORY = new DaoFactory();
    private DaoFactory() {}

    public static <T> T getBean(Object obj) {
        if(AccountRepository.class == obj) {
            System.out.println("here ???");
            return (T) accountRepository();
        }
        return null;
    }

/*    private static AccountDao accountDao() {
        // UserDao가 필요로 하는 객체 생성 (클라이언트 쪽, service 등에서 주입)
        return new AccountDao(makeConnection());
    }*/

    private static AccountRepository accountRepository(){
        return new AccountDao(makeConnection());
    }

    private static ConnectionMaker makeConnection() {
        ConnectionMaker makeConnection = new LocalConnectionMaker();
        return makeConnection;
    }
}
