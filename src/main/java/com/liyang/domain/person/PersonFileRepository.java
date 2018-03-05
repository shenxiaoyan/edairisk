package com.liyang.domain.person;

import com.liyang.domain.base.FileRepository;
import com.liyang.domain.base.LogRepository;
//@RepositoryRestResource(excerptProjection = AbstractWorkflowLogProjection.class)
public interface PersonFileRepository extends FileRepository<PersonFile> {

}