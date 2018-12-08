package com.offcn.dao;

import com.offcn.pojo.DataDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataDictionaryMapper {

    public List<DataDictionary> getByTypeCode(@Param("typeCode") String typeCode);
}
