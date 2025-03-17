package org.example.coralreef_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.coralreef_backend.entity.CoralPhoto;

import java.util.List;

@Mapper
public interface PhotoMapper {
    @Select("SELECT * FROM coralphoto")
    List<CoralPhoto> find();

    @Insert("INSERT INTO coralphoto (name,data,status,time,del_flag) VALUES (#{name},#{data},#{status},#{time},0)")
    void save(CoralPhoto coralphoto);
}
