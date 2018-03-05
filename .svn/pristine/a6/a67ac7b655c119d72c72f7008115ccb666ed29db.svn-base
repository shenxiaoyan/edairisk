package com.liyang.service;

import cn.tsign.ching.eSign.SignHelper;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.orderwdsjsh.OrderwdsjshFile;
import com.liyang.domain.person.Person;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserESign;
import com.liyang.domain.user.UserESignRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.SuccessReturnObject;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.service.AccountService;
import com.timevale.esign.sdk.tech.service.factory.AccountServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TSignService {

    @Value(value = "${aliyun.file.url}")
    String ossPrefix;
    @Autowired
    private UserESignRepository userESignRepository;
    @Autowired
    private FileUploadService fileUploadService;
    public TSignService() {
        SignHelper.initProject();
    }

    //根据user添加e签账户
    //
    public String addPersonAccount(User user) {
        UserESign account = user.getUserESign();

        if (account != null) {
            return account.getAccountId();
        }

        Person person =user.getPerson();
        PersonBean personBean = new PersonBean();
        personBean.setName(person.getName());
        // 身份证号/护照号
        personBean.setIdNo(person.getIdentity());
        // 个人归属地：0-大陆，1-香港，2-澳门，3-台湾，4-外籍，默认0
        personBean.setPersonArea(0);

        System.out.println("----开始创建个人账户...");
        AccountService accountService = AccountServiceFactory.instance();
        AddAccountResult addAccountResult = accountService.addAccount(personBean);
        if (0 != addAccountResult.getErrCode()) {
            throw new FailReturnObject(1423, "创建个人账户失败，errCode=" + addAccountResult.getErrCode() + " msg=" + addAccountResult.getMsg(), ReturnObject.Level.FATAL);
        } else {
            System.out.println("创建个人账户成功！accountId = " + addAccountResult.getAccountId());
        }
        UserESign eSign = new UserESign();
        eSign.setAccountId(addAccountResult.getAccountId());
        eSign.setUser(user);
        userESignRepository.save(eSign);
        return addAccountResult.getAccountId();
    }

    /**
     * 执行图片电子签
     *
     * @param sealDataBase64 图片编码 没有头
     * @return 阿里云新的文件名
     */
    public String doSignWithImageSealByStream(String pdfSrc, String eSignAccountID, String sealDataBase64,String username) {
        //  user.getESign().getAccount();
        //String userPersonAccountId = orderwdsjsh.getUser().getUserESign().getAccountId();

        if (eSignAccountID == null) {
            throw new FailReturnObject(1385, "ESignAccountID 不能为null", ReturnObject.Level.INFO);
        }
        if (pdfSrc == null) {
            throw new FailReturnObject(1245, "没有pdf文件", ReturnObject.Level.INFO);
        }
        //贵公司签署，签署方式：坐标定位,以文件流的方式传递pdf文档
        FileDigestSignResult platformSignResult = SignHelper.platformSignByStreamm(ossPrefix+pdfSrc);
        // 个人客户签署，签署方式：关键字定位,以文件流的方式传递pdf文档
        FileDigestSignResult userPersonSignResult = SignHelper.userPersonSignByStream(platformSignResult.getStream(),
                eSignAccountID, sealDataBase64);
        if (userPersonSignResult.getErrCode() != 0) {
            throw new FailReturnObject(2049, userPersonSignResult.getMsg(), ReturnObject.Level.FATAL);
        }
//        String date = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        SuccessReturnObject returnObject =fileUploadService.uploadSignPdf(new ByteArrayInputStream(userPersonSignResult.getStream()));
        return ((FileUploadService.OssFile)returnObject.getResult()).getNewFileName();
//        SignHelper.saveSignedByStream(userPersonSignResult.getStream(), "pdf/", date+username+".pdf");

    }
}
