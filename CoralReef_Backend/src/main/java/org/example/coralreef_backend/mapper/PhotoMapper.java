package org.example.coralreef_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.coralreef_backend.entity.CoralPhoto;

import java.util.List;

@Mapper
public interface PhotoMapper {
    @Select("SELECT * FROM coralphoto")
    List<CoralPhoto> findAll();

    @Select("SELECT * FROM coralphoto WHERE username=#{username}")
    List<CoralPhoto> find(String username);

    @Update("UPDATE coralphoto SET del_flag=1 WHERE name=#{photoname}")
    int deleteData(String photoname);

    @Insert("INSERT INTO coralphoto (name,data,status,time,del_flag,username,confidence) VALUES (#{name},#{data},#{status},#{time},0,#{username},#{confidence})")
    void save(CoralPhoto coralphoto);
}
