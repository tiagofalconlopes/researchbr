package br.com.researchbr.backendresearchbr.Service.Impl;

import br.com.researchbr.backendresearchbr.DTO.ItemDto;
import br.com.researchbr.backendresearchbr.Entity.CategoryEntity;
import br.com.researchbr.backendresearchbr.Entity.ItemEntity;
import br.com.researchbr.backendresearchbr.Repository.CategoryRepository;
import br.com.researchbr.backendresearchbr.Repository.ItemRepository;
import br.com.researchbr.backendresearchbr.Service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ItemServiceImpl extends ServiceAbstract<ItemRepository, ItemEntity, Long> {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public ItemDto saveWithCategoryName(ItemDto itemDto) {
        logger.warn("If fields are missing it will not be possible to save the entity");
        try{
            CategoryEntity categoryEntity = categoryRepository.findByName(itemDto.getCategory().getName());
            itemDto.setCategory(categoryEntity);
            return new ItemDto(itemRepository.save(itemDto.convert()));
        } catch (Exception e) {
            logger.error("Error while saving entity: " + e.getMessage());
            throw new RuntimeException();
        }
    }

}
