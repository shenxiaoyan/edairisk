package com.liyang.service;

import static com.liyang.domain.capitalproduct.Capitalproduct.RepaymentMethodCode.BEFORE_INTEREST_AFTER_PRINCIPAL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.liyang.domain.ordercddloan.Ordercddloan;
import com.liyang.domain.ordercddloan.OrdercddloanAct;
import com.liyang.domain.ordercddloan.OrdercddloanActRepository;
import com.liyang.domain.ordercddloan.OrdercddloanFile;
import com.liyang.domain.ordercddloan.OrdercddloanLog;
import com.liyang.domain.ordercddloan.OrdercddloanLogRepository;
import com.liyang.domain.ordercddloan.OrdercddloanRepository;
import com.liyang.domain.ordercddloan.OrdercddloanState;
import com.liyang.domain.ordercddloan.OrdercddloanStateRepository;
import com.liyang.domain.ordercddloan.OrdercddloanWorkflow;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.domain.product.Product;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;

@Service
public class OrdercddloanService extends AbstractWorkflowService<Ordercddloan, OrdercddloanWorkflow, OrdercddloanAct, OrdercddloanState, OrdercddloanLog, OrdercddloanFile> {

    @Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
    private String OPEN_ACC_APPLY;

    @Autowired
    OrdercddloanLogRepository ordercddloanLogRepository;

    @Autowired
    OrdercddloanRepository ordercddloanRepository;

    @Autowired
    OrdercddloanActRepository ordercddloanActRepository;

    @Autowired
    OrdercddloanStateRepository ordercddloanStateRepository;

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
    @Override
    public void sqlInit() {
        
        	long findAll = ordercddloanActRepository.count();
	        if(findAll == 0 ){

        	OrdercddloanAct save1 = ordercddloanActRepository.save(new OrdercddloanAct("创建", "create", 10, ActGroup.UPDATE));
        	OrdercddloanAct save2 = ordercddloanActRepository.save(new OrdercddloanAct("读取", "read", 20, ActGroup.READ));
        	OrdercddloanAct save3 = ordercddloanActRepository.save(new OrdercddloanAct("更新", "update", 30, ActGroup.UPDATE));
        	OrdercddloanAct save4 = ordercddloanActRepository.save(new OrdercddloanAct("删除", "delete", 40, ActGroup.UPDATE));
        	OrdercddloanAct save5 = ordercddloanActRepository.save(new OrdercddloanAct("自己的列表", "listOwn", 50, ActGroup.READ));
        	OrdercddloanAct save6 = ordercddloanActRepository.save(new OrdercddloanAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
        	OrdercddloanAct save7 = ordercddloanActRepository.save(new OrdercddloanAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
        	OrdercddloanAct save8 = ordercddloanActRepository.save(new OrdercddloanAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
        	OrdercddloanAct save9 = ordercddloanActRepository.save(new OrdercddloanAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
        	OrdercddloanAct save10 = ordercddloanActRepository.save(new OrdercddloanAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));


            OrdercddloanState newState = new OrdercddloanState("已创建", 0, "CREATED");
            newState = ordercddloanStateRepository.save(newState);
            OrdercddloanState enableState = new OrdercddloanState("已放款", 100, "ENABLED");
            enableState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream().collect(Collectors.toSet()));
            ordercddloanStateRepository.save(enableState);
            ordercddloanStateRepository.save(new OrdercddloanState("无效", 200, "DISABLED"));
            ordercddloanStateRepository.save(new OrdercddloanState("删除", 300, "DELETED"));
           OrdercddloanState notLend = new OrdercddloanState("未放款",101,"NOTLEND");
           notLend.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));

        }
    }

    @Override
    public LogRepository<OrdercddloanLog> getLogRepository() {
        // TODO Auto-generated method stub
        return ordercddloanLogRepository;
    }

    @Override
    public AuditorEntityRepository<Ordercddloan> getAuditorEntityRepository() {

        return ordercddloanRepository;
    }


