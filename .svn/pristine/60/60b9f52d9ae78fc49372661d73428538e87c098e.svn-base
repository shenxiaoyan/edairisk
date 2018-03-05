package com.liyang.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.liyang.domain.role.RoleRepository;
import org.apache.el.lang.EvaluationContext;
import org.apache.http.HttpRequest;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.config.TIMKey;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.Departmenttype;
import com.liyang.domain.exception.Exception;
import com.liyang.domain.exception.ExceptionRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.message.CustomContent;
import com.liyang.message.CustomContent.Ext;
import com.liyang.message.Message;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.ReturnObjectImpl;

@Service
public class TIMService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ExceptionRepository exceptionRepository;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    RoleRepository roleRepository;

    private String ver = "V4";
    private long sdkappid = TIMKey.sdkAppId;
    private String identifier = TIMKey.identifier;
    private String usersig = TIMKey.usersig;
    private int random = 9999;
    private String contenttype = "json";

    private String urlTemplate = "https://console.tim.qq.com/" + ver + "/%s/%s?sdkappid=" + sdkappid + "&identifier=" + identifier + "&usersig=" + usersig + "&random=%d&contenttype=json";
//	StringBuilder sb = new StringBuilder();
//	private String urlTemplate = sb.append("https://console.tim.qq.com/") .append(ver) .append("/%s/%s?sdkappid=")
//	.append(sdkappid).append("&identifier=").append(identifier).append("&usersig=").append(usersig).append("&random=%d&contenttype=json").toString();

    public void doNotice(User fromUser, AbstractAuditorEntity entity, AbstractAuditorAct act) {
        if (request.getParameter("notice") != null && !"".equals(request.getParameter("notice"))) {
            entity.setNotice(request.getParameter("notice"));
        }

        if (act.getNoticeSelfTemplate() != null && !act.getNoticeSelfTemplate().trim().equals("")) {
            AbstractAuditorAct actNotice = entity.getService().getActRepository().findByActCode("noticeActUser");
            if (actNotice == null) {
                throw new FailReturnObject(1800, "noticeActUser动作不存在", com.liyang.util.ReturnObject.Level.FATAL);
            }
            String sendCustomElemMessage = sendCustomElemMessage(CommonUtil.getPrincipal(), entity, act,
                    act.getNoticeSelfTemplate());
            if (sendCustomElemMessage != null) {

                noticeLog(CommonUtil.getPrincipal(), entity, actNotice, sendCustomElemMessage);
            }
        }
        if (act.getNoticeShowTemplate() != null && !act.getNoticeShowTemplate().trim().equals("")) {
            AbstractAuditorAct actNotice = entity.getService().getActRepository().findByActCode("noticeShowUser");
            if (actNotice == null) {
                throw new FailReturnObject(1810, "noticeShowUser动作不存在", com.liyang.util.ReturnObject.Level.ERROR);
            }
            String sendCustomElemMessage = sendCustomElemMessage(fromUser, entity, act, act.getNoticeShowTemplate());
            if (sendCustomElemMessage != null) {
                noticeLog(fromUser, entity, actNotice, sendCustomElemMessage);
            }
        }
        if (act.getNoticeOtherTemplate() != null && !act.getNoticeOtherTemplate().trim().equals("")) {
            AbstractAuditorAct actNotice = entity.getService().getActRepository().findByActCode("noticeOther");
            if (actNotice == null) {
                throw new FailReturnObject(1820, "noticeOther动作不存在", com.liyang.util.ReturnObject.Level.FATAL);
            }
            if (entity.getNotice() != null && !entity.getNotice().equals("")) {

                String[] split = entity.getNotice().split(",");

                for (String u : split) {
                    //User findByUnionid = userRepository.findByUnionid(u);
                    User findOne = userRepository.findOne(Integer.valueOf(u));//unionid 该 openid
                    String sendCustomElemMessage = sendCustomElemMessage(findOne, entity, act,
                            act.getNoticeOtherTemplate());
                    if (sendCustomElemMessage != null) {
                        noticeLog(findOne, entity, actNotice, sendCustomElemMessage);
                    }
                }
            }
//            if (act.getNoticeDepartmenttype() != null && act.getNoticeRole() != null) {
            if (act.getNoticeDepartmenttype() != null && act.getNoticeRoles() != null) {

//                List<User> users = walkByDepartmentTypeAndRole(entity, act.getNoticeDepartmenttype(), act.getNoticeRole());
                List<User> users = walkByDepartmentTypeAndRole(entity, act.getNoticeDepartmenttype(), act.getNoticeRoles());

                if (users == null || users.isEmpty()) {
                    Exception exception = new Exception();
                    exception.setActionStatus("FAIL");
                    exception.setErrorCode(3102);
                    StringBuilder sb2 = new StringBuilder();
                    exception.setErrorInfo(sb2.append(entity.getClass().getSimpleName()).append("的动作").append(act.getLabel()).append("的自动通知没有用户接收").toString());
                    exceptionRepository.save(exception);

                } else {
                    for (User user : users) {
                        String sendCustomElemMessage = sendCustomElemMessage(user, entity, act,
                                act.getNoticeOtherTemplate());
                        if (sendCustomElemMessage != null) {
                        	//通知消息插入到日志表
                            noticeLog(user, entity, actNotice, sendCustomElemMessage);
                        }
                    }
                }
            }
        }
    }


