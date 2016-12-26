package com.xiyoukeji.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 文章详情
 *
 * Created by Matilda on 2016/12/13.
 */

@Entity
@Table(name = "article")
public class Article implements Comparable<Article>{

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    private Date time;
    @NotNull
    private String title;
    @Column(columnDefinition = "text")
    private String summary;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String text;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "article_img")
    private List<String> img;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Article o) {
        return o.getTime().compareTo(this.time);
    }
}
