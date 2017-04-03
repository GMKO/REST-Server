package hello;

import java.util.List;

public class ScanResult {

	String name;
	List<ScanData> data;
	
	public ScanResult(String name, List<ScanData> data) {
		this.name = name;
		this.data = data;
	}
	
	public ScanResult() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setData(List<ScanData> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public List<ScanData> getData() {
		return data;
	}
}
