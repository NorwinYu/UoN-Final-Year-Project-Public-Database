package propra2.leihOrDie.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import propra2.leihOrDie.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemForm {

    @NotNull
    @Size(min=2, max=50, message="Namen müssen 2-50 Buchstaben lang sein.")
    private String name;

    @NotNull
    @Size(min=2, max=50, message="Beschreibung muss 2-50 Buchstaben lang sein.")
    private String description;

    @NotNull
    @Range(min = 1,message="Preis muss einer positiven Zahl größer 1 entsprechen.")
    private int cost;

    @NotNull
    @Range(min = 1,message="Kaution muss einer positiven Zahl größer 1 entsprechen.")
    private int deposit;

    @NotNull
    @Range(min = 1 ,message="Verfügbarkeit muss einer positiven Zahl größer 1 entsprechen.")
    private int availableTime;

    private boolean availability;

    private User user;

    @Range(min = 0 ,message="Gib 0 ein wenn du den Artikel nicht verkaufen möchtest.")
    private int soldPrice;
}


