package edu.cnm.deepdive.lightbulb.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.lightbulb.view.FlatComment;
import edu.cnm.deepdive.lightbulb.view.FlatKeyword;
import edu.cnm.deepdive.lightbulb.view.FlatUser;
import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("JpaDataSourceORMInspection")
@Component
@Entity
@Table(
    indexes = {
        @Index(columnList = "created")
    }
)
public class Comment implements FlatComment {

  private static EntityLinks entityLinks;

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "comment_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;


  @Column(length = 1024)
  private String name;

  @NonNull
  @Column(length = 4096, nullable = false)
  private String text;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  @JsonSerialize(as = FlatUser.class)
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reference_id", updatable = false)
  @JsonSerialize(as = FlatComment.class)
  private Comment reference;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "reference",
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("created ASC")
  @JsonSerialize(contentAs = FlatComment.class)
  private List<Comment> responses = new LinkedList<>();

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
  @JoinTable(name = "comment_keyword",
      joinColumns = @JoinColumn(name = "comment_id", nullable = false, updatable = false),
      inverseJoinColumns = @JoinColumn(name = "keyword_id", nullable = false, updatable = false))
  @JsonSerialize(contentAs = FlatKeyword.class)
  private Set<Keyword> keywords = new HashSet<>();

  @Override
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }



  @NonNull
  public Date getUpdated() {
    return updated;
  }


  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String text) {
    this.text = text;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Comment getReference() {
    return reference;
  }

  public void setReference(Comment reference) {
    this.reference = reference;
  }

  public List<Comment> getResponses() {
    return responses;
  }

  public Set<Keyword> getKeywords() {
    return keywords;
  }

  @Override
  public URI getHref() {
    return entityLinks.linkForItemResource(Comment.class, id).toUri();
  }

  @PostConstruct
  private void init() {
    entityLinks.toString();
  }

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Comment.entityLinks = entityLinks;
  }
}
