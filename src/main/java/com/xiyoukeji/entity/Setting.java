package com.xiyoukeji.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 全局信息
 *
 * Created by Matilda on 2016/12/12.
 */

@Entity
@Table(name = "setting")
public class Setting {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Column(columnDefinition = "text")
    private String value;
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "setting_img")
    private List<String> img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
