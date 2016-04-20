

package com.krm.dbaudit.web.task.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.krm.dbaudit.common.base.BaseEntity;

/**
 * 
 * @author if
 */

@SuppressWarnings({ "unused"})
@Table(name="sys_task")
public class MaintainTaskDefinition extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="Any",sequenceName="sys_area_seq")
    private Long id; //id <主键id>
	
	public Long getId() {
		return this.getLong("id");
    }
   
    public void setId(Long id) {
		this.set("id", id);
    }
    private String beanClass; //bean_class <>

	
    private String beanName; //bean_name <>

	
    private String cron; //cron <>

	
    private String description; //description <>

	
    private Boolean isStart; //is_start <>

	
    private String methodName; //method_name <>

	
    private String name; //name <>


	public String getBeanClass() {
		return this.getString("beanClass");
    }
   
    public void setBeanClass(String beanClass) {
		this.set("beanClass", beanClass);
    }

	public String getBeanName() {
		return this.getString("beanName");
    }
   
    public void setBeanName(String beanName) {
		this.set("beanName", beanName);
    }

	public String getCron() {
		return this.getString("cron");
    }
   
    public void setCron(String cron) {
		this.set("cron", cron);
    }

	public String getDescription() {
		return this.getString("description");
    }
   
    public void setDescription(String description) {
		this.set("description", description);
    }

	public Boolean getIsStart() {
		return this.getBoolean("isStart");
    }
   
    public void setIsStart(Boolean isStart) {
		this.set("isStart", isStart);
    }

	public String getMethodName() {
		return this.getString("methodName");
    }
   
    public void setMethodName(String methodName) {
		this.set("methodName", methodName);
    }

	public String getName() {
		return this.getString("name");
    }
   
    public void setName(String name) {
		this.set("name", name);
    }


}
