package com.example.training.thread;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class SuperBO implements Serializable {

    //@CreatedDate
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    // @CreatedBy
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    // @LastModifiedDate
    @Column(name = "update_date")
    private Date updateDate;

    // @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Version
    @Column(name = "tech_lock", nullable = false)
    private int techLock;

    @PrePersist
    private void prePersist() {
        this.setCreationDate(new Timestamp(System.currentTimeMillis()));
        this.setCreatedBy(UserContext.get().getUserName());
    }

    @PreUpdate
    private void preUpdate(){
        this.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        this.setUpdatedBy(UserContext.get().getUserName());
    }

}
