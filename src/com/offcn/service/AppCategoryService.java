package com.offcn.service;

import com.offcn.pojo.AppCategory;
import com.offcn.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppCategoryService {
    public List<AppCategory> getCategoryById(Integer id);

}
