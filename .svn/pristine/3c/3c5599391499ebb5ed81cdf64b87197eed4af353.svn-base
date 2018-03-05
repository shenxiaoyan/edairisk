package com.liyang.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.department.Departmenttype;
import com.liyang.domain.department.Departmenttype.DepartmenttypeCode;
import com.liyang.domain.department.DepartmenttypeRepository;
import com.liyang.domain.ordersbdy.Ordersbdy;
import com.liyang.domain.ordersbdy.OrdersbdyAct;
import com.liyang.domain.ordersbdy.OrdersbdyActRepository;
import com.liyang.domain.ordersbdy.OrdersbdyFile;
import com.liyang.domain.ordersbdy.OrdersbdyLog;
import com.liyang.domain.ordersbdy.OrdersbdyLogRepository;
import com.liyang.domain.ordersbdy.OrdersbdyRepository;
import com.liyang.domain.ordersbdy.OrdersbdyState;
import com.liyang.domain.ordersbdy.OrdersbdyStateRepository;
import com.liyang.domain.ordersbdy.OrdersbdyAct;
import com.liyang.domain.ordersbdy.OrdersbdyActRepository;
import com.liyang.domain.ordersbdy.OrdersbdyWorkflow;
import com.liyang.domain.ordersbdy.OrdersbdyWorkflowRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonAct;
import com.liyang.domain.person.PersonActRepository;
import com.liyang.domain.person.PersonFile;
import com.liyang.domain.person.PersonLog;
import com.liyang.domain.person.PersonLogRepository;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonState;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.domain.person.PersonWorkflow;
import com.liyang.domain.person.PersonWorkflowRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserActRepository;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserLogRepository;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.domain.user.UserWorkflow;
import com.liyang.domain.user.UserWorkflowRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject.Level;
import com.liyang.util.WechatImageAsyncFetchEvent;

@Service
@Order(35)
public class OrdersbdyService extends AbstractWorkflowService<Ordersbdy, OrdersbdyWorkflow, OrdersbdyAct, OrdersbdyState, OrdersbdyLog, OrdersbdyFile> {

    @Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
    private String OPEN_ACC_APPLY;

    @Autowired
    OrdersbdyActRepository ordersbdyActRepository;

    @Autowired
    OrdersbdyStateRepository ordersbdyStateRepository;

    @Autowired
    OrdersbdyLogRepository ordersbdyLogRepository;

    @Autowired
    OrdersbdyRepository ordersbdyRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    WlqzWechatPubService wechatPubService;
    @Autowired
    OrdersbdyWorkflowRepository ordersbdyWorkflowRepository;
    @Autowired
    PersonStateRepository personStateRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmenttypeRepository departmenttypeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private TSignService tSignService;

