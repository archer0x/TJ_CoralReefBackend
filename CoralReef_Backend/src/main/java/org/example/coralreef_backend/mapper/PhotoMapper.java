package org.example.coralreef_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.coralreef_backend.entity.CoralPhoto;

import java.util.List;

@Mapper
public interface PhotoMapper {
    @Select("SELECT * FROM coralphoto WHERE username=#{username}")
    List<CoralPhoto> find(String username);

    @Insert("INSERT INTO coralphoto (name,data,status,time,del_flag,username) VALUES (#{name},#{data},#{status},#{time},0,#{username})")
    void save(CoralPhoto coralphoto);
}
