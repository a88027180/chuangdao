package com.xiyoukeji.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 管理团队
 *
 * Created by Matilda on 2016/12/12.
 */

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    private String name;
    private String spell;
    private String appellation; //称呼
    private String main_title;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "manager_subtitle")
    private List<String> sub_title;
    @Column(columnDefinition = "text")
    private String description;

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

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getMain_title() {
        return main_title;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }

    public List<String> getSub_title() {
        return sub_title;
    }

    public void setSub_title(List<String> sub_title) {
        this.sub_title = sub_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
