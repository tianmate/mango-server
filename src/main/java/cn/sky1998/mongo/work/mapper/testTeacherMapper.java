package cn.sky1998.mongo.work.mapper;

import java.util.List;
import cn.sky1998.mongo.work.domain.testTeacher;

/**
 * teacher_fucMapper接口
 * 
 * @author tcy
 * @date 2022-04-20
 */
public interface testTeacherMapper 
{
    /**
     * 查询teacher_fuc
     * 
     * @param id teacher_fucID
     * @return teacher_fuc
     */
    public testTeacher selecttestTeacherById(java.math.BigInteger id);

    /**
     * 查询teacher_fuc列表
     * 
     * @param testTeacher teacher_fuc
     * @return teacher_fuc集合
     */
    public List<testTeacher> selecttestTeacherList(testTeacher testTeacher);

    /**
     * 新增teacher_fuc
     * 
     * @param testTeacher teacher_fuc
     * @return 结果
     */
    public int inserttestTeacher(testTeacher testTeacher);

    /**
     * 修改teacher_fuc
     * 
     * @param testTeacher teacher_fuc
     * @return 结果
     */
    public int updatetestTeacher(testTeacher testTeacher);

    /**
     * 删除teacher_fuc
     * 
     * @param id teacher_fucID
     * @return 结果
     */
    public int deletetestTeacherById(java.math.BigInteger id);

    /**
     * 批量删除teacher_fuc
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletetestTeacherByIds(java.math.BigInteger[] ids);
}