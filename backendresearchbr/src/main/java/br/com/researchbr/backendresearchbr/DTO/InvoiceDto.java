package br.com.researchbr.backendresearchbr.DTO;

import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import br.com.researchbr.backendresearchbr.Entity.ItemEntity;
import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;

import java.time.LocalDate;
import java.util.List;

public class InvoiceDto {
    private Long id;
    private String code;
    private LocalDate date;
    private double value;
    private ProjectEntity project;
    private List<ItemEntity> items;

    public InvoiceDto() {
    }

    public InvoiceDto(InvoiceEntity invoice) {
        this.id = invoice.getId();
        this.code = invoice.getCode();
        this.date = invoice.getDate();
        this.value = invoice.getValue();
        this.project = invoice.getProject();
        this.items = invoice.getItems();
    }

    public InvoiceEntity convert() {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setId(this.id);
        invoice.setCode(this.code);
        invoice.setDate(this.date);
        invoice.setValue(this.value);
        invoice.setProject(this.project);
        invoice.setItems(this.items);
        return invoice;
    }

    public Long getId() {
        return id;
    }

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

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }
}