//    private List<User> walkByDepartmentTypeAndRole(AbstractAuditorEntity entity, Departmenttype noticeDepartmentType,
//                                                   Role noticeRole) {
//
//        if (entity.getCreatedByDepartment() != null) {
//            Department department = searchDepartment(entity.getCreatedByDepartment(), noticeDepartmentType);
//            if (department == null) {
//                Exception exception = new Exception();
//                exception.setActionStatus("FAIL");
//                exception.setErrorCode(3114);
//                StringBuilder sb3 = new StringBuilder();
//                exception.setErrorInfo(sb3.append(entity.getClass().getSimpleName()).append(entity.toString()).append("通知的部门类型")
//                        .append(noticeDepartmentType.getDepartmenttypeCode()).append("没有部门").toString());
//                exceptionRepository.save(exception);
//            } else {
//                if (!department.getDepartmenttype().getRoles().contains(noticeRole)) {
//                    Exception exception = new Exception();
//                    exception.setActionStatus("FAIL");
//                    exception.setErrorCode(3116);
//                    StringBuilder sb4 = new StringBuilder();
//                    exception.setErrorInfo(sb4.append("通知的部门类型").append(noticeDepartmentType.getDepartmenttypeCode()).append("没有角色")
//                            .append(noticeRole.getRoleCode()).toString());
//                    exceptionRepository.save(exception);
//                } else {
//                    return userRepository.findByDepartmentAndRole(department, noticeRole);
//                }
//            }
//        } else {
//            Exception exception = new Exception();
//            exception.setActionStatus("FAIL");
//            exception.setErrorCode(3112);
//            StringBuilder sb6 = new StringBuilder();
//            exception.setErrorInfo(sb6.append(entity.getClass().getSimpleName()).append(":").append(entity.toString()).append("没有创建部门").toString());
//            exceptionRepository.save(exception);
//        }
//
//
//        return null;
//    }

    private List<User> walkByDepartmentTypeAndRole(AbstractAuditorEntity entity, Departmenttype noticeDepartmentType,
                                                   String noticeRoleStr) {

        if (entity.getCreatedByDepartment() != null) {
            Department department = searchDepartment(entity.getCreatedByDepartment(), noticeDepartmentType);
            if (department == null) {
                Exception exception = new Exception();
                exception.setActionStatus("FAIL");
                exception.setErrorCode(3114);
                StringBuilder sb3 = new StringBuilder();
                exception.setErrorInfo(sb3.append(entity.getClass().getSimpleName()).append(entity.toString()).append("通知的部门类型")
                        .append(noticeDepartmentType.getDepartmenttypeCode()).append("没有部门").toString());
                exceptionRepository.save(exception);
            } else {
            	//此部门类型下角色
                Set<Role> deptRoles = department.getDepartmenttype().getRoles();
                //通知的角色
                String []noticeRoleStrArr = noticeRoleStr.split("-");
                List<Integer> roleIdList=new ArrayList<>();
                for (String noticeRoleID:noticeRoleStrArr){
                    roleIdList.add(Integer.valueOf(noticeRoleID));
                }
                List<Role> noticeRoles =roleRepository.findAllByIdIn(roleIdList);
                noticeRoles.retainAll(deptRoles);
                if (noticeRoles.isEmpty()) {
                    StringBuilder roleCodes=new StringBuilder();
                    noticeRoles.forEach(item->{
                        roleCodes.append(item.getRoleCode()).append(',');
                    });
                    Exception exception = new Exception();
                    exception.setActionStatus("FAIL");
                    exception.setErrorCode(3116);
                    StringBuilder sb4 = new StringBuilder();
                    exception.setErrorInfo(sb4.append("通知的部门类型").append(noticeDepartmentType.getDepartmenttypeCode()).append("没有角色")
                            .append(roleCodes).toString());
                    exceptionRepository.save(exception);
                } else {
                    return userRepository.findAllByDepartmentAndRoleIn(department, noticeRoles);
                }
            }
        } else {
            Exception exception = new Exception();
            exception.setActionStatus("FAIL");
            exception.setErrorCode(3112);
            StringBuilder sb6 = new StringBuilder();
            exception.setErrorInfo(sb6.append(entity.getClass().getSimpleName()).append(":").append(entity.toString()).append("没有创建部门").toString());
            exceptionRepository.save(exception);
        }


        return null;
    }


    private Department searchDepartment(Department createdByDepartment, Departmenttype noticeDepartmentType) {
        if (createdByDepartment.getDepartmenttype().equals(noticeDepartmentType)) {
            return createdByDepartment;
        } else {
            if (createdByDepartment.getParent() != null) {
                return searchDepartment(createdByDepartment.getParent(), noticeDepartmentType);
            }
            return null;
        }
    }


    private void noticeLog(User user, AbstractAuditorEntity entity, AbstractAuditorAct act, String message) {
        AbstractAuditorLog logInstance = entity.getLogInstance();
        logInstance.setAct(act);
        logInstance.setActGroup(act.getActGroup());
        logInstance.setEntity(entity);
        logInstance.setLabel(act.getLabel());
        logInstance.setNoticeTo(user);
        logInstance.setNotice(message);
        entity.getLogRepository().save(logInstance);
    }

    public String sendCustomElemMessage(User user, AbstractAuditorEntity entity, AbstractAuditorAct act,
                                        String template) {
        if (act.getMessageSender() == null) {
            throw new FailReturnObject(6361, "发送的账户不存在", com.liyang.util.ReturnObject.Level.ERROR);
        }


        if (user != null) {
        	//string.format() 第一个参数为字符串格式，后面的参数可以任意多个，用于填充第一个参数中的格式控制符，最后返回完整的格式化后的字符串。
            String url = String.format(urlTemplate, "openim", "sendmsg", new Random().nextInt(700000000));
            String dataFromTemplate = getDataFromTemplate(user, entity, act, template);
            if (dataFromTemplate == null) {
                throw new FailReturnObject(1900, "没有设置行为消息模板", com.liyang.util.ReturnObject.Level.FATAL);
            }

            Ext ext = new CustomContent.Ext();
            ext.setEntityId(entity.getId());
            ext.setEntityName(entity.getClass().getSimpleName());
            ext.setType("notice");
            if (entity instanceof AbstractWorkflowEntity) {
                ext.setEntityType("workflowEntity");
                AbstractWorkflowEntity entity1 = (AbstractWorkflowEntity) entity;//2017-10-19 12:04:27 ---Jax
                ext.setWorkflowId(entity1.getWorkflow().getId());
            } else if (entity instanceof AbstractAuditorEntity) {
                ext.setEntityType("auditEntity");
            }

            Message message = CommonUtil.customElemMessageWrapper(dataFromTemplate, ext, act.getMessageSender().getId().toString(), user.getId().toString(), 2);
            ReturnObject restTemplatePost = restTemplatePost(url, message);
            if (restTemplatePost != null && "OK".equals(restTemplatePost.getActionStatus())) {
                return dataFromTemplate;
            } else {
                return restTemplatePost.getErrorCode() + ":" + restTemplatePost.getErrorInfo();
            }

        } else {
            return null;
        }
    }

    private String getDataFromTemplate(User user, AbstractAuditorEntity entity, AbstractAuditorAct act,
                                       String template) {
        SpelContext spelContext = new SpelContext(user, entity, act);
        ExpressionParser parser = new SpelExpressionParser();

        String result = parser.parseExpression(template, new TemplateParserContext()).getValue(spelContext,
                String.class);
        return result;

    }

    private ReturnObject restTemplatePost(String url, Object message) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String ret = restTemplate.postForObject(url, message, String.class);
        ObjectMapper mapper = new ObjectMapper();
        ReturnObjectImpl readValue;
        try {
            readValue = mapper.readValue(ret, ReturnObjectImpl.class);
        } catch (IOException e) {
            throw new FailReturnObject(1800, "解析tim信息错误", com.liyang.util.ReturnObject.Level.FATAL);
        }
        return readValue;
    }

    public ReturnObject addUser(String identifier, String nick, String faceUrl) {
        String url = String.format(urlTemplate, "im_open_login_svc", "account_import", new Random().nextInt(100000000));
        AddUser user = new AddUser();
        user.setFaceUrl(faceUrl);
        user.setIdentifier(identifier);
        user.setNick(nick);
        ReturnObject restTemplatePost = restTemplatePost(url, user);
        if (restTemplatePost != null && "OK".equals(restTemplatePost.getActionStatus())) {
            return restTemplatePost;
        } else {
            throw new FailReturnObject(1750, restTemplatePost.getErrorCode() + ":" + restTemplatePost.getErrorInfo(), com.liyang.util.ReturnObject.Level.FATAL);
        }
    }

    private class AddUser {
        @JsonProperty("Identifier")
        private String identifier;
        @JsonProperty("Nick")
        private String nick;
        @JsonProperty("FaceUrl")
        private String faceUrl;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }

    }

    private class SpelContext {
        private User user;
        private AbstractAuditorEntity entity;
        private AbstractAuditorAct act;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public AbstractAuditorEntity getEntity() {
            return entity;
        }

        public void setEntity(AbstractAuditorEntity entity) {
            this.entity = entity;
        }

        public AbstractAuditorAct getAct() {
            return act;
        }

        public void setAct(AbstractAuditorAct act) {
            this.act = act;
        }

        public SpelContext(User user, AbstractAuditorEntity entity, AbstractAuditorAct act) {
            super();
            this.user = user;
            this.entity = entity;
            this.act = act;
        }

    }
}
