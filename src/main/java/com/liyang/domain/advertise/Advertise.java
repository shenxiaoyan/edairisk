package com.liyang.domain.advertise;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.application.Application;
import com.liyang.domain.base.*;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonLog;
import com.liyang.domain.product.Product;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;



/**
 * Created by win7 on 2017-07-25.
 */
@Entity
@Table(name = "advertise")
@Info(label="广告",placeholder="",tip="",help="",secret="")
public class Advertise extends AbstractWorkflowEntity<AdvertiseWorkflow, AdvertiseState, AdvertiseAct, AdvertiseLog,AdvertiseFile> {
    @Transient
    private static AbstractWorkflowService service;
    @Transient
    private static ActRepository actRepository;

   
    @JoinColumn(name="application_id")
    @ManyToOne
    @Info(label="投放终端",placeholder="",tip="",help="",secret="")
    private Application application;
    
    
    @Transient
    private static LogRepository logRepository;
    @Info(label="别名",placeholder="",tip="",help="",secret="")
    private String alias;
   
    
    @Lob
    @Info(label="介绍1",placeholder="",tip="",help="",secret="")
    private String intro1;
    @Lob
    @Info(label="介绍2",placeholder="",tip="",help="",secret="")
    private String intro2;
    @Lob
    @Info(label="介绍3",placeholder="",tip="",help="",secret="")
    private String intro3;
    @Lob
    @Info(label="介绍4",placeholder="",tip="",help="",secret="")
    private String intro4;
    @Lob
    @Info(label="介绍5",placeholder="",tip="",help="",secret="")
    private String intro5;
    
    @Info(label="图1",placeholder="",tip="",help="",secret="")
    private String image1;
    @Info(label="图2",placeholder="",tip="",help="",secret="")
    private String image2;
    @Info(label="图3",placeholder="",tip="",help="",secret="")
    private String image3;
    @Info(label="图4",placeholder="",tip="",help="",secret="")
    private String image4;
    @Info(label="图5",placeholder="",tip="",help="",secret="")
    private String image5;
    
    @Info(label="文件1url",placeholder="",tip="",help="",secret="")
    private String file1;
    
    @Column(name = "file1_name")
    @Info(label="文件1中文名",placeholder="",tip="",help="",secret="")
    private String file1Name;

    @Info(label="文件2url",placeholder="",tip="",help="",secret="")
    private String file2;
    
    @Column(name = "file2_name")
    @Info(label="文件2中文名",placeholder="",tip="",help="",secret="")
    private String file2Name;

    @Info(label="文件3url",placeholder="",tip="",help="",secret="")
    private String file3;
    
    @Column(name = "file3_name")
    @Info(label="文件3中文名",placeholder="",tip="",help="",secret="")
    private String file3Name;
    
    @Info(label="文件4url",placeholder="",tip="",help="",secret="")
    private String file4;
    
    @Column(name = "file4_name")
    @Info(label="文件4中文名",placeholder="",tip="",help="",secret="")
    private String file4Name;
    
    @Info(label="文件5url",placeholder="",tip="",help="",secret="")
    private String file5;
    
    @Column(name = "file5_name")
    @Info(label="文件5中文名",placeholder="",tip="",help="",secret="")
    private String file5Name;
    
    @Lob
    @Info(label="内部备注",placeholder="",tip="",help="",secret="")
    private String comment;

    @Info(label="排序",placeholder="",tip="",help="",secret="")
    private Integer sort;
    


	
	
	
    public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }



    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIntro1() {
        return intro1;
    }

    public void setIntro1(String intro1) {
        this.intro1 = intro1;
    }

    public String getIntro2() {
        return intro2;
    }

    public void setIntro2(String intro2) {
        this.intro2 = intro2;
    }

    public String getIntro3() {
        return intro3;
    }

    public void setIntro3(String intro3) {
        this.intro3 = intro3;
    }

    public String getIntro4() {
        return intro4;
    }

    public void setIntro4(String intro4) {
        this.intro4 = intro4;
    }

    public String getIntro5() {
        return intro5;
    }

    public void setIntro5(String intro5) {
        this.intro5 = intro5;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile1Name() {
        return file1Name;
    }

    public void setFile1Name(String file1Name) {
        this.file1Name = file1Name;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getFile2Name() {
        return file2Name;
    }

    public void setFile2Name(String file2Name) {
        this.file2Name = file2Name;
    }

    public String getFile3() {
        return file3;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }

    public String getFile3Name() {
        return file3Name;
    }

    public void setFile3Name(String file3Name) {
        this.file3Name = file3Name;
    }

    public String getFile4() {
        return file4;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
    }

    public String getFile4Name() {
        return file4Name;
    }

    public void setFile4Name(String file4Name) {
        this.file4Name = file4Name;
    }

    public String getFile5() {
        return file5;
    }

    public void setFile5(String file5) {
        this.file5 = file5;
    }

    public String getFile5Name() {
        return file5Name;
    }

    public void setFile5Name(String file5Name) {
        this.file5Name = file5Name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
  
    @JsonIgnore
	@Transient
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	public void setActRepository(ActRepository actRepository) {
		Advertise.actRepository = actRepository;
		
	}
	@JsonIgnore
	@Override
	public AbstractAuditorService getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Advertise.service=(AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public AdvertiseLog getLogInstance() {
		// TODO Auto-generated method stub
		return new AdvertiseLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Advertise.logRepository = logRepository;
		
	}

}
