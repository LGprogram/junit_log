package com.kaishengit;

import com.kaishengit.mapper.AccountMapper;
import com.kaishengit.pojo.Account;
import com.kaishengit.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * Created by liu on 2017/1/6.
 */
public class AccountInterfaceTestCase {
    @Test
    public void insert(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        accountMapper.insert("lilisi","beijing");

        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void save(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        Account account = new Account("jimi","America");
        accountMapper.save(account);
        sqlSession.commit();
        Integer id =  account.getId();
        System.out.println(id);

        sqlSession.close();

    }
    @Test
    public void update(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        Account account  = accountMapper.findById(1);
        account .setAccname("aa");
        account .setAddress("fenlan");
        accountMapper.update(account);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void del(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        accountMapper.del(1);
        sqlSession.commit();
        sqlSession.close();
    }

}
