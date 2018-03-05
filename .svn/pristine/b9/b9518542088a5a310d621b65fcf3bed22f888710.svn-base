package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.liyang.domain.account.Account;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.product.Product;
import com.liyang.domain.product.ProductAct;
import com.liyang.domain.product.ProductActRepository;
import com.liyang.domain.product.ProductFile;
import com.liyang.domain.product.ProductLog;
import com.liyang.domain.product.ProductLogRepository;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.product.ProductState;
import com.liyang.domain.product.ProductStateRepository;
import com.liyang.domain.product.ProductWorkflow;
import com.liyang.domain.product.ProductWorkflowRepository;
import com.liyang.domain.role.RoleRepository;

@Service
@Order(100)
public class ProductService extends AbstractWorkflowService<Product,ProductWorkflow, ProductAct,ProductState,ProductLog,ProductFile> {
	
	@Autowired
	ProductActRepository productActRepository;
	
	@Autowired
	ProductStateRepository productStateRepository;
	
	@Autowired
	ProductLogRepository  productLogRepository;
	
	@Autowired
	ProductRepository  productRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	
	@Autowired
	ProductWorkflowRepository productWorkflowRepository;
	
	
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		System.out.println("ProductService初始化");
		
			long findAll = productActRepository.count();
	        if(findAll == 0 ){
			ProductWorkflow productApplyWorkflow = new ProductWorkflow();
			productApplyWorkflow.setLabel("入职流程");
			productApplyWorkflow = productWorkflowRepository.save(productApplyWorkflow);
			
			
			ProductAct save1 = productActRepository.save(new ProductAct("创建","create",10,ActGroup.UPDATE));
			ProductAct save2 = productActRepository.save(new ProductAct("读取","read",20,ActGroup.READ));
			ProductAct save3 = productActRepository.save(new ProductAct("更新","update",30,ActGroup.UPDATE));
			ProductAct save4 = productActRepository.save(new ProductAct("删除","delete",40,ActGroup.UPDATE));
			ProductAct applyAct = productActRepository.save(new ProductAct("入门申请","apply",45,ActGroup.OPERATE));
			ProductAct save5 = productActRepository.save(new ProductAct("自己的列表","listOwn",50,ActGroup.READ));
			ProductAct save6 = productActRepository.save(new ProductAct("部门的列表","listOwnDepartment",60,ActGroup.READ));
			ProductAct save7 = productActRepository.save(new ProductAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70,ActGroup.READ));
			ProductAct save8 = productActRepository.save(new ProductAct("通知其他人","noticeOther",80,ActGroup.NOTICE));
			ProductAct save9 = productActRepository.save(new ProductAct("通知操作者","noticeActUser",90,ActGroup.NOTICE));
			ProductAct save10 = productActRepository.save(new ProductAct("通知展示人","noticeShowUser",100,ActGroup.NOTICE));
			
	
			ProductState newState =new ProductState("待创建",0,"UNBORN");
			newState.setActs(Arrays.asList(applyAct).stream().collect(Collectors.toSet()));
			newState = productStateRepository.save(newState);
			
			ProductState applyState = productStateRepository.save(new ProductState("待审核",100,"APPLIED"));
			ProductState createState = new ProductState("已创建",200,"CREATED");
			ProductState enableState = new ProductState("有效",300,"ENABLED");
			
			
			enableState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));
			productStateRepository.save(enableState);
			productStateRepository.save(new ProductState("无效",400,"DISABLED"));
			productStateRepository.save(new ProductState("已删除",500,"DELETED"));
			
			
			productActRepository.save(applyAct);
			
		}
		
	}

	@Override
	public LogRepository<ProductLog> getLogRepository() {
		// TODO Auto-generated method stub
		return productLogRepository;
	}

	@Override
	public AuditorEntityRepository<Product> getAuditorEntityRepository() {
		
		return productRepository;
	}

	
	

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Product().setLogRepository(productLogRepository);
		
	}

	@Override
	public ProductLog getLogInstance() {
		// TODO Auto-generated method stub
		return new ProductLog();
	}

	@Override
	public ActRepository<ProductAct> getActRepository() {
		// TODO Auto-generated method stub
		return productActRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityService() {
		new Product().setService(this);
		
	}

	@Override
	public ProductFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new ProductFile();
	}
	public Product getProductByAccount(Account account){
		return null;
	}

}
