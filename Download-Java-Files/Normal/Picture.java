package propra2.leihOrDie.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Picture {
    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="picture_id")
    private Long id;

    private String type;

    public Picture() {}

    public Picture(Item item) {
        this.item = item;
    }
}

