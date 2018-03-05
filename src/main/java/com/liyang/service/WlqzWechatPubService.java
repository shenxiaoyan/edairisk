package com.liyang.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import com.liyang.util.ReturnObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.annotation.PersonField;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.person.Person;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.DateUtil;
import com.liyang.util.FailReturnObject;

import net.sf.json.JSONObject;

/**
 * 微信公众号
 *
 * @author win7
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@Service
public class WlqzWechatPubService {
    @Value("${spring.wlqz.wechat.appid}")
    private String appid;
    @Value("${spring.wlqz.wechat.secret}")
    private String security;// 公众号的appsecret
    @Value("${spring.wlqz.wechat.WE_MESSAGE_URL}")
    private String WE_MESSAGE_URL;
    @Value("${spring.wlqz.wechat.FIRST_KEY}")
    private String FIRST_KEY;
    @Value("${spring.wlqz.wechat.ONE_KEY}")
    private String ONE_KEY;
    @Value("${spring.wlqz.wechat.TWO_KEY}")
    private String TWO_KEY;
    @Value("${spring.wlqz.wechat.THREE_KEY}")
    private String THREE_KEY;
    @Value("${spring.wlqz.wechat.FOUR_KEY}")
    private String FOUR_KEY;
    @Value("${spring.wlqz.wechat.FIVE_KEY}")
    private String FIVE_KEY;
    @Value("${spring.wlqz.wechat.REMARK_KEY}")
    private String REMARK_KEY;
    @Value("${spring.wlqz.wechat.MESSAGE_TEMPLATE_LOAN}")
    private String MESSAGE_TEMPLATE_LOAN;
    @Value("${spring.wlqz.wechat.MESSAGE_TEMPLATE_REPAYMENT}")
    private String MESSAGE_TEMPLATE_REPAYMENT;
    @Value("${spring.wlqz.wechat.OPEN_ACC_FIRST_VALUE}")
    private String OPEN_ACC_FIRST_VALUE;
    @Value("${spring.wlqz.wechat.MESSAGE_TEMPLATE_OPEN_ACC}")
    private String MESSAGE_TEMPLATE_OPEN_ACC;
    @Value("${spring.wlqz.wechat.MESSAGE_TEMPLATE_ADD_QUOTA}")
    private String MESSAGE_TEMPLATE_ADD_QUOTA;
    @Value("${spring.wlqz.wechat.MESSAGE_TEMPLATE_REPAYMENT_REMIND}")
    private String MESSAGE_TEMPLATE_REPAYMENT_REMIND;
    @Value("${spring.wlqz.wechat.MESSAGE_TEMPLATE_LOANOVERDUE}")
    private String MESSAGE_TEMPLATE_LOANOVERDUE;
    @Value("${spring.wlqz.wechat.URL}")
    private String URL;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WlqzWechatGetService wechatGetService;
    @Autowired
    private UserStateRepository userStateRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TIMService timService;

    @Autowired
    private CacheManager cacheManager;
    private AccountService accountService;
    public String getWechatMenu() {
    	
    	System.out.println();
    	String result = "";
    	
    	result = "{\"button\":[\n" +
                "        {\n" +
                "            \"name\":\"借款\",\n" +
                "            \"sub_button\":[\n" +
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"随借随还\",\n" +
                "                    \"url\":\"" + URL + "/html/loan/sjsh_introduce.html\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"网点信用贷\",\n" +
                "                    \"url\":\"" + URL + "/html/loan/xyd_introduce.html\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"设备抵押\",\n" +
                "                    \"url\":\"" + URL + "/html/loan/appli.html\"\n" +
                "                },\n" + 
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"网点面单白条\",\n" +
                "                    \"url\":\"" + URL + "/html/loan/mdbt_introduce.html\"\n" +
                "                },\n" + 
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"融资租赁\",\n" +
                "                    \"url\":\"" + URL + "/html/loan/rzzp_introduce.html\"\n" +
                "                }]\n" +                
                "        },\n" +
                "        {\n" +
                "            \"name\":\"账户\",\n" +
                "            \"type\":\"view\",\n" +
                "            \"url\":\"" + URL + "/html/account/myLoanLogin.html\"\n" +//账户开通成功
                "        },\n" +
                "        {\n" +
                "            \"name\":\"我的\",\n" +
                "            \"sub_button\":[\n" +
                
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"我的资料\",\n" +
                "                    \"url\":\"" + URL + "/html/my/myProfileLogin.html\"\n" +//申请成功失败
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"公司资料\",\n" +
                "                    \"url\":\"" + URL + "/html/my/company/companyLogin.html\"\n" +//申请成功失败
                "                },\n" +
                "\t\t{\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"我的评价\",\n" +
                "                    \"url\":\"" + URL + "/html/my/Recommend.html\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"联系我们\",\n" +
                "                    \"url\":\"" + URL + "/html/my/contact.html\"\n" +//申请成功失败
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"view\",\n" +
                "                    \"name\":\"app下载\",\n" +
                "                    \"url\":\"" + URL + "/html/aa.html\"\n" +
                "                }]\n" +
                "        }\n" +
                "    ]\n" +
                "    }";
        System.out.println(result);
    	return result;
        
    }
    
     
     
    /**
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI
     * &response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     *
     * @return
     */
    /**
     * 授权登录
     *
     * @param redirect 要重定向的url
     * @return
     */
    public String authenize(String redirect) {
        String redirectUrl = URLEncoder.encode(redirect);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
                + redirectUrl + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return url;

    }

    /**
     * @param code
     * @return
     */
    public User callback(String code) {
        Map<String, String> token_auth = getAccessToken(code);
        //Map token_auth=new HashMap<>();
        User user = doApply(token_auth);

        User exitUser = userRepository.findByOpenid(user.getOpenid());
        if (exitUser != null) {
            return exitUser;
        }
        UserAct actApply = userService.getAct(user, "create", user.getRole());
        user = userService.doBeforeAct(user, actApply, null, null);
        user = userRepository.save(user);
        timService.addUser(user.getOpenid(), user.getNickname(), user.getHeadimgurl());
        userService.doAfterAct(user, null, null);
        return user;
    }

    /**
     * 微信登录判断是否在线
     * @param code
     * @return
     */
    public User getUserByCode(String code)
    {
        Map<String, String> token_auth = getAccessToken(code);
        String token_quanju = wechatGetService.getCacheTokenTotal();
        Map<String, String> info = getUserInfo(token_quanju, token_auth.get("openid").toString());///
        return userRepository.findByOpenid(info.get("openid").toString());//
    }
    public void weixinBind(String code,User user)
    {
        Map<String,String> token_auth = getAccessToken(code);
        String token_quanju=wechatGetService.getCacheTokenTotal();
        Map<String,String> info = getUserInfo(token_quanju, token_auth.get("openid").toString());///
        User exist_user = userRepository.findByOpenid(info.get("openid").toString());
        if (exist_user != null) {
            throw new FailReturnObject(1572, "微信已经被绑定过",com.liyang.util.ReturnObject.Level.WARNING);
        }
        if("".equals(user.getOpenid())){
            throw new FailReturnObject(1623,"请联系开发人员,openid为空字符串", ReturnObject.Level.INFO);
        }
        if(user.getOpenid()!=null){
            throw new FailReturnObject(1573, "此用户已经绑定过微信",com.liyang.util.ReturnObject.Level.WARNING);
        }
        user.setOpenid(info.get("openid"));
        user.setNickname(CommonUtil.filter(info.get("nickname")));
        user.setUnionid(info.get("unionid"));
        user.setProvince(info.get("province"));
        user.setCity(info.get("city"));
        user.setCountry(info.get("country"));
        user.setHeadimgurl(info.get("headimgurl"));
        userRepository.save(user);
        timService.addUser(user.getOpenid(), user.getNickname(), user.getHeadimgurl());
//        userService.doAfterAct(user, null, null); //---报错了
    }
    private User fillOwnInfo(User user, Map token) {
        Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
        user.setOpenid(token.get("openid").toString());
        user.setUnionid(token.get("unionid").toString());
        user.setNickname(CommonUtil.filter(info.get("nickname").toString()));
        user.setSex(Integer.valueOf(info.get("sex").toString()));
        user.setProvince(info.get("province").toString());
        user.setCity(info.get("city").toString());
        user.setCountry(info.get("country").toString());
        user.setHeadimgurl(info.get("headimgurl").toString());
        return user;

    }
