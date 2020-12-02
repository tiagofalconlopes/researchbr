package br.com.researchbr.backendresearchbr.DTO;

import br.com.researchbr.backendresearchbr.Entity.CategoryEntity;
import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import br.com.researchbr.backendresearchbr.Entity.ItemEntity;

public class ItemDto {

    private Long id;
    private String name;
    private double value;
    private int quantity;
    private String description;
    private InvoiceEntity invoice;
    private CategoryEntity category;

    public ItemDto() {
    }

    public ItemDto(ItemEntity item) {
        this.id = item.getId();
        this.name = item.getName();
        this.value = item.getValue();
        this.quantity = item.getQuantity();
        this.description = item.getDescription();
        this.invoice = item.getInvoice();
        this.category = item.getCategory();
    }

    public ItemEntity convert() {
        ItemEntity item = new ItemEntity();
        item.setId(this.id);
        item.setName(this.name);
        item.setValue(this.value);
        item.setQuantity(this.quantity);
        item.setDescription(this.description);
        item.setInvoice(this.invoice);
        item.setCategory(this.category);
        return item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
