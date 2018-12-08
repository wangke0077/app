package com.offcn.service;

import com.offcn.dao.AppCategoryMapper;
import com.offcn.dao.AppInfoMapper;
import com.offcn.pojo.AppCategory;
import com.offcn.pojo.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppCategoryServiceImpl implements AppCategoryService{

    @Autowired
    private AppCategoryMapper appCategoryMapper;


    @Override
    public List<AppCategory> getCategoryById(Integer id) {
        List<AppCategory> categoryById = appCategoryMapper.getCategoryById(id);
        return categoryById;
    }


}
