package br.com.researchbr.backendresearchbr.Service;

import br.com.researchbr.backendresearchbr.BackendresearchbrApplication;
import br.com.researchbr.backendresearchbr.Entity.EntityAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class ServiceAbstract<
        R extends CrudRepository<E, T>,
        E extends EntityAbstract, T> {

    @Autowired
    protected R repository;

    protected Logger logger = LoggerFactory.getLogger(BackendresearchbrApplication.class);

    @Transactional(rollbackFor = Exception.class)
    public E save(E entity) {
        logger.warn("If fields are missing it will not be possible to save the entity");
        try{
            return repository.save(entity);
        } catch (Exception e) {
            logger.error("Error while saving entity: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public E edit(E entidade, T id) {
        try{
            entidade.setId(id);
            return repository.save(entidade);
        } catch (Exception e) {
            logger.error("Error while editing entity: " + e.getMessage());
            throw new RuntimeException();
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public List<E> all() {
        try {
            return (List<E>) repository.findAll();
        } catch (Exception e) {
            logger.error("Error while listing all entities: " + e.getMessage());
            throw new RuntimeException();
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public E byId(T id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            logger.error("It was not possible to find the entity of id: " + id +". Message: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(T id) {
        try {
            E entity = repository.findById(id).get();
            repository.delete(entity);
        } catch (Exception e) {
            logger.error("It was not possible to delete the entity of id: " + id +". Message: " + e.getMessage());
            throw new RuntimeException();
        }
    }

}
