package org.example.coralreef_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class CoralPhoto {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String name;
    private String data;
    private String status;
    private String time;
    private String username;

    public CoralPhoto(String name, String data, String status, String time, String username) {
        this.name = name;
        this.data = data;
        this.status = status;
        this.time = time;
        this.username = username;
    }
}
