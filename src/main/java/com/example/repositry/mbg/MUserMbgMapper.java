package com.example.repositry.mbg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.model.mbg.MUserMbg;
import com.example.domain.model.mbg.MUserMbgExample;

@Mapper
public interface MUserMbgMapper {
    long countByExample(MUserMbgExample example);

    int deleteByExample(MUserMbgExample example);

    int deleteByPrimaryKey(String userId);

    int insert(MUserMbg row);

    int insertSelective(MUserMbg row);

    List<MUserMbg> selectByExample(MUserMbgExample example);

    MUserMbg selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("row") MUserMbg row, @Param("example") MUserMbgExample example);

    int updateByExample(@Param("row") MUserMbg row, @Param("example") MUserMbgExample example);

    int updateByPrimaryKeySelective(MUserMbg row);

    int updateByPrimaryKey(MUserMbg row);
}