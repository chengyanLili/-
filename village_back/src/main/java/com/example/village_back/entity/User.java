package com.example.village_back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author 李
 * @since 2023-04-01
 */
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("用户名")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String username;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("0:系统管理员；1：村干部；2普通村民")
      private Integer identify;
//        private String avatarUrl;
//        private String address;
//        private String phone;
//        private Date birthday;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getUsername() {
        return username;
    }

      public void setUsername(String username) {
          this.username = username;
      }

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }
//
//
//    public String getAvatarUrl() {
//        return avatarUrl;
//    }
//
//    public void getAvatarUrl(String avatarUrl) {
//        this.avatarUrl = avatarUrl;
//    }
    
    public String getPassword() {
        return password;
    }

      public void setPassword(String password) {
          this.password = password;
      }
    
    public Integer getIdentify() {
        return identify;
    }

      public void setIdentify(Integer identify) {
          this.identify = identify;
      }

    @Override
    public String toString() {
        return "User{" +
              "id=" + id +
                  ", username=" + username +
                  ", password=" + password +
                  ", identify=" + identify +
//                ", avatarUrl=" + avatarUrl +
//                ", username=" + address +
//                ", password=" + phone +
//                ", identify=" + birthday +
              "}";
    }
}
