package api.iti0208.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String topic;

    private String title;

    private String description;

    @Column(name = "reward_description")
    private String rewardDescription;

    @Column(name = "file_location")
    private String fileLocation;

    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name = "posted_at")
    private Date postedAt;

    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser postedBy;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Reply> answers = new LinkedList<>();

    @PrePersist
    protected void onCreate() {
        postedAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public Post(String title, String topic, String description, AppUser postedBy) {
        this.title = title;
        this.topic = topic;
        this.description = description;
        this.postedBy = postedBy;
    }

    public Post(String title, String description, String topic) {
        this.title = title;
        this.topic = topic;
        this.description = description;
    }
}
