package com.kaishengit.mapper;

import com.kaishengit.pojo.Account;
import org.apache.ibatis.annotations.*;

/**
 * Created by liu on 2017/1/6.
 */
public interface AccountMapper {
    @Insert("insert into t_account(accname,address) values(#{accname},#{address})")
    void insert(@Param("accname")String accname, @Param("address") String address);

    @Insert("insert into t_account(accname,address) values(#{accname},#{address})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void save(Account account);
    @Select("select * from t_account where id=#{id}")
    Account findById(Integer id);

    @Update("update t_account set accname= #{accname},address= #{address} where id= #{id}")
    void update(Account account );

    @Delete("delete from t_account where id =#{id}")
    @Options(flushCache = Options.FlushCachePolicy.FALSE)
    void del(Integer id);
}
