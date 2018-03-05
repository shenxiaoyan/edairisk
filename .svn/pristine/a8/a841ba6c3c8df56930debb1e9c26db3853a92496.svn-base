package com.liyang.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.MapDifference.ValueDifference;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.message.BaseMsgElement;
import com.liyang.message.CustomContent;
import com.liyang.message.EnumOperationMessageType;
import com.liyang.message.Message;
import com.liyang.message.CustomContent.Ext;

public class CommonUtil {

	public static Message customElemMessageWrapper(Object data, Ext ext, String fromAccount, String toAccount, Integer syncOtherMachine) {
		Message message = new Message();
		BaseMsgElement customElement = new BaseMsgElement();
		CustomContent customContent = new CustomContent();
		customContent.setData(data);
		customContent.setExt(ext);
		customElement.setMsgType(EnumOperationMessageType.TIMCustomElem);
		customElement.setMsgContent(customContent);
		message.setMsgBody(new BaseMsgElement[] { customElement });
		message.setToAccount(toAccount);
		message.setFrom_Account(fromAccount);
		message.setSyncOtherMachine(syncOtherMachine);
		message.setMsgRandom(new Random().nextInt(30000000));
		return message;
	}

	public static String filter(String name) {
		Pattern emoji = Pattern.compile(

				"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher matcher = emoji.matcher(name);
		while (matcher.find()) {
			name = matcher.replaceAll("");
		}
		System.out.println(name + "--------------");
		return name;

	}

	public static String NumToDateString(String time) {
		Date date = NumToDate(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static Date NumToDate(String time) {
		Integer year = Integer.parseInt(time.substring(0, 4));
		Integer month = Integer.parseInt(time.substring(4, 6));
		Calendar calendar = Calendar.getInstance();
		if (time.length() == 8) {
			Integer day = Integer.parseInt(time.substring(6));

			calendar.set(year, month, day);
		} else if (time.length() == 14) {
			Integer day = Integer.parseInt(time.substring(6, 8));
			Integer hour = Integer.parseInt(time.substring(8, 10));
			Integer mm = Integer.parseInt(time.substring(10, 12));
			Integer ss = Integer.parseInt(time.substring(12));
			calendar.set(year, month, day, hour, mm, ss);
		}
		return calendar.getTime();
	}

	public static String template(String entityName, String viewType) {
		String defaultStr = "default";
		String prefix = "template/entities/";
		String split = "/";
		if (getCurrentUserDepartment() == null) {
			ClassPathResource resource = new ClassPathResource(prefix + viewType + ".html");
			if (!resource.exists()) {
				throw new FailReturnObject(800, "实体模板不存在"+(entityName==null?"":entityName)+(viewType==null?"":viewType),com.liyang.util.ReturnObject.Level.FATAL);
			}
		}
		String departmentType = getCurrentUserDepartment().getDepartmenttype().getDepartmenttypeCode().toString().toLowerCase();
		String departmentId = getCurrentUserDepartment().getId().toString().toLowerCase();
		String roleCode = CommonUtil.getCurrentUserRoleCode().toString().toLowerCase();
		String targetFile = entityName + split + departmentType + split + departmentId + split + roleCode + split
				+ viewType;
		ClassPathResource resource = new ClassPathResource(prefix + targetFile + ".html");
		if (!resource.exists()) {
			targetFile = entityName + split + departmentType + split + departmentId + split + viewType;
			resource = new ClassPathResource(prefix + targetFile + ".html");
			if (!resource.exists()) {

				targetFile = entityName + split + departmentType + split + roleCode + split + viewType;
				resource = new ClassPathResource(prefix + targetFile + ".html");
				if (resource.exists()) {
					return "entities" + split + targetFile;
				} else {
					targetFile = entityName + split + departmentType + split + viewType;
					resource = new ClassPathResource(prefix + targetFile + ".html");
					if (!resource.exists()) {
						targetFile = entityName + split + viewType;
						resource = new ClassPathResource(prefix + targetFile + ".html");
						if (!resource.exists()) {
							targetFile = viewType;
							resource = new ClassPathResource(prefix + targetFile + ".html");
							if (!resource.exists()) {
								throw new FailReturnObject(801, "实体模板不存在"+(entityName==null?"":entityName)+(viewType==null?"":viewType),com.liyang.util.ReturnObject.Level.WARNING);
							}
						}
					}
				}

			}
		}

		return "entities" + split + targetFile;
	}

	public static String getCurrentUserRoleCode() {
		String authority;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication) {
			authority = "ANONYMOUS";
		} else {
			if (authentication.getAuthorities().isEmpty()) {
				throw new FailReturnObject(640, "没有角色授权",com.liyang.util.ReturnObject.Level.FATAL);
			}
			authority = authentication.getAuthorities().toArray()[0].toString();
		}
		return authority;
	}

	public static Department getCurrentUserDepartment() {
		Department department = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication && authentication.getPrincipal() instanceof User) {
			//
			// User user=((User) authentication.getPrincipal());
			department = ((User) authentication.getPrincipal()).getDepartment();
		}
		return department;
	}

	public static String prettyPrint(Object o) {
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		String s = null;
		// 读取JSON数据
		try {
			s = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(2000, "json解析错误",com.liyang.util.ReturnObject.Level.ERROR);
		}
		return s;
	}

	public static List<Department> ownAndChildrenDepartments(Department node) {
		if (node == null) {
			return new ArrayList<Department>();
		}
		ArrayList<Department> departments = new ArrayList<Department>();
		departments.add(node);

		if (node.getChildren() != null && !node.getChildren().isEmpty()) {
			for (Department department : node.getChildren()) {
				departments.addAll(ownAndChildrenDepartments(department));
			}
		}

		return departments;
	}

	public static List<TreeNodeImpl> listToTree(Collection listItems) {
		List<TreeNodeImpl> treeNodes = new ArrayList<TreeNodeImpl>();
		for (Object t : listItems) {
			TreeNodeImpl<TreeNodeImpl> treeNodeImpl = new TreeNodeImpl<TreeNodeImpl>();
			BeanUtils.copyProperties(t, treeNodeImpl, "children", "parent");
			treeNodes.add(treeNodeImpl);
		}
		// Collections.sort(treeNodes);
		return TreeBuilder.bulid(treeNodes);

	}

	public static User getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		if (authentication.getPrincipal() instanceof User) {
			return (User) authentication.getPrincipal();
		} else {
			return null;
		}
	}

