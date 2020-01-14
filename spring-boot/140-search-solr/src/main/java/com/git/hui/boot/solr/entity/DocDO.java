package com.git.hui.boot.solr.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class DocDO implements Serializable {
    private static final long serialVersionUID = 7245059137561820707L;
    @Id
    @Field("id")
    private Integer id;
    @Field("content_id")
    private Integer contentId;
    @Field("title")
    private String title;
    @Field("content")
    private String content;
    @Field("type")
    private Integer type;
    @Field("create_at")
    private Long createAt;
    @Field("publish_at")
    private Long publishAt;
}
