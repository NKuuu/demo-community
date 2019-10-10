package com.nky.community.dto;

/**
 * @Auther:nky
 * @Date:2019/10/9
 * @Description:com.nky.community.dto
 * @version:1.0
 */
public class GithubUser {
    private String name;
    private Long id;
    private String dio;

    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", dio='" + dio + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDio() {
        return dio;
    }

    public void setDio(String dio) {
        this.dio = dio;
    }
}
