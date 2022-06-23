package cn.sky1998.mango.work.teacher.service;

import java.util.List;
import cn.sky1998.mango.work.teacher.domain.testTeacher;

/**
 * 教师管理Service接口
 * 
 * @author tcy
 * @date 2022-04-21
 */
public interface ItestTeacherService 
{
    /**
     * 查询教师管理
     * 
     * @param id 教师管理ID
     * @return 教师管理
     */
    public testTeacher selecttestTeacherById(java.math.BigInteger id);

    /**
     * 查询教师管理列表
     * 
     * @param testTeacher 教师管理
     * @return 教师管理集合
     */
    public List<testTeacher> selecttestTeacherList(testTeacher testTeacher);

    /**
     * 新增教师管理
     * 
     * @param testTeacher 教师管理
     * @return 结果
     */
    public int inserttestTeacher(testTeacher testTeacher);

    /**
     * 修改教师管理
     * 
     * @param testTeacher 教师管理
     * @return 结果
     */
    public int updatetestTeacher(testTeacher testTeacher);

    /**
     * 批量删除教师管理
     * 
     * @param ids 需要删除的教师管理ID
     * @return 结果
     */
    public int deletetestTeacherByIds(java.math.BigInteger[] ids);

    /**
     * 删除教师管理信息
     * 
     * @param id 教师管理ID
     * @return 结果
     */
    public int deletetestTeacherById(java.math.BigInteger id);
}