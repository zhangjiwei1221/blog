package cn.butterfly.flinkcdc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SxglDeptYwInf {

	private String id;
	private String qlid;
	private String version;
	private String iddept_Yw_Inf;
	private String update_Type;
	private String dept_Ql_Id;
	private String dept_Yw_Num;
}