//    public User weixinRegister(String code, String username, String password) {
//        Map<String, String> token_auth = getAccessToken(code);
//        Map<String, String> info = getUserInfo(wechatGetService.getCacheTokenTotal(), token_auth.get("openid").toString());
//        User user = new User();
//        user.setOpenid(info.get("openid"));
//        user.setNickname(CommonUtil.filter(info.get("nickname")));
//        user.setUnionid(info.get("unionid"));
//        user.setProvince(info.get("province"));
//        user.setCity(info.get("city"));
//        user.setCountry(info.get("country"));
//        user.setHeadimgurl(info.get("headimgurl"));
//        UserState userState = userStateRepository.findByStateCode("ENABLED");
//        Role role = roleRepository.findByRoleCode("USER");
//        user.setState(userState);
//        user.setRole(role);
//        user.setUsername(username);
//        user.setPassword(password);
//        timService.addUser(user.getOpenid(), user.getNickname(), user.getHeadimgurl());
//        userService.doAfterAct(user, null, null);
//        accountService.createdDefaultAccountByUser(user);
//        return user;
//    }

    /**
     * 根据微信用户的openid在数据库中查找。没有找到根据微信用户凭证，用户唯一标识从微信后台获取，然后保存。
     *
     * @param token_auth 封装了微信用户的信息
     * @return 微信用户
     */
    @Transactional
    public User doApply(Map<String, String> token_auth) {
        User user = null;


        String token_quanju = wechatGetService.getCacheTokenTotal();
        Map<String, String> info = getUserInfo(token_quanju, token_auth.get("openid").toString());///
        user = userRepository.findByOpenid(info.get("openid").toString());//

        if (user != null) {
            System.out.println(user.getUnionid());
            return user;
        }
        user = new User();
        user.setOpenid(info.get("openid"));
        user.setNickname(CommonUtil.filter(info.get("nickname")));
    /*	if (info.get("sex")!=null) {
			user.setSex((int));
		}*/
        user.setUnionid(info.get("unionid"));
        user.setProvince(info.get("province"));
        user.setCity(info.get("city"));
        user.setCountry(info.get("country"));
        user.setHeadimgurl(info.get("headimgurl"));
        UserState userState = userStateRepository.findByStateCode("ENABLED");
		/*user.setLanguage(info.get("language").toString());
		user.setGroupid((Integer)info.get("groupid"));
		user.setSubscribe(info.get("subscribe").toString());
		user.setSubscribe_time((Integer) info.get("subscribe_time"));
		user.setRemark(info.get("remark").toString());*/
//		user.setSig(TIMSignature.generate(info.get("unionid").toString()).urlSig);
        Role role = roleRepository.findByRoleCode("USER");
        user.setState(userState);
        user.setRole(role);
        //user = userRepository.save(user);
        System.out.println(user.getUnionid());
        return user;
    }

    /**
     * 获取access_token
     *
     * @param code 状态码
     * @return
     */
    private Map getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid
                + "&secret=" + security + "&code=" + code + "&grant_type=authorization_code";
        Map<String, Object> m = getMapFromURL(url);
        if (m.containsKey("errcode")) {
            throw new FailReturnObject(130, "获取微信token返回错误：" + m.get("errcode") + m.get("errmsg").toString(), com.liyang.util.ReturnObject.Level.FATAL);
        }
        return m;
    }
    /**
     * 获取微信用户信息
     *
     * @param token  用户凭证
     * @param openid 用户唯一标识
     * @return 微信用户信息集合
     */
    private Map getUserInfo(String token, String openid) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid + "&lang=zh_CN ";
        try {
            Map<String, Object> m = getMapFromURL(url);
            if (m.containsKey("errcode")) {
                throw new FailReturnObject(140, "获取微信userinfo返回错误：" + m.get("errmsg").toString(), com.liyang.util.ReturnObject.Level.FATAL);
            }
            String nickname = (String) m.get("nickname");
            String name = new String(nickname.getBytes("ISO-8859-1"), "utf-8");
            m.put("nickname", name);
            return m;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new FailReturnObject(150, "用户微信信息解析失败", com.liyang.util.ReturnObject.Level.ERROR);
        }
    }

    /**
     * 根据json数据来创建自定义菜单
     * <p>
     * 自定义菜单的json数据
     *
     * @return 是否创建成功
     */
    public boolean isAccessToken() {
        // https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + wechatGetService.getCacheTokenTotal();

        //Map<String, Object> m = getMapFromJSON(url);
        if (getStringFromURL(url, getWechatMenu()).contains("ok")) {
            return true;
        }
        return false;
    }

    /**
     * get请求url，返回的json数据包装成Map
     *
     * @param url
     * @return 保存了json数据的map
     */
    public Map<String, Object> getMapFromURL(String url) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(result);
        Map<String, Object> m = null;
        try {
            m = mapper.readValue(result, Map.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return m;
    }


    /**
     * post 携带json请求url，返回的json为String
     *
     * @param url
     * @return json格式的String
     */
    private String getStringFromURL(String url, String json) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=utf-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(json);
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        return result;
    }

    public Map<String, Object> stringToMap(String json) {
        return (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(json), Map.class);
    }

    public String mapToString(Map map) {
        return JSONObject.fromObject(map).toString();
    }

    private Map<String, Object> getMapFromJson(String url, String json) {
        String jsonString = getStringFromURL(url, json);
        JSONObject jasonObject = JSONObject.fromObject(json);
        ;
        Map map = (Map) jasonObject;
        return map;
    }

    /**
     * 推送贷款消息
     *
     * @param toUser
     * @param name
     * @param money
     * @param remark
     * @param type
     * @param url
     */
    public void pushLoanMessage(User toUser, String name, String money, String remark, String type, String url) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(FIRST_KEY, "尊敬的用户您好，您的贷款申请已成功受理");
        map.put(ONE_KEY, name);
        map.put(TWO_KEY, type);
        map.put(THREE_KEY, money);
        map.put(REMARK_KEY, remark);
        pushMessage(toUser, url, MESSAGE_TEMPLATE_LOAN, map);
    }

    /**
     * 推送还款消息
     *
     * @param toUser
     * @param name
     * @param bank
     * @param money
     * @param state
     * @param time
     * @param remark
     * @param url
     */
    public void pushRepaymentMessage(User toUser, String name, String bank, String money, String state, String time, String remark, String url) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(FIRST_KEY, "还款进度提醒");
        map.put(TWO_KEY, bank);
        map.put(THREE_KEY, state);
        map.put(FOUR_KEY, money);
        map.put(FIVE_KEY, time);
        map.put(REMARK_KEY, remark);
        pushMessage(toUser, url, MESSAGE_TEMPLATE_REPAYMENT, map);
    }

    /**
     * 推送开户消息
     *
     * @param toUser
     * @param state
     * @param name
     * @param telephone
     * @param url
     * @param result
     */
    public void pushOpenAccMessage(User toUser, String state, String name, String telephone, String url, String result) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(FIRST_KEY, OPEN_ACC_FIRST_VALUE.replace("&", name));
        map.put(ONE_KEY, name);
        map.put(TWO_KEY, telephone);
        map.put(THREE_KEY, state);
        map.put("remark", result);
        pushMessage(toUser, url, MESSAGE_TEMPLATE_OPEN_ACC, map);
    }

    /**
     * 推送信用卡提额消息
     *
     * @param toUser
     * @param state
     * @param name
     * @param telephone
     * @param url
     * @param result
     */
    public void pushAddQuotPassMessage(User toUser, BigDecimal quota) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(FIRST_KEY,"尊敬的用户：您好！恭喜，您的提额申请已通过");
        map.put(ONE_KEY, quota.toString());
        map.put(TWO_KEY, DateUtil.getStrByDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("remark", "感谢您的申请");
        pushMessage(toUser, null, MESSAGE_TEMPLATE_ADD_QUOTA, map);
    }
    
    /**
     * 推送还款提醒消息
     * @param toUser
     * @param date
     * @param amonut
     * @param orderNo
     */
    public void pushRepaymentRemindMessage(User toUser,String date,String amonut,String orderNo){
    	 Map<String, Object> map = new LinkedHashMap<>();
    	 map.put(FIRST_KEY,"您好，您近期有一笔还款。");
         map.put(ONE_KEY, orderNo);
         map.put(TWO_KEY, amonut);
         map.put(THREE_KEY, date);
         map.put("remark", "请及时还款,如遇问题请联系客服.");
         pushMessage(toUser, null, MESSAGE_TEMPLATE_REPAYMENT_REMIND, map);
    }
    
    /**
     * 推送逾期消息
     */
    public void pushOverdueMessage(User toUser,String orderNo,String date,String overdueAmount,String punishInterest){
    	Map<String, Object> map = new LinkedHashMap<>();
   	 	map.put(FIRST_KEY,"您好，您有一笔借款已经逾期");
        map.put(ONE_KEY, orderNo);
        map.put(TWO_KEY, date);
        map.put(THREE_KEY, overdueAmount);
        map.put(FOUR_KEY, punishInterest);
        map.put("remark", "请尽快还款，否则将会影响您的个人信用");
        pushMessage(toUser, null, MESSAGE_TEMPLATE_LOANOVERDUE, map);
    	
    }
    
    
    /**
     * 微信推送消息
     *
     * @param toUser     通知的用户
     * @param url        点击跳转的url
     * @param templateId 微信消息模板id
     * @return
     */
    public void pushMessage(User toUser, String url, String templateId, Map<String, Object> childMap) {
        Map<String, Object> messageMap = new LinkedHashMap<>();
        messageMap.put("touser", toUser.getOpenid());
        messageMap.put("template_id", templateId);
        messageMap.put("url", url);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Map<String, Object> dataMap = new HashMap<>();

        //通知用户时间
        //dateMap.put("date", format.format(new Date()));

        Set<Map.Entry<String, Object>> entries = childMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            dataMap.putAll(buildTemplateData((String) entry.getKey(), (String) entry.getValue()));
        }
        messageMap.put("data", dataMap);
        System.out.println(mapToString(messageMap));
        String weURL = WE_MESSAGE_URL + wechatGetService.getCacheTokenTotal();
        System.out.println(getStringFromURL(weURL, mapToString(messageMap)) + ":----------微信消息模板");
    }

    /**
     * 建立消息
     * "first": {
     * "value":"恭喜你购买成功！",
     * "color":"#173177"
     * },
     *
     * @param datakey
     * @param value
     * @return
     */
    protected Map<String, Object> buildTemplateData(String datakey, String value) {
        Map<String, Object> map = new LinkedHashMap<>();
        Map<String, String> valueMap = new LinkedHashMap<>();
        map.put(datakey, valueMap);
        valueMap.put("value", value);
        valueMap.put("color", "#173177");

        return map;
    }

    public String getOpenAccState(int state) {

        if (state == 1) {

            return "初步筛选已通过！"
                    + "";
        } else if (state == 2) {

        } else if (state == 3) {

        }
        return null;
    }

    public Person setPersonField(Person person, AbstractWorkflowEntity entity) {
    	//通过Class.getDeclaredFields()获取类或接口的指定已声明字段。  
        Field[] personFields = person.getClass().getDeclaredFields();
        Field[] entityFields = entity.getClass().getDeclaredFields();
        for (Field entityField : entityFields) {
            if (entityField.getAnnotation(PersonField.class) != null) {
                String annotationName = entityField.getAnnotation(PersonField.class).name();
                for (Field personField : personFields) {
                	//获取字段上的注解标签
                    if (personField.getAnnotation(PersonField.class) != null) {
                        System.out.println(personField + ":" + personField.getAnnotation(PersonField.class));
                        //获取字段上的注解标签的值（即标签的名字）
                        String personAnnotaionName = personField.getAnnotation(PersonField.class).name();

                        if (!StringUtils.isEmpty(personAnnotaionName) && personAnnotaionName.equals(annotationName)) {
                            personField.setAccessible(true);//成员变量为private，设置是否允许访问，而不是修改原来的访问权限修饰词
                            entityField.setAccessible(true);
                            try {
                                personField.set(person, entityField.get(entity));
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
        return person;

    }

}
