package com.liyang.domain.user;

import com.liyang.domain.base.FileRepository;
import com.liyang.domain.base.LogRepository;
//@RepositoryRestResource(excerptProjection = AbstractWorkflowLogProjection.class)
public interface UserFileRepository extends FileRepository<UserFile> {

}