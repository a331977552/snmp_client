package uk.co.evaluation.course.client;

import java.util.ArrayList;
import java.util.List;

import org.snmp4j.smi.VariableBinding;

public class Response {

	private int status;
	private List<VariableBinding> result=new ArrayList<>();
	private boolean isSuccessful;
	public String getErrorStatusText() {
		return errorStatusText;
	}
	public void setErrorStatusText(String errorStatusText) {
		this.errorStatusText = errorStatusText;
	}
	private String errorStatusText;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<VariableBinding> getResult() {
		return result;
	}
	public void setResult(List<VariableBinding> result) {
		this.result = result;
	}
	
	public void addResult(VariableBinding result) {
		
		this.result.add(result);
	}
	
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	
}
