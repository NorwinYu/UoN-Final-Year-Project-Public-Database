package propra2.leihOrDie.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="buy_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    private int purchasePrice;
    private String status;

    @ManyToOne
    @JoinColumn(name="username")
    private User buyer;

    public Buy() {}

    public Buy(Item item, int purchasePrice, String status, User buyer) {
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.status = status;
        this.buyer = buyer;
    }
}
