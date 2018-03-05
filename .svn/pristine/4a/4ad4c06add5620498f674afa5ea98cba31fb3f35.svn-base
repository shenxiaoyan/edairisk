package com.liyang.service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.liyang.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.capitalproduct.Capitalproduct;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.loan.Loan;
import com.liyang.domain.loan.LoanStateRepository;
import com.liyang.domain.ordercdd.Ordercdd;
import com.liyang.domain.ordercdd.OrdercddAct;
import com.liyang.domain.ordercdd.OrdercddActRepository;
import com.liyang.domain.ordercdd.OrdercddFile;
import com.liyang.domain.ordercdd.OrdercddLog;
import com.liyang.domain.ordercdd.OrdercddLogRepository;
import com.liyang.domain.ordercdd.OrdercddRepository;
import com.liyang.domain.ordercdd.OrdercddState;
import com.liyang.domain.ordercdd.OrdercddStateRepository;
import com.liyang.domain.ordercdd.OrdercddWorkflow;
import com.liyang.domain.ordercddloan.Ordercddloan;
import com.liyang.domain.ordercddloan.OrdercddloanFile;
import com.liyang.domain.ordercddloan.OrdercddloanFileRepository;
import com.liyang.domain.ordercddloan.OrdercddloanRepository;
import com.liyang.domain.ordercddloan.OrdercddloanState;
import com.liyang.domain.ordercddloan.OrdercddloanStateRepository;
import com.liyang.domain.ordercddloan.OrdercddloanWorkflow;
import com.liyang.domain.ordercddloan.OrdercddloanWorkflowRepository;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.domain.product.Product;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import static com.liyang.domain.capitalproduct.Capitalproduct.RepaymentMethodCode.BEFORE_INTEREST_AFTER_PRINCIPAL;

@Service
public class OrdercddService extends AbstractWorkflowService<Ordercdd, OrdercddWorkflow, OrdercddAct, OrdercddState, OrdercddLog, OrdercddFile> {

    @Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
    private String OPEN_ACC_APPLY;

    @Autowired
    OrdercddLogRepository ordercddLogRepository;

    @Autowired
    OrdercddRepository ordercddRepository;

    @Autowired
    OrdercddActRepository ordercddActRepository;

    @Autowired
    OrdercddStateRepository ordercddStateRepository;
    
    @Autowired
    OrdercddloanStateRepository ordercddloanStateRepository;
    
    @Autowired
    OrdercddloanWorkflowRepository ordercddloanWorkflowRepository;
    
    @Autowired
    OrdercddloanFileRepository ordercddloanFileRepository;
    
    @Autowired
    OrdercddloanRepository ordercddloanRespository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    WlqzWechatPubService wechatPubService;
    @Autowired
    PersonStateRepository personStateRepository;
    @Autowired
    UserService userService;
    @Autowired
    WlqzWechatPubService wlqzWechatPubService;
    @Autowired
    CreditcardService creditcardService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserStateRepository userStateRepository;
    @Autowired
    LoanStateRepository loanStateRepository;
    @Autowired
    LoanService loanService;
    @Autowired
    CacheManager cacheManager;
    @Override
    @PostConstruct
    public void sqlInit() {
        
        	long findAll = ordercddActRepository.count();
	        if(findAll == 0 ){

            OrdercddAct save1 = ordercddActRepository.save(new OrdercddAct("创建", "create", 10, ActGroup.UPDATE));
            OrdercddAct save2 = ordercddActRepository.save(new OrdercddAct("读取", "read", 20, ActGroup.READ));
            OrdercddAct save3 = ordercddActRepository.save(new OrdercddAct("更新", "update", 30, ActGroup.UPDATE));
            OrdercddAct save4 = ordercddActRepository.save(new OrdercddAct("删除", "delete", 40, ActGroup.UPDATE));
            OrdercddAct save5 = ordercddActRepository.save(new OrdercddAct("自己的列表", "listOwn", 50, ActGroup.READ));
            OrdercddAct save6 = ordercddActRepository.save(new OrdercddAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
            OrdercddAct save7 = ordercddActRepository.save(new OrdercddAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
            OrdercddAct save8 = ordercddActRepository.save(new OrdercddAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
            OrdercddAct save9 = ordercddActRepository.save(new OrdercddAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
            OrdercddAct save10 = ordercddActRepository.save(new OrdercddAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));


            OrdercddState newState = new OrdercddState("已创建", 0, "CREATED");
            newState = ordercddStateRepository.save(newState);
            OrdercddState enableState = new OrdercddState("已放款", 100, "ENABLED");
            enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream().collect(Collectors.toSet()));
            ordercddStateRepository.save(enableState);
            ordercddStateRepository.save(new OrdercddState("无效", 200, "DISABLED"));
            ordercddStateRepository.save(new OrdercddState("删除", 300, "DELETED"));
           OrdercddState notLend = new OrdercddState("未放款",101,"NOTLEND");
           notLend.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));

        }
    }

