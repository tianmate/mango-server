package cn.sky1998.mango.system.mapper;

import cn.sky1998.mango.system.domain.*;
import cn.sky1998.mango.system.domain.dto.AccountRoleDto;
import cn.sky1998.mango.system.domain.form.AccountForm;
import cn.sky1998.mango.system.domain.form.AccountRoleForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AccountMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Account record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Account record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    Account selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Account record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Account record);

    /**
     * select by username
     * @param username
     * @return object by primary key
     */
    Account selectByUsername(String username);

    List<Account> getList(AccountForm account);

    AccountRoleDto getUserRole(Account account);

    int assignRole(@Param("list")List<Role> roles ,@Param("id") Long id);

    int removeUserRole(@Param("accountId") Long accountId,@Param("roleId")Long roleId);

    List<Menu> getUserMenu(Account account);


     AccountRoleDto profile(Long userId);
}