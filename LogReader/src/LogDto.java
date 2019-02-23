import java.io.Serializable;

/**
 * 과제 1번을 수행할 때 값을 저장하기 위한 DTO
 * @author meta
 *
 */
public class LogDto implements Serializable {
	
	private static final long serialVersionUID = 2657551712788701441L;
	private String start;
	private String end;
	private String esb_tran_id;
	private String content_length;
	private String call_time;
	private String bmarshalling;
	private String marshalling;
	private String invoke_galileo;
	private String unmarshalling;
	
	public LogDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogDto(String start, String end, String esb_tran_id, String content_length, String call_time, String bmarshalling,
			String marshalling, String invoke_galileo, String unmarshalling) {
		super();
		this.start = start;
		this.end = end;
		this.esb_tran_id = esb_tran_id;
		this.content_length = content_length;
		this.call_time = call_time;
		this.bmarshalling = bmarshalling;
		this.marshalling = marshalling;
		this.invoke_galileo = invoke_galileo;
		this.unmarshalling = unmarshalling;
	}

	@Override
	public String toString() {
		return "LogDto [start=" + start + ", end=" + end + ", esb_tran_id=" + esb_tran_id + ", content_length="
				+ content_length + ", call_time=" + call_time + ", bmarshalling=" + bmarshalling + ", marshalling="
				+ marshalling + ", invoke_galileo=" + invoke_galileo + ", unmarshalling=" + unmarshalling + "]";
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getEsb_tran_id() {
		return esb_tran_id;
	}

	public void setEsb_tran_id(String esb_tran_id) {
		this.esb_tran_id = esb_tran_id;
	}

	public String getContent_length() {
		return content_length;
	}

	public void setContent_length(String string) {
		this.content_length = string;
	}

	public String getCall_time() {
		return call_time;
	}

	public void setCall_time(String string) {
		this.call_time = string;
	}

	public String getBmarshalling() {
		return bmarshalling;
	}

	public void setBmarshalling(String string) {
		this.bmarshalling = string;
	}

	public String getMarshalling() {
		return marshalling;
	}

	public void setMarshalling(String marshalling) {
		this.marshalling = marshalling;
	}

	public String getInvoke_galileo() {
		return invoke_galileo;
	}

	public void setInvoke_galileo(String invoke_galileo) {
		this.invoke_galileo = invoke_galileo;
	}

	public String getUnmarshalling() {
		return unmarshalling;
	}

	public void setUnmarshalling(String unmarshalling) {
		this.unmarshalling = unmarshalling;
	}
	
	
	
	
}