    @Override
    public LogRepository<OrdercddLog> getLogRepository() {
        // TODO Auto-generated method stub
        return ordercddLogRepository;
    }

    @Override
    public AuditorEntityRepository<Ordercdd> getAuditorEntityRepository() {

        return ordercddRepository;
    }


    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Ordercdd().setLogRepository(ordercddLogRepository);

    }

    @Override
    public OrdercddLog getLogInstance() {
        // TODO Auto-generated method stub
        return new OrdercddLog();
    }

    @Override
    public ActRepository<OrdercddAct> getActRepository() {
        // TODO Auto-generated method stub
        return ordercddActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Ordercdd().setService(this);

    }

    @Override
    public OrdercddFile getFileLogInstance() {
        // TODO Auto-generated method stub
        return new OrdercddFile();
    }

    public void doDistribution(Ordercdd ordercdd) {
        User serviceUser = userRepository.findOne(ordercdd.getServiceId());
        ordercdd.setServiceName(serviceUser.getNickname());
        ordercdd.setServiceUser(serviceUser);
        ordercdd.setDistribution(true);
        System.out.println("--------------" + ordercdd.getServiceId());
    }

    //
    public void doSign(Ordercdd ordercdd) {
        //拷贝数据到ordercddloan
        copytoOrdercddloan(ordercdd);
    }
    
    private void copytoOrdercddloan(Ordercdd ordercdd) {
    	if (ordercdd==null) {
			return;
		}
    	Ordercddloan cddloan = new Ordercddloan();
        OrdercddloanState cddloanState = ordercddloanStateRepository.findOne(22);//确定的状态，写死
        cddloan.setState(cddloanState);
        OrdercddloanWorkflow cddloanWorkflow = ordercddloanWorkflowRepository.findOne(4);//确定的工作流，写死
        cddloan.setWorkflow(cddloanWorkflow);
        cddloan.setLabel(ordercdd.getLabel());
        cddloan.setApplyAmount(ordercdd.getApplyAmount());
        cddloan.setApplyAnnualSurveyMaturity(ordercdd.getApplyAnnualSurveyMaturity());
        cddloan.setApplyEnterpriseProvince(ordercdd.getApplyEnterpriseProvince());
        cddloan.setApplyIdentityNo(ordercdd.getApplyIdentityNo());
        cddloan.setApplyMobile(ordercdd.getApplyMobile());
        cddloan.setApplyPeriodNumber(ordercdd.getApplyPeriodNumber());
        cddloan.setApplyPlateNumber(ordercdd.getApplyPlateNumber());
        cddloan.setApplyRegisterReigionName(ordercdd.getApplyRegisterReigionName());
        cddloan.setApplyVehicleModel(ordercdd.getApplyVehicleModel());
        cddloan.setBirthday(ordercdd.getBirthday());
        cddloan.setCompanyAddress(ordercdd.getCompanyAddress());
        cddloan.setCompanyName(ordercdd.getCompanyName());
        cddloan.setCompanyProvince(ordercdd.getCompanyProvince());
        cddloan.setCompanyTelephone(ordercdd.getCompanyTelephone());
        cddloan.setEmail(ordercdd.getEmail());
//        cddloan.setMaritalStatus(ordercdd.getMaritalStatus());
        cddloan.setPersonalAddress(ordercdd.getPersonalAddress());
        cddloan.setRealName(ordercdd.getRealName());
        cddloan.setSex(ordercdd.getSex());
        cddloan.setCreatedBy(CommonUtil.getPrincipal());
        cddloan.setCreatedByDepartment(CommonUtil.getCurrentUserDepartment());
        cddloan.setDebtorCreditcard(ordercdd.getDebtorCreditcard());
        cddloan.setPerson(ordercdd.getPerson());
        cddloan.setProduct(ordercdd.getProduct());
        cddloan.setUser(ordercdd.getUser());
        cddloan.setServiceUser(ordercdd.getServiceUser());
        cddloan.setDistribution(ordercdd.getDistribution());
        cddloan.setComment(ordercdd.getComment());
        cddloan.setServiceName(ordercdd.getServiceName());
        cddloan.setCddSn(ordercdd.getCddSn());
        cddloan.setOrdercdd(ordercdd);
        Ordercddloan save = ordercddloanRespository.save(cddloan);
        Set<OrdercddFile> list = ordercdd.getFiles();
        if (list!=null) {
        	Set<OrdercddloanFile> cddloanFilelist = new HashSet<>();
			for (OrdercddFile file : list) {
				OrdercddloanFile cddloanFile = new OrdercddloanFile();
				cddloanFile.setLabel(file.getLabel());
				cddloanFile.setLastModifiedBy(CommonUtil.getPrincipal());
				cddloanFile.setAppCode(file.getAppCode());
				cddloanFile.setClient(file.getClient());
				cddloanFile.setFileSize(file.getFileSize());
				cddloanFile.setFileType(file.getFileType());
				cddloanFile.setImei(file.getImei());
				cddloanFile.setIp(file.getIp());
				cddloanFile.setLargeImage(file.getLargeImage());
				cddloanFile.setMiddleImage(file.getMiddleImage());
				cddloanFile.setNewFileName(file.getNewFileName());
				cddloanFile.setOriginalFileName(file.getOriginalFileName());
				cddloanFile.setSmallImage(file.getSmallImage());
				cddloanFile.setSubcategory(file.getSubcategory());
				cddloanFile.setTopcategory(file.getTopcategory());
				cddloanFile.setUploadType(file.getUploadType());
				cddloanFile.setCreatedBy(CommonUtil.getPrincipal());
				cddloanFile.setCreatedByDepartment(CommonUtil.getCurrentUserDepartment());
				cddloanFile.setActCode(file.getActCode());
				cddloanFile.setEntity(save);
				//没有日志关联
				cddloanFilelist.add(cddloanFile);
			}
			Set<OrdercddloanFile> saveFile = new HashSet<>();
			saveFile.addAll(ordercddloanFileRepository.save(cddloanFilelist));
			cddloan.setFiles(saveFile);
			ordercddloanRespository.save(cddloan);
		}
        		
    }

    @Transactional
    public void doCreate(Ordercdd ordercdd) {
        userService.verifySmsCode(ordercdd.getApplyMobile(), ordercdd.getSmsCode());
        Collection<String> noSuccessCode = new ArrayList<>();
        noSuccessCode.add("CREATED");
        noSuccessCode.add("PENDING");
        noSuccessCode.add("MATCH");
        noSuccessCode.add("AUDIT");
        noSuccessCode.add("CONTRACT");
        ///属于这些状态code的不能再申请

        List<OrdercddState> states = ordercddStateRepository.findAllByStateCodeIn(noSuccessCode);
        List<Ordercdd> notSuccessOrdercdds = ordercddRepository.findAllByApplyMobileAndStateIn(ordercdd.getApplyMobile(), states);
        if (!notSuccessOrdercdds.isEmpty()) {
            throw new FailReturnObject(1534, "存在没有签约的申请单，请先签约已存在的申请单", ReturnObject.Level.INFO);
        }
        User user = userRepository.findByUsername(ordercdd.getApplyMobile());
        if (user == null) {

            user = userService.baseRegister(ordercdd.getApplyMobile(), "123456", "APPLIED");
            if (ordercdd.getWechatCode() != null) {
                wlqzWechatPubService.weixinBind(ordercdd.getWechatCode(), user);
                userRepository.save(user);
            }
        }

        ordercdd.setCreatedBy(user);
        ordercdd.setUser(user);
        ordercdd.setCddSn(OrderUtil.generateSn());
        ordercdd.setProduct(productRepository.findFirstByLabel("汽车抵押贷款"));
        wechatPubService.pushOpenAccMessage(ordercdd.getUser(), "申请提交成功", ordercdd.getRealName(), ordercdd.getApplyMobile(),null,ordercdd.getComment());

    }

    @Transactional
    public void doAdopt(Ordercdd ordercdd) {
        User user = ordercdd.getUser();
        if (user == null) {
            throw new FailReturnObject(1499, "没有申请的用户", ReturnObject.Level.WARNING);//lhg
        }


        String identity = ordercdd.getApplyIdentityNo();
        if (identity == null) {
            throw new FailReturnObject(1543,"身份证号不能为空,请补填信息", ReturnObject.Level.INFO);
        }
        Person person = personRepository.findByIdentity(identity);
        if (person == null) {
            person = new Person();
            person = wechatPubService.setPersonField(person, ordercdd);
            person.setState(personStateRepository.findByStateCode("ENABLED"));
            wechatPubService.pushOpenAccMessage(user, "已同意申请", person.getName(), person.getTelephone(), null, ordercdd.getComment());
        }
        personRepository.save(person);
        user.setPerson(person);
        user.setState(userStateRepository.findByStateCode("ENABLED"));
        userRepository.save(user);
        ordercdd.setPerson(person);

        //1 授信
        Creditcard creditcard = creditcardService
                .findByCreditcardIdentity(ordercdd.getApplyIdentityNo());
        if (creditcard == null) {
            creditcard = new Creditcard();
            creditcard.setSort(new Random().nextInt(1000));
            creditcard.setCreditcardIdentity(ordercdd.getApplyIdentityNo());// 贷款人身份证号
            creditcard.setCreditGrant(ordercdd.getApplyAmount());// 额度
            creditcard.setCreditBalance(ordercdd.getApplyAmount());
            creditcard.setPerson(personRepository.getByTelephoneAndStateCode(creditcard.getCreditcardIdentity(), "ENABLED"));
            creditcard.setProduct(productRepository.findFirstByLabel("汽车抵押贷款"));///李斌说一定是 "汽车抵押贷款"
            creditcardService.saveFromOrdercdd(creditcard, "已同意");

        }
        //2 生成贷款
        loanService.save(ordercdd,creditcard);
    }

    public void doFailed(Ordercdd Ordercdd) {
        wechatPubService.pushOpenAccMessage(Ordercdd.getUser(), "申请被驳回", Ordercdd.getRealName(), Ordercdd.getApplyMobile(), null, Ordercdd.getComment());
    }

    public void doDownloadfile(Ordercdd ordercdd) {
        System.out.println("握草--------------------------------------");
        downloadAllFilesAnduploadToAliyun(ordercdd);
    }

    public List<Ordercdd> getNotCloneByServiceUser(Integer serviceUserId,List<OrdercddState> states)//每个客户经理未结束的CDD订单
    {

        User serviceUser = userRepository.findOne(serviceUserId);
        if(serviceUser==null){
            throw new FailReturnObject(1623,"客户经理不存在!", ReturnObject.Level.INFO);
        }


        return ordercddRepository.findAllByServiceUserAndStateIn(serviceUser,states);
    }
//    @Cacheable(value = "ordercddNoCloneCodes",key = "'0'")
    public List<OrdercddState> getNoCloneCode()
    {
        System.out.println("---------noCloneCodes");
        Collection<String> noCloneCode = new ArrayList<>();
        noCloneCode.add("PENDING");//待提交
        noCloneCode.add("MATCH");//待匹配
        noCloneCode.add("AUDIT");//待审核
        noCloneCode.add("CONTRACT");//待签约
      return ordercddStateRepository.findAllByStateCodeIn(noCloneCode);
    }

}
