package org.example.coralreef_backend.dto;
import lombok.Data;
/**
 * 用户查询参数 DTO
 */
@Data
public class UserRequestDto {
    /**
     * 当前页码 (默认 1)
     */
    private Integer pageNum = 1;

    /**
     * 每页大小 (默认 10)
     */
    private Integer pageSize = 10;

    /**
     * 用户名 (模糊查询)
     */
    private String username;

    /**
     * 用户邮箱 (精确查询)
     */
    private String email;

    /**
     * 用户电话 (精确查询)
     */
    private String phone;

    /**
     * 是否启用 (1: 启用, 0: 禁用)
     */
    private Integer enabled;
}