	public static void validate(Object object, Class<?> group) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> validates = validator.validate(object, group);
		if (!validates.isEmpty()) {
			throw new FailReturnObject(400, validates, FailReturnObject.Level.INFO);
		}

	}

	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());

			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class") && !key.contentEquals("enum")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					if (getter != null && !getter.getName().equals("getLastModifiedAt")
							&& !getter.getName().equals("getLastModifiedBy") && !getter.getName().equals("getLastAct")
							&& !getter.getName().equals("getCreatedAt")) {
						Class<?> returnType = property.getReadMethod().getReturnType();
						Transient annotation = property.getReadMethod().getAnnotation(Transient.class);
						if (!returnType.equals(Map.class) && !returnType.equals(List.class)
								&& !returnType.equals(Set.class) && annotation == null) {
							Object value = getter.invoke(obj);
							map.put(key, value);
						}
					}

				}

			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FailReturnObject(1900, "bean2map失败",com.liyang.util.ReturnObject.Level.FATAL);
		}

		return map;

	}

	public static Map<String, String> mapStringToMap(String str) {
		str = str.substring(1, str.length() - 1);
		String[] strs = str.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (String string : strs) {
			String key = string.split("=")[0];
			String value = string.split("=")[1];
			map.put(key, value);
		}
		return map;
	}

	public static Map entityToDiffMap(AbstractAuditorEntity entity, Object linked) {

		if (entity.getMap() == null) {
			entity.setupMap();
		}
		MapDifference diffHadle = Maps.difference(entity.getMap(), CommonUtil.transBean2Map(entity));
		Map<String, ValueDifference> entriesDiffering = diffHadle.entriesDiffering();
		Set<Entry<String, ValueDifference>> entrySet = entriesDiffering.entrySet();
		HashMap<String, Map> hashMap = new HashMap<String, Map>();

		if (linked != null) {

			String linkedKey = entity.getLinkedKey();

			HashMap<String, Object> hashMap2 = new HashMap<String, Object>();

			BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(entity);
			if (!beanWrapperImpl.isReadableProperty(linkedKey)) {
				throw new FailReturnObject(2110, "没有属性" + linkedKey,com.liyang.util.ReturnObject.Level.ERROR);
			}
			Object propertyValue = new BeanWrapperImpl(entity).getPropertyValue(linkedKey);
			if (propertyValue == null) {
				throw new FailReturnObject(2100, "没有属性" + linkedKey,com.liyang.util.ReturnObject.Level.ERROR);
			}

			Set newSet = (Set) propertyValue;
			Object set1 = newSet.stream().map(e -> new B(((BaseEntity) e).getId(), ((BaseEntity) e).getLabel()))
					.collect(Collectors.toSet());
			Set set2 = entity.getLinkedSet();
			hashMap2.put("old", set2);
			hashMap2.put("new", set1);
			hashMap.put(linkedKey, hashMap2);
		} else {
			for (Entry<String, ValueDifference> entry : entrySet) {
				String key = entry.getKey();
				ValueDifference value = entry.getValue();
				HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
				if (value.leftValue() != null) {
					hashMap2.put("old", value.leftValue().toString().startsWith("{")
							? CommonUtil.mapStringToMap(value.leftValue().toString()) : value.leftValue().toString());
				} else {
					hashMap2.put("old", null);
				}
				if (value.rightValue() != null) {
					hashMap2.put("new", value.rightValue().toString().startsWith("{")
							? CommonUtil.mapStringToMap(value.rightValue().toString()) : value.rightValue().toString());
				} else {
					hashMap2.put("new", null);
				}
				hashMap.put(key, hashMap2);
			}

		}
		return hashMap;
	}

	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	public static String getlinkedKeyFromRequest(HttpServletRequest request) {
		String str = null;
		if (request.getMethod().equals("DELETE") && request.getRequestURI().matches(".*/\\d+/(\\w+)/\\d+")) {
			str = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/"));
			str = str.substring(str.lastIndexOf("/") + 1);
		} else {
			if (request.getHeader("Content-Type") != null
					&& request.getHeader("Content-Type").equals("text/uri-list")) {
				str = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
			}
		}
		return str;
	}

	public static Map<?, ?> fileCategoryToTree(Set<AbstractWorkflowFile> files) {

		return files.stream().collect(Collectors.groupingBy(AbstractWorkflowFile::getTopcategory,
				Collectors.groupingBy(AbstractWorkflowFile::getSubcategory, Collectors.counting())));
	}

	private static class B {
		private Integer id;
		private String label;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public B(Integer id, String label) {
			super();
			this.id = id;
			this.label = label;
		}

	}

}
