package com.offcn.dao;

import com.offcn.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppCategoryMapper {

    public List<AppCategory> getCategoryById(@Param("parentId") Integer id);
}
