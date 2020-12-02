package br.com.researchbr.backendresearchbr.DTO;

import br.com.researchbr.backendresearchbr.Entity.EntityAbstract;
import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public class ProjectDto {

    private Long id;
    private String code;
    private String agency;
    private String title;
    private double total;
    private double outgoing;
    private LocalDate start;
    private LocalDate end;
    private Set<UserEntity> users;
    private List<Long> usersIds;
    private List<InvoiceEntity> invoices;

    public ProjectDto() {
    }

    public ProjectDto(ProjectEntity project) {
        this.id = project.getId();
        this.code = project.getCode();
        this.agency = project.getAgency();
        this.title = project.getTitle();
        this.total = project.getTotal();
        this.outgoing = project.getOutgoing();
        this.start = project.getStart();
        this.end = project.getEnd();
        this.users = project.getUsers();
        this.invoices = project.getInvoices();
    }

    public ProjectEntity convert() {
        ProjectEntity project = new ProjectEntity();
        project.setId(this.id);
        project.setCode(this.code);
        project.setAgency(this.agency);
        project.setTitle(this.title);
        project.setTotal(this.total);
        project.setOutgoing(this.outgoing);
        project.setStart(this.start);
        project.setEnd(this.end);
        project.setUsers(this.users);
        project.setInvoices(this.invoices);
        return project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {

    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public List<Long> getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(List<Long> usersIds) {
        this.usersIds = usersIds;
    }

    public List<InvoiceEntity> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceEntity> invoices) {
        this.invoices = invoices;
    }
}