    @Override
    @PostConstruct
    public void injectLogRepository() {
        new Ordercdd().setLogRepository(ordercddloanLogRepository);

    }

    @Override
    public OrdercddloanLog getLogInstance() {
        // TODO Auto-generated method stub
        return new OrdercddloanLog();
    }

    @Override
    public ActRepository<OrdercddloanAct> getActRepository() {
        // TODO Auto-generated method stub
        return ordercddloanActRepository;
    }

    @Override
    @PostConstruct
    public void injectEntityService() {
        new Ordercddloan().setService(this);

    }

    @Override
    public OrdercddloanFile getFileLogInstance() {
        // TODO Auto-generated method stub
        return new OrdercddloanFile();
    }

    public void doDistribution(Ordercddloan ordercddloan) {
        User serviceUser = userRepository.findOne(ordercddloan.getServiceId());
        ordercddloan.setServiceName(serviceUser.getNickname());
        ordercddloan.setServiceUser(serviceUser);
        ordercddloan.setDistribution(true);
        System.out.println("--------------" + ordercddloan.getServiceId());
    }

    //
//    public void doSign(Ordercddloan ordercddloan) {
//
//     //1 授信
//        Creditcard creditcard = creditcardService
//                .findByCreditcardIdentity(ordercddloan.getApplyIdentityNo());
//        if (creditcard == null) {
//            creditcard = new Creditcard();
//            creditcard.setSort(new Random().nextInt(1000));
//            creditcard.setCreditcardIdentity(ordercddloan.getApplyIdentityNo());// 贷款人身份证号
//            creditcard.setCreditGrant(ordercddloan.getApplyAmount());// 额度
//            creditcard.setCreditBalance(ordercddloan.getApplyAmount());
//            creditcard.setPerson(personRepository.getByTelephoneAndStateCode(creditcard.getCreditcardIdentity(), "ENABLED"));
//            creditcard.setProduct(productRepository.findFirstByLabel("汽车抵押贷款"));///李斌说一定是 "汽车抵押贷款"
//            creditcardService.saveFromOrdercdd(creditcard, "签约成功");
//
//        }
//     //2 生成贷款
//
//        Product product=ordercddloan.getProduct();
//        Capitalproduct capitalproduct=product.getCapitalproduct();
//        Loan loan = new Loan();
//        loan.setState(loanStateRepository.findByStateCode("ENABLED"));//具体状态问项目经理
//        creditcard.setCreditBalance(BigDecimal.valueOf(0));
//        loan.setCreditcard(creditcard);//所属授信账户
//        loan.setRepaymentMethodCode(BEFORE_INTEREST_AFTER_PRINCIPAL);//先息后本
//        loan.setPrincipal(creditcard.getCreditGrant());//贷款本金
//        loan.setPeriodCode(capitalproduct.getPeriodCode());//贷款分期时间单位 类型冲突  为了统一类型，全部放在了loan.PeriodCode
//        loan.setPeriodNumber(capitalproduct.getPeriodNumber());//贷款分期期数
//        loan.setDebtorInterest(product.getDebtorInterest());//借方利率
//        loan.setStoreInterest(product.getStoreInterest());//给门店利率
//        loan.setCreditorInterest(capitalproduct.getCreditorInterest());//贷方供应利率
//        if(product.getRepaymentAccount()!=null){loan.setDebtorReceiveBankcard(product.getRepaymentAccount());}else {loan.setDebtorReceiveBankcard(capitalproduct.getCreditorRepaymentBankcard());}//借方收款账户
//        loan.setCreditorRepaymentDay(capitalproduct.getCreditorRepaymentDay());//还给资方的日期
//        // 借方还款的时间
//        loan.setDebtorExtentedRepaymentDays(capitalproduct.getOverdueGraceDays());//借方还款宽限日期数  --这里取的的资金产品的逾期天数，不知对不对
//        //说明
//        //内部备注
//        if(product.getPunishinterestrule()!=null){loan.setPunishinterestrule(product.getPunishinterestrule());}else {loan.setPunishinterestrule(capitalproduct.getPunishinterestrule());}//罚息规则
//        loan.setDebtorUser(ordercddloan.getUser());//贷款人
//        loan.setDebtorPerson(ordercddloan.getPerson());//贷款自然人
//        loan.setCreditorLoanSn(ordercddloan.getCddSn());//资方贷款单号
//        loan.setServiceUserName(ordercddloan.getServiceName());//业务员姓名
//        loan.setServiceUser(ordercddloan.getServiceUser());//业务员id
//
//
//
//
//    }
//
//    public void doCreate(Ordercddloan ordercddloan) {
//        userService.verifySmsCode(ordercddloan.getApplyMobile(), ordercddloan.getSmsCode());
//        Collection<String> noSuccessCode = new ArrayList<>();
//        noSuccessCode.add("CREATED");
//        noSuccessCode.add("PENDING");
//        noSuccessCode.add("MATCH");
//        noSuccessCode.add("AUDIT");
//        noSuccessCode.add("CONTRACT");
//        ///属于这些状态code的不能再申请
//
//        List<OrdercddloanState> states = ordercddloanStateRepository.findAllByStateCodeIn(noSuccessCode);
//        List<Ordercddloan> notSuccessOrdercdds = ordercddloanRepository.findAllByApplyMobileAndStateIn(ordercddloan.getApplyMobile(), states);
//        if (!notSuccessOrdercdds.isEmpty()) {
//            throw new FailReturnObject(1534, "存在没有签约的申请单，请先签约已存在的申请单", ReturnObject.Level.INFO);
//        }
//        User user = userRepository.findByUsername(ordercddloan.getApplyMobile());
//        if (user == null) {
//
//            user = userService.baseRegister(ordercddloan.getApplyMobile(), "123456", "APPLIED");
//            if (ordercddloan.getWechatCode() != null) {
//                wlqzWechatPubService.weixinBind(ordercddloan.getWechatCode(), user);
//                userRepository.save(user);
//            }
//        }
//
//        ordercddloan.setCreatedBy(user);
//        ordercddloan.setUser(user);
//        ordercddloan.setCddSn(UUID.randomUUID().toString().split("-")[4]);
//
//    }
//
//    public void doAdopt(Ordercddloan ordercddloan) {
//        User user = ordercddloan.getUser();
//        if (user == null) {
//            throw new FailReturnObject(499, "没有申请的用户", ReturnObject.Level.WARNING);//lhg
//        }
//
//
//        String identity = ordercddloan.getApplyIdentityNo();
//        if (identity == null) {
//            throw new FailReturnObject(1543,"身份证号不能为空,请补填信息", ReturnObject.Level.INFO);
//        }
//        Person person = personRepository.findByIdentity(identity);
//        if (person == null) {
//            person = new Person();
//            person = wechatPubService.setPersonField(person, ordercddloan);
//            person.setState(personStateRepository.findByStateCode("ENABLED"));
//            wechatPubService.pushOpenAccMessage(user, "通过初步筛选", person.getName(), person.getTelephone(), OPEN_ACC_APPLY, ordercddloan.getComment());
//        }
//        personRepository.save(person);
//        user.setPerson(person);
//        user.setState(userStateRepository.findByStateCode("ENABLED"));
//        userRepository.save(user);
//        ordercddloan.setPerson(person);
//
//
//    }
//
//    public void doFailed(Ordercddloan Ordercddloan) {
//        wechatPubService.pushOpenAccMessage(Ordercddloan.getCreatedBy(), "初步筛选未通过", Ordercddloan.getRealName(), Ordercddloan.getApplyMobile(), OPEN_ACC_APPLY, Ordercddloan.getComment());
//    }

    public void doDownloadFile(Ordercddloan Ordercddloan) {
        downloadAllFilesAnduploadToAliyun(Ordercddloan);
    }

}
