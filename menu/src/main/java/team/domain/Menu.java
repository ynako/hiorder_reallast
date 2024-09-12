package team.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import team.MenuApplication;
import team.domain.MenuCreated;
import team.domain.MenuUpdated;

@Entity
@Table(name = "Menu_table")
@Data
//<<< DDD / Aggregate Root
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menuId;

    private String menuName;

    private String description;

    private Integer price;

    private Boolean isAvailable;

    private String category;

    private Integer discount;

    private Integer todayOrderCount;

    private Integer accOrderCount;

    private Boolean isHot;

    private String imageUrl;

    @PostPersist
    public void onPostPersist() {
        MenuCreated menuCreated = new MenuCreated(this);
        menuCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        MenuUpdated menuUpdated = new MenuUpdated(this);
        menuUpdated.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {}

    @PreUpdate
    public void onPreUpdate() {}

    @PreRemove
    public void onPreRemove() {}

    public static MenuRepository repository() {
        MenuRepository menuRepository = MenuApplication.applicationContext.getBean(
            MenuRepository.class
        );
        return menuRepository;
    }

    //<<< Clean Arch / Port Method
    public void menuDelete() {
        //implement business logic here:

        MenuDeleted menuDeleted = new MenuDeleted(this);
        menuDeleted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
