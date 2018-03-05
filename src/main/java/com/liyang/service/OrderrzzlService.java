package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.datetransform.DateTransform;
import com.liyang.Enum.OrderTypeEnum;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Departmenttype;
import com.liyang.domain.department.DepartmenttypeRepository;
import com.liyang.domain.orderrzzl.Orderrzzl;
import com.liyang.domain.orderrzzl.OrderrzzlAct;
import com.liyang.domain.orderrzzl.OrderrzzlActRepository;
import com.liyang.domain.orderrzzl.OrderrzzlFile;
import com.liyang.domain.orderrzzl.OrderrzzlLog;
import com.liyang.domain.orderrzzl.OrderrzzlLogRepository;
import com.liyang.domain.orderrzzl.OrderrzzlRepository;
import com.liyang.domain.orderrzzl.OrderrzzlState;
import com.liyang.domain.orderrzzl.OrderrzzlStateRepository;
import com.liyang.domain.orderrzzl.OrderrzzlWorkflow;
import com.liyang.domain.orderrzzl.OrderrzzlWorkflowRepository;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ProductUtil;
import com.liyang.util.ReturnObject;
import com.liyang.util.WechatImageAsyncFetchEvent;

@Service
@Order(35)
public class OrderrzzlService extends AbstractWorkflowService<Orderrzzl, OrderrzzlWorkflow, OrderrzzlAct, OrderrzzlState, OrderrzzlLog, OrderrzzlFile> {
    @Autowired
    OrderrzzlActRepository orderrzzlActRepository;

    @Autowired
    OrderrzzlStateRepository orderrzzlStateRepository;

    @Autowired
    OrderrzzlLogRepository  personLogRepository;

    @Autowired
    OrderrzzlRepository  orderrzzlRepository;

    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
    private String OPEN_ACC_APPLY;
    @Autowired
    OrderrzzlWorkflowRepository OrderrzzlWorkflowRepository;

    @Autowired
    DepartmenttypeRepository departmenttypeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WlqzWechatPubService wechatPubService;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonStateRepository personStateRepository;
    @Autowired
    XinGePushService  xinGePushService;
    @Autowired
    SmsService smsService;
    @Autowired
    DateTransform dateTransform;
    