    @Override
    @PostConstruct
    public void sqlInit() {
        
        	long findAll = ordersbdyActRepository.count();
	        if(findAll == 0 ){

            OrdersbdyAct save1 = ordersbdyActRepository.save(new OrdersbdyAct("创建", "create", 10, ActGroup.UPDATE));
            OrdersbdyAct save2 = ordersbdyActRepository.save(new OrdersbdyAct("读取", "read", 20, ActGroup.READ));
            OrdersbdyAct save3 = ordersbdyActRepository.save(new OrdersbdyAct("更新", "update", 30, ActGroup.UPDATE));
            OrdersbdyAct save4 = ordersbdyActRepository.save(new OrdersbdyAct("删除", "delete", 40, ActGroup.UPDATE));
            OrdersbdyAct save5 = ordersbdyActRepository.save(new OrdersbdyAct("自己的列表", "listOwn", 50, ActGroup.READ));
            OrdersbdyAct save6 = ordersbdyActRepository.save(new OrdersbdyAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
            OrdersbdyAct save7 = ordersbdyActRepository.save(new OrdersbdyAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
            OrdersbdyAct save8 = ordersbdyActRepository.save(new OrdersbdyAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
            OrdersbdyAct save9 = ordersbdyActRepository.save(new OrdersbdyAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
            OrdersbdyAct save10 = ordersbdyActRepository.save(new OrdersbdyAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));


            OrdersbdyState newState = new OrdersbdyState("已创建", 0, "CREATED");
            newState = ordersbdyStateRepository.save(newState);
            OrdersbdyState enableState = new OrdersbdyState("有效", 100, "ENABLED");
            enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream().collect(Collectors.toSet()));
            ordersbdyStateRepository.save(enableState);
            ordersbdyStateRepository.save(new OrdersbdyState("无效", 200, "DISABLED"));
            ordersbdyStateRepository.save(new OrdersbdyState("删除", 300, "DELETED"));

        }

    }

    @Override
    public LogRepository<OrdersbdyLog> getLogRepository() {
        // TODO Auto-generated method stub
        return ordersbdyLogRepository;
    }

    @Override
    public AuditorEntityRepository<Ordersbdy> getAuditorEntityRepository() {

        return ordersbdyRepository;
    }


    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Ordersbdy().setLogRepository(ordersbdyLogRepository);

    }

    @Override
    public OrdersbdyLog getLogInstance() {
        // TODO Auto-generated method stub
        return new OrdersbdyLog();
    }

    @Override
    public ActRepository<OrdersbdyAct> getActRepository() {
        // TODO Auto-generated method stub
        return ordersbdyActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Ordersbdy().setService(this);

    }

    @Override
    public OrdersbdyFile getFileLogInstance() {
        // TODO Auto-generated method stub
        return new OrdersbdyFile();
    }

//    public void changeStateNotpass(String telephone, String beidouComment) {
//        System.out.println("denited:" + telephone);
//        Ordersbdy ordersbdy = ordersbdyRepository.getByTelephoneAndStatCode(telephone, "ADOPT");
//        if (ordersbdy != null) {
//            ordersbdy.setBeidouComment(beidouComment);
//            ordersbdy.setState(ordersbdyStateRepository.findByStateCode("NOTPASS"));
//            ordersbdyRepository.save(ordersbdy);
//        }
//    }

    /**
     * 提交申请动作  一次只能允许一次提交，待提交成功后才能进行下一次申请
     *
     * @param ordersbdy
     */
//    public void doCreate(Ordersbdy ordersbdy) {
//        ordersbdy.setWorkflow(ordersbdyWorkflowRepository.findOne(1));
//
//        Ordersbdy findByApplyMobile = ordersbdyRepository.findByApplyMobile(ordersbdy.getApplyMobile());
//        if (null != findByApplyMobile) {
//            throw new FailReturnObject(6657, "设备抵押订单相同手机号" + ordersbdy.getApplyMobile() + "已存在，请使用不同手机号", Level.WARNING);
//        }
//        Ordersbdy findByApplyIdentityNo = ordersbdyRepository.findByApplyIdentityNo(ordersbdy.getApplyIdentityNo());
//        if (null != findByApplyIdentityNo) {
//            throw new FailReturnObject(6657, "设备抵押订单相同身份证号" + ordersbdy.getApplyIdentityNo() + "已存在，请使用不同身份证", Level.WARNING);
//        }
//
//        Page<Ordersbdy> ordersbdyes = ordersbdyRepository.listOwn(null);
//        List<Ordersbdy> list = ordersbdyes.getContent();
//
//        if (ordersbdyes != null && list.size() != 0) {
//            for (Ordersbdy exitOrdersbdy : list) {
//
//                if (!(exitOrdersbdy.isStateAdopt() || exitOrdersbdy.isStateEnable())) {
//                    System.out.println(exitOrdersbdy.getApplyIdentityNo() + ":" + exitOrdersbdy.getState().getStateCode() + ":" + exitOrdersbdy.getId());
//                    throw new FailReturnObject(199, "前一个申请还在审核中！", Level.WARNING);
//                }
//            }
//        }
//    }

    /**
     * 主管 为贷款人分配业务人员 notice id=1  将这个用户分配到id为1的业务员下
     *
     * @param
     */
//    public void doDistribution(Ordersbdy ordersbdy) {
//        ordersbdy.setServiceName(userRepository.findOne(ordersbdy.getServiceId()).getNickname());
//        ordersbdy.setServiceUser(userRepository.findOne(ordersbdy.getServiceId()));
//        ordersbdy.setDistribution(true);
//        System.out.println("--------------" + ordersbdy.getServiceId());
//    }

    /**
     * 通过背篼申请
     *
     * @param telephone
     */
//    public void adoptApply(String telephone) {
//        Ordersbdy ordersbdy = ordersbdyRepository.getByTelephoneAndStatCode(telephone, "SIGNED");
//
//        if (ordersbdy == null) {
//            ordersbdy = ordersbdyRepository.getByTelephoneAndStatCode(telephone, "DENIED");
//
//        }
//        System.out.println(ordersbdy + ":" + telephone);
//        if (ordersbdy != null) {
//            ordersbdy.setState(ordersbdyStateRepository.findByStateCode("ENABLED"));
//            System.out.println(ordersbdy.getState().getStateCode());
//            ordersbdyRepository.save(ordersbdy);
//        }
//    }

    /**
     * 一个微信用户只能有一个person  身份证是唯一标识
     * 一次只能允许一次提交，待提交成功后才能进行下一次申请
     * <p>
     * 申请通过我方后建立person 与 orderWdsjs，user建立关联
     */
//    public void doAdopt(Ordersbdy ordersbdy) {
//        User user = ordersbdy.getCreatedBy();
//        if (user == null) {
//            throw new FailReturnObject(499, "没有创建人", Level.WARNING);//lhg
//        }
//
//        System.out.println("doAdopt");
//
////        List<User> list = userRepository.findByUnionid(user.getUnionid());
////        if (list != null && list.size() > 0) {
////            for (User exitUser : list) {
////                if (exitUser.getPerson() != null) {
////                    user.setPerson(exitUser.getPerson());
////                    ordersbdy.setPerson(exitUser.getPerson());
////                    return;
////                }
////            }
////        }
//        String identity = ordersbdy.getApplyIdentityNo();
//        Person person = personRepository.findByIdentity(identity);
//        if (person == null) {
//            person = new Person();
//            person = wechatPubService.setPersonField(person, ordersbdy);
//
//            person.setState(personStateRepository.findByStateCode("ENABLED"));
//            wechatPubService.pushOpenAccMessage(user, "通过初步筛选", person.getName(), person.getTelephone(), OPEN_ACC_APPLY, ordersbdy.getComment());
//        }
//        personRepository.save(person);
//        ordersbdy.setUser(user);
//        user.setPerson(person);
//        userRepository.save(user);
//        ordersbdy.setPerson(person);
//    }

//    public void doFailed(Ordersbdy ordersbdy) {
//        wechatPubService.pushOpenAccMessage(ordersbdy.getCreatedBy(), "初步筛选未通过", ordersbdy.getRealName(), ordersbdy.getApplyMobile(), OPEN_ACC_APPLY, ordersbdy.getComment());
//    }

    public void doDownloadFile(Ordersbdy ordersbdy) {
        downloadAllFilesAnduploadToAliyun(ordersbdy);
    }

    @Override
    public OrdersbdyLog fillmultiWechatImageToLog(Ordersbdy entity, OrdersbdyLog log) {
        // TODO Auto-generated method stub
        String[] wechatFiles = entity.getWechatFiles();
        for (String fileName : wechatFiles) {
            OrdersbdyFile fileLogInstance = getFileLogInstance();
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
            Departmenttype departmentType = departmenttypeRepository.findByDepartmenttypeCode(DepartmenttypeCode.DEBTOR);
            fileLogInstance.setUploaderDepartmenttype(departmentType);
            OrdersbdyFile file = fileRepository.save(fileLogInstance);
            applicationContext.publishEvent(new WechatImageAsyncFetchEvent(entity, file));
        }
        return log;
    }

//    public void doChangeid(Ordersbdy ordersbdy) {
//        if (ordersbdyRepository.findByApplyIdentityNo(ordersbdy.getApplyIdentityNo()) != null) {
//            throw new FailReturnObject(4561, "身份证号已经存在", Level.INFO);
//        }
//
//        Person person = ordersbdy.getPerson();
//        if (person != null) {
//            person.setIdentity(ordersbdy.getApplyIdentityNo());
//            personRepository.save(person);
//        }
//    }

//    public void doSign(Ordersbdy ordersbdy) {
//        String accountId = tSignService.addPersonAccount(ordersbdy.getUser());
//        String ossFile = tSignService.doSignWithImageSealByStream(ordersbdy.getPdfName(), accountId, ordersbdy.getSignData(), ordersbdy.getPerson().getName());
//        ordersbdy.setPdfName(ossFile);
//    }

}
