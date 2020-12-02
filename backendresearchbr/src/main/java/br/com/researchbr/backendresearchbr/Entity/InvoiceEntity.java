package br.com.researchbr.backendresearchbr.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = InvoiceEntity.class)
public class InvoiceEntity extends EntityAbstract<Long>{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectId", nullable = false)
    private ProjectEntity project;

    public InvoiceEntity() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }
}
