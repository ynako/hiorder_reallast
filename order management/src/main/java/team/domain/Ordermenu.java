package team.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import team.OrderManagementApplication;
import team.domain.OrderAdded;
import team.domain.OrderDeleted;
import team.domain.StatusUpdated;

@Entity
@Table(name = "Ordermenu_table")
@Data
//<<< DDD / Aggregate Root
public class Ordermenu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private Long menuId;

    private Integer quantity;

    private String orderStatus;

    private Integer tableIdx;

    private Date createdAt;

    @PostPersist
    public void onPostPersist() {
        OrderAdded orderAdded = new OrderAdded(this);
        orderAdded.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        StatusUpdated statusUpdated = new StatusUpdated(this);
        statusUpdated.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove() {
        OrderDeleted orderDeleted = new OrderDeleted(this);
        orderDeleted.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {}

    @PreRemove
    public void onPreRemove() {}

    public static OrdermenuRepository repository() {
        OrdermenuRepository ordermenuRepository = OrderManagementApplication.applicationContext.getBean(
            OrdermenuRepository.class
        );
        return ordermenuRepository;
    }

    //<<< Clean Arch / Port Method
    public static void orderAccept(AddCart addCart) {
        //implement business logic here:

        /** Example 1:  new item 
        Ordermenu ordermenu = new Ordermenu();
        repository().save(ordermenu);

        OrderAdded orderAdded = new OrderAdded(ordermenu);
        orderAdded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(addCart.get???()).ifPresent(ordermenu->{
            
            ordermenu // do something
            repository().save(ordermenu);

            OrderAdded orderAdded = new OrderAdded(ordermenu);
            orderAdded.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
