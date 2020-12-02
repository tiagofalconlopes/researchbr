package br.com.researchbr.backendresearchbr.Repository;

import br.com.researchbr.backendresearchbr.Entity.InvoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Long> {
    InvoiceEntity findByCode(String code);
    List<InvoiceEntity> findByDate(LocalDate date);
}
