package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.DTO.InvoiceDto;
import br.com.researchbr.backendresearchbr.DTO.ItemDto;
import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import br.com.researchbr.backendresearchbr.Entity.ItemEntity;
import br.com.researchbr.backendresearchbr.Service.Impl.InvoiceServiceImpl;
import br.com.researchbr.backendresearchbr.Service.Impl.ItemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/items")
public class ItemController {
    @Autowired
    ItemServiceImpl itemService;

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);




    @GetMapping(value = "/all")
    public List<ItemEntity> listAll(){
        log.info("Finding items.");
        log.warn("The list can return null if no items are available!");
        List<ItemEntity> itemEntities = new ArrayList<>();
        itemService.all().forEach( item -> itemEntities.add(item));
        return itemEntities;
    }

    @PostMapping(value = "/new")
    public ItemDto create(@RequestBody ItemDto itemDto){
        log.info("Creating a new item.");
        ItemDto newItemDto = itemService.saveWithCategoryName(itemDto);
        return newItemDto;
    }

    @GetMapping(value = "/item/{id}")
    public ItemDto findAnInvoice(@PathVariable Long id){
        log.info("finding item of id " + id);
        return new ItemDto(itemService.byId(id));
    }

    @PutMapping(value = "/item/edit/{id}")
    public ItemDto editAnInvoice(@PathVariable(value = "id") Long id, @RequestBody ItemDto itemDto){
        log.info("Editing item of id: " + id);
        ItemDto newDTO = new ItemDto(itemService.edit(itemDto.convert(), id));
        return newDTO;
    }


    @DeleteMapping(value = "/item/delete/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        log.info("Deleting item of id: " + id);
        itemService.deleteById(id);
    }
}
