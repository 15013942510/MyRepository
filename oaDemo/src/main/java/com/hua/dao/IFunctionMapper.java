package com.hua.dao;

import com.hua.domain.Function;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IFunctionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_functions
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer funcId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_functions
     *
     * @mbg.generated
     */
    int insert(Function record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_functions
     *
     * @mbg.generated
     */
    Function selectByPrimaryKey(Integer funcId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_functions
     *
     * @mbg.generated
     */
    List<Function> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_functions
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Function record);

	List<Function> getMenuList(@Param("userId")String userId);

	Function findFunctionByName(@Param("funcName")String funcName);

	List<Function> findFuncListByFunId(@Param("parentId")String parentId);
}