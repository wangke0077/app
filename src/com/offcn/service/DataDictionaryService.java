package com.offcn.service;

import com.offcn.pojo.DataDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataDictionaryService {
    public List<DataDictionary> getByTypeCode(String typeCode);
}
