package com.clocker.clocker.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"start_at", "end_at"},
        allowGetters = true
)
public abstract class TimerDateAudit implements Serializable {

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_at;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_at;

    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }
}