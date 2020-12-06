package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.DTO.InvoiceDto;
import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import br.com.researchbr.backendresearchbr.Service.Impl.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/invoices")
public class InvoiceController {
    @Autowired
    InvoiceServiceImpl invoiceService;

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);




    @GetMapping(value = "/all")
    public List<InvoiceEntity> listAll(){
        log.info("Finding invoices.");
        log.warn("The list can return null if no invoices are available!");
        List<InvoiceEntity> invoiceDtos = invoiceService.all();
        return invoiceDtos;
    }

    @PostMapping(value = "/new")
    public InvoiceDto create(@RequestBody InvoiceDto invoiceDto){
        log.info("Creating a new invoice.");
        InvoiceDto newInvoiceDto = invoiceService.saveChangingProjectOutgoing(invoiceDto);
        return newInvoiceDto;
    }

    @GetMapping(value = "/invoice/{id}")
    public InvoiceDto findAnInvoice(@PathVariable Long id){
        log.info("finding invoice of id " + id);
        return new InvoiceDto(invoiceService.byId(id));
    }

    @PutMapping(value = "/invoice/edit/{id}")
    public InvoiceDto editAnInvoice(@PathVariable(value = "id") Long id, @RequestBody InvoiceDto invoiceDto){
        log.info("Editing invoice of id: " + id);
        InvoiceDto newDTO = invoiceService.editChangingOutgoing(invoiceDto, id);
        return newDTO;
    }


    @DeleteMapping(value = "/invoice/delete/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        log.info("Deleting invoice of id: " + id);
        invoiceService.deleteChangingProjectOutgoing(id);
    }
}