    @Override
    @PostConstruct
    public void sqlInit() {
        long findAll = orderrzzlActRepository.count();
        if(findAll == 0 ){


            OrderrzzlAct save1 = orderrzzlActRepository.save(new OrderrzzlAct("创建","create",10,ActGroup.UPDATE));
            OrderrzzlAct save2 = orderrzzlActRepository.save(new OrderrzzlAct("读取","read",20,ActGroup.READ));
            OrderrzzlAct save3 = orderrzzlActRepository.save(new OrderrzzlAct("更新","update",30,ActGroup.UPDATE));
            OrderrzzlAct save4 = orderrzzlActRepository.save(new OrderrzzlAct("删除","delete",40,ActGroup.UPDATE));
            OrderrzzlAct save5 = orderrzzlActRepository.save(new OrderrzzlAct("自己的列表","listOwn",50,ActGroup.READ));
            OrderrzzlAct save6 = orderrzzlActRepository.save(new OrderrzzlAct("部门的列表","listOwnDepartment",60,ActGroup.READ));
            OrderrzzlAct save7 = orderrzzlActRepository.save(new OrderrzzlAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70,ActGroup.READ));
            OrderrzzlAct save8 = orderrzzlActRepository.save(new OrderrzzlAct("通知其他人","noticeOther",80,ActGroup.NOTICE));
            OrderrzzlAct save9 = orderrzzlActRepository.save(new OrderrzzlAct("通知操作者","noticeActUser",90,ActGroup.NOTICE));
            OrderrzzlAct save10 = orderrzzlActRepository.save(new OrderrzzlAct("通知展示人","noticeShowUser",100,ActGroup.NOTICE));


            OrderrzzlState newState =new OrderrzzlState("已创建",0,"CREATED");
            newState = orderrzzlStateRepository.save(newState);

            OrderrzzlState enableState = new OrderrzzlState("有效",100,"ENABLED");
            enableState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));
            orderrzzlStateRepository.save(enableState);
            orderrzzlStateRepository.save(new OrderrzzlState("无效",200,"DISABLED"));
            orderrzzlStateRepository.save(new OrderrzzlState("删除",300,"DELETED"));

        }

    }

    @Override
    public LogRepository<OrderrzzlLog> getLogRepository() {
        // TODO Auto-generated method stub
        return personLogRepository;
    }

    @Override
    public AuditorEntityRepository<Orderrzzl> getAuditorEntityRepository() {

        return orderrzzlRepository;
    }


    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Orderrzzl().setLogRepository(personLogRepository);

    }

    @Override
    public OrderrzzlLog getLogInstance() {
        // TODO Auto-generated method stub
        return new OrderrzzlLog();
    }

    @Override
    public ActRepository<OrderrzzlAct> getActRepository() {
        // TODO Auto-generated method stub
        return orderrzzlActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Orderrzzl().setService(this);

    }

    @Override
    public OrderrzzlFile getFileLogInstance() {
        // TODO Auto-generated method stub
        return new OrderrzzlFile();
    }
    /**
     * 提交申请动作  一次只能允许一次提交，待提交成功后才能进行下一次申请
     *
     * @param Orderrzzl
     */
    public void doCreate(Orderrzzl Orderrzzl) {
        Orderrzzl.setWorkflow(OrderrzzlWorkflowRepository.findOne(1));
        Orderrzzl.setOrderNo(ProductUtil.modify(Orderrzzl.getProduct().getLabel(), OrderTypeEnum.ORDER));
        Orderrzzl findByApplyMobile =orderrzzlRepository.findByApplyMobile(Orderrzzl.getApplyMobile());
        if (null != findByApplyMobile) {
            throw new FailReturnObject(6657, "融资租赁订单相同手机号" + Orderrzzl.getApplyMobile() + "已存在，请使用不同手机号", ReturnObject.Level.WARNING);
        }
        Orderrzzl findByApplyIdentityNo = orderrzzlRepository.findByApplyIdentityNo(Orderrzzl.getApplyIdentityNo());
        if (null != findByApplyIdentityNo) {
            throw new FailReturnObject(6657, "融资租赁订单相同身份证号" + Orderrzzl.getApplyIdentityNo() + "已存在，请使用不同身份证", ReturnObject.Level.WARNING);
        }
        Page<Orderrzzl> Orderrzzlss = orderrzzlRepository.listOwn(null);
        List<Orderrzzl> list = Orderrzzlss.getContent();

        if (Orderrzzlss != null && list.size() != 0) {
            for (Orderrzzl exitOrderwdsjsh : list) {

                if (!(exitOrderwdsjsh.isStateAdopt() || exitOrderwdsjsh.isStateEnable())) {
                    System.out.println(exitOrderwdsjsh.getApplyIdentityNo() + ":" + exitOrderwdsjsh.getState().getStateCode() + ":" + exitOrderwdsjsh.getId());
                    throw new FailReturnObject(199, "前一个申请还在审核中！", ReturnObject.Level.WARNING);
                }
            }
        }
        smsService.sendCommitMessage(Orderrzzl.getOrderNo(), "产品申请", "融资租赁");
    }
    @Override
    public OrderrzzlLog fillmultiWechatImageToLog(Orderrzzl entity, OrderrzzlLog log) {
        // TODO Auto-generated method stub
        String[] wechatFiles = entity.getWechatFiles();
        for (String fileName : wechatFiles) {
            OrderrzzlFile fileLogInstance = getFileLogInstance();
            fileLogInstance.setEntity(entity);
            fileLogInstance.setAct(log.getAct());
            if (entity.getSubcategory() != null) {
                fileLogInstance.setSubcategory(entity.getSubcategory());
            }
            if (entity.getTopcategory() != null) {
                fileLogInstance.setTopcategory(entity.getTopcategory());
            }
            if (CommonUtil.getCurrentUserDepartment() != null) {
                fileLogInstance.setUploaderDepartmenttype(CommonUtil.getCurrentUserDepartment().getDepartmenttype());
            }
            fileLogInstance.setOriginalFileName(String.format(IMAGE_URL_TEMPLATE,
                    wechatGetService.getCacheTokenTotal(), fileName));
            fileLogInstance.setUploadType("file");
            fileLogInstance.setLog(log);
            Departmenttype departmentType = departmenttypeRepository.findByDepartmenttypeCode(Departmenttype.DepartmenttypeCode.DEBTOR);
            fileLogInstance.setUploaderDepartmenttype(departmentType);
            OrderrzzlFile file = fileRepository.save(fileLogInstance);
            applicationContext.publishEvent(new WechatImageAsyncFetchEvent(entity, file));
        }
        return log;
    }
    public void doDistribution(Orderrzzl Orderrzzl) {
        User user = userRepository.findOne(Orderrzzl.getServiceId());
        if(user==null){
            throw new FailReturnObject(1643,"分配的人员不存在！", ReturnObject.Level.INFO);
        }
        Orderrzzl.setServiceName(user.getNickname());
        Orderrzzl.setServiceUser(user);
//      Orderrzzl.setDistribution(true);
        Orderrzzl.setIsDistribution(true);
        System.out.println("--------------" + Orderrzzl.getServiceId());
    }
    public void doFailed(Orderrzzl orderrzzl) {
        wechatPubService.pushOpenAccMessage(orderrzzl.getCreatedBy(), "审核未通过", orderrzzl.getRealName(), orderrzzl.getApplyMobile(), OPEN_ACC_APPLY, orderrzzl.getComment());
        String content = xinGePushService.getYiDaiFailedContent(orderrzzl.getProduct().getLabel());
        xinGePushService.pushAppMessage(orderrzzl.getCreatedBy(), orderrzzl.getProduct(), "审核进度通知", content,1);
    }
    public void doAdopt(Orderrzzl orderrzzl) {
        User user = orderrzzl.getCreatedBy();
        if (user == null) {
            throw new FailReturnObject(499, "没有创建人", ReturnObject.Level.WARNING);//lhg
        }

        System.out.println("doAdopt");

        String identity = orderrzzl.getApplyIdentityNo();
        Person person = personRepository.findByIdentity(identity);
        if (person == null) {
            person = new Person();
            person = wechatPubService.setPersonField(person, orderrzzl);
            person.setState(personStateRepository.findByStateCode("ENABLED"));
            person.setAccountStatus("NORMAL");
            wechatPubService.pushOpenAccMessage(user, "审核通过", person.getName(), person.getTelephone(), OPEN_ACC_APPLY, orderrzzl.getComment());
        }
        String content = xinGePushService.getYiDaiPassContent(orderrzzl.getProduct().getLabel());
        xinGePushService.pushAppMessage(user, orderrzzl.getProduct(), "审核进度通知", content,1);
        
        personRepository.save(person);
        orderrzzl.setUser(user);
        user.setPerson(person);
        userRepository.save(user);
        orderrzzl.setPerson(person);
    }
    
    
    //根据条件动态查询orderrzzl并分页
    public Page<Orderrzzl> getOrderrzzl(Orderrzzl orderrzzl,DateTransform dateTransform,String stateCode,Integer serviceUserId,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Orderrzzl> queryExpression = new GenericQueryExpSpecification<Orderrzzl>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("realName", orderrzzl.getRealName(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", orderrzzl.getApplyMobile(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("orderNo", orderrzzl.getOrderNo(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("serviceUser.id", serviceUserId, true));
    	queryExpression.add(QueryExpSpecificationBuilder.gt("createdAt", dateTransform.getCreateDateTime(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.lt("createdAt", dateTransform.getEndDateTime(), true));		
		
		return orderrzzlRepository.findAll(queryExpression,pageable);	
    }
    
    //根据条件动态查询orderrzzl并分页
    public Page<Orderrzzl> getCommissionerOrderrzzlList(Orderrzzl orderrzzl,DateTransform dateTransform,String stateCode,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Orderrzzl> queryExpression = new GenericQueryExpSpecification<Orderrzzl>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode",stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("realName", orderrzzl.getRealName(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", orderrzzl.getApplyMobile(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("serviceUser.id", orderrzzl.getServiceUser().getId(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("orderNo", orderrzzl.getOrderNo(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.gt("createdAt", dateTransform.getCreateDateTime(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.lt("createdAt", dateTransform.getEndDateTime(), true));		
		return orderrzzlRepository.findAll(queryExpression,pageable);	
    }

}
