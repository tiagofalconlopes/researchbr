package br.com.researchbr.backendresearchbr.Service.Impl;

import br.com.researchbr.backendresearchbr.Entity.CategoryEntity;
import br.com.researchbr.backendresearchbr.Repository.CategoryRepository;
import br.com.researchbr.backendresearchbr.Service.ServiceAbstract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CategoryServiceImpl extends ServiceAbstract<CategoryRepository, CategoryEntity, Long> {
}
