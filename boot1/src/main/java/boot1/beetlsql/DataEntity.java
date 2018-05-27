/**
 * Copyright &copy; 2017-2027 <a href="http://www.cnony.com">JeeHook</a> All rights reserved.
 */
package boot1.beetlsql;

import java.io.Serializable;
import java.util.Date;

import org.beetl.sql.core.annotatoin.LogicDelete;
import org.beetl.sql.core.annotatoin.Version;
import org.hibernate.validator.constraints.Length;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据Entity类
 * @author cnony
 * @version 2017-05-16
 */
public abstract class DataEntity<M extends Model> extends Model<M> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected Long createBy;	// 创建者
	protected Long updateBy;	// 更新者
	@Length(min=0, max=255)
	protected String remarks;	// 备注
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	protected Date createDate;	// 创建日期
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	protected Date updateDate;	// 更新日期
	@Length(min=1, max=1)
	@JSONField(serialize=false)
	@LogicDelete
	protected String delFlag = "0"; 	// 删除标记（0：正常；1：删除；2：审核）
	//当调用内置的updateById，或者updateTemlateById的时候，被@Version注解的字段将作为where条件的一部分
	@Version
	private Integer version ; 
	
	/*public DataEntity(String id) {
		super(id);
	}*/
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(){
		/*if (!this.isNewRecord){
			if(this.getIdType().equals(IDTYPE_UUID)){
				setId(IdGen.uuid());
			}else if(this.getIdType().equals(IDTYPE_AUTO)){
				//使用自增长不需要设置主键
			}

		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}*/
		this.updateBy = 1L;
		this.createBy = 1L;
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(){
		/*User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}*/
		this.updateDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public M setId(Long id) {
		this.id = id;
		return (M) this;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public M setCreateBy(Long createBy) {
		this.createBy = createBy;
		return (M) this;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public M setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
		return (M) this;
	}

	public String getRemarks() {
		return remarks;
	}

	public M setRemarks(String remarks) {
		this.remarks = remarks;
		return (M) this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public M setCreateDate(Date createDate) {
		this.createDate = createDate;
		return (M) this;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public M setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
		return (M) this;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public M setDelFlag(String delFlag) {
		this.delFlag = delFlag;
		return (M) this;
	}

	public Integer getVersion() {
		return version;
	}

	public M setVersion(Integer version) {
		this.version = version;
		return (M) this;
	}
	
}
