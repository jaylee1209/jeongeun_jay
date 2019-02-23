import java.io.Serializable;


/**
 * 과제 2번을 진행하기 위해 값을 저장하는 DTO
 * @author meta
 *
 */
public class LogDto2 implements Serializable{
	
	private static final long serialVersionUID = -8367455330815762550L;
	
	private String start;
	private String end;
	private String content;
	
	
	public LogDto2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LogDto2(String start, String end, String content) {
		super();
		this.start = start;
		this.end = end;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "LogDto2 [start=" + start + ", end=" + end + ", content=" + content + "]";
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
	
}
