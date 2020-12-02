package br.com.researchbr.backendresearchbr.Service.Impl;

import br.com.researchbr.backendresearchbr.DTO.InvoiceDto;
import br.com.researchbr.backendresearchbr.DTO.ProjectDto;
import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import br.com.researchbr.backendresearchbr.Repository.InvoiceRepository;
import br.com.researchbr.backendresearchbr.Repository.ProjectRepository;
import br.com.researchbr.backendresearchbr.Service.ServiceAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class InvoiceServiceImpl extends ServiceAbstract<InvoiceRepository, InvoiceEntity, Long> {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ProjectRepository projectRepository;

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public InvoiceDto saveChangingProjectOutgoing(InvoiceDto invoiceDto) {
        logger.warn("If fields are missing it will not be possible to save the entity");
        try{
            testInvoiceDuplicatedData(invoiceDto);
            InvoiceEntity invoiceEntity = createInvoiceEntity(invoiceDto, "add");
            InvoiceEntity invoiceEntity1 = invoiceRepository.save(invoiceEntity);
            InvoiceDto newDto = new InvoiceDto(invoiceEntity1);
            newDto.setId(invoiceEntity1.getId());
            return newDto;
        } catch (Exception e) {
            logger.error("Error while saving entity: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public InvoiceDto editChangingOutgoing(InvoiceDto invoiceDto, Long id) {
        try{
            invoiceDto.setId(id);
            InvoiceEntity invoice = invoiceDto.convert();
            changeProjectOutgoingEditing(invoiceDto);
            invoice.setId(id);
            System.out.println((invoice.getId()));
            return new InvoiceDto(repository.save(invoice));
        } catch (Exception e) {
            logger.error("Error while editing entity: " + e.getMessage());
            throw new RuntimeException();
        }

    }

    private void changeProjectOutgoingEditing(InvoiceDto invoiceDto) {
        ProjectEntity projectEntity = projectRepository.findById(invoiceDto.getProject().getId()).get();
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceDto.getId()).get();
        double value = projectEntity.getOutgoing();
        double valueChanged = invoiceDto.getValue() - invoiceEntity.getValue();
        projectEntity.setOutgoing(value + valueChanged);
        projectRepository.save(projectEntity);
    }

    public void deleteChangingProjectOutgoing(Long id) {
        logger.warn("If fields are missing it will not be possible to save the entity");
        try{
            InvoiceDto invoiceDto = new InvoiceDto(invoiceRepository.findById(id).get());

            InvoiceEntity invoiceEntity = createInvoiceEntity(invoiceDto, "reduce");
            invoiceRepository.delete(invoiceEntity);
        } catch (Exception e) {
            logger.error("Error while saving entity: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void testInvoiceDuplicatedData(InvoiceDto invoiceDto) {
        InvoiceEntity invoiceWithDuplicatedCode = invoiceRepository.findByCode(invoiceDto.getCode());
        if(invoiceWithDuplicatedCode != null) {
            log.error(String.format("Duplicated code %", invoiceDto.getCode()));
            throw new RuntimeException("Duplicated code.");
        }
    }

    private InvoiceEntity createInvoiceEntity(InvoiceDto invoiceDto, String method) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setCode(invoiceDto.getCode());
        invoiceEntity.setDate(invoiceDto.getDate());
        invoiceEntity.setValue(invoiceDto.getValue());
        invoiceEntity.setProject(invoiceDto.getProject());
        changeProjectOutgoing(invoiceDto, method);
        return invoiceEntity;
    }

    private void changeProjectOutgoing(InvoiceDto invoiceDto, String method) {
        ProjectEntity projectEntity = projectRepository.findById(invoiceDto.getProject().getId()).get();
        double value = projectEntity.getOutgoing();
        if(method.equals("add")) {
            projectEntity.setOutgoing(value + invoiceDto.getValue());
        }
        if(method.equals("reduce")) {
            projectEntity.setOutgoing(value - invoiceDto.getValue());
        }
        projectRepository.save(projectEntity);
    }

}
