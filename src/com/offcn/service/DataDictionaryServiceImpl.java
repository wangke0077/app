package com.offcn.service;

import com.offcn.dao.DataDictionaryMapper;
import com.offcn.pojo.DataDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService{
    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;
    @Override
    public List<DataDictionary> getByTypeCode(String typeCode) {

        List<DataDictionary> byTypeCode = dataDictionaryMapper.getByTypeCode(typeCode);
        return byTypeCode;
    }
}
