package hello;

import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	System.out.println("Client connected.");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
    @RequestMapping(value = "/scan", method = RequestMethod.POST)
    public HttpStatus getScanResult(@RequestBody ScanResult scanResult) {
    	//System.out.println(scanResult.data.get(0).SSID);
    	writeScanToHistory(scanResult);
    	System.out.println("Done POST.");
        return HttpStatus.OK;//new ResponseEntity<ScanResult>(scanResult, HttpStatus.OK);
    } 
    
    @RequestMapping(value = "/scan", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody ScanHistory historyRequest(@RequestParam(value="name", defaultValue="default") String name) {
    	ScanHistory history = getHistory(name);
    	System.out.println("Done GET.");
        return history;
    }
    
    @RequestMapping(value = "/scan", method = RequestMethod.DELETE)
    public HttpStatus deleteRequest(@RequestParam(value="name", defaultValue="default") String name) {
   		deleteHistory(name);
   		System.out.println("Done DELETE.");
        return HttpStatus.OK;
    }
    
    public void deleteHistory(String name) {
    	BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = "";

			fw = new FileWriter(name + ".txt");
			bw = new BufferedWriter(fw);
			bw.write(content);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    public void writeScanToHistory(ScanResult scanResult) {
    	BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			//String content = "This is the content to write into file\n";

			fw = new FileWriter(scanResult.getName() + ".txt", true);
			bw = new BufferedWriter(fw);
			bw.write(scanResult.timestamp + "\n");
			
			for(ScanData data : scanResult.data) {
				bw.write(data.getNetwork() + "\n");
				bw.write(data.getSSID() + "\n");
				bw.write(data.getBSSID() + "\n");
				bw.write(data.getFrequency() + "\n");
				bw.write(data.getIntensity() + "\n");
				bw.write(data.getCapabilities() + "\n");
			}
			
			bw.write(Character.toString ((char) 31) + "\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    public ScanHistory getHistory(String name){
    	BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(name + ".txt");
			br = new BufferedReader(fr);
			String sCurrentLine;
			
			ScanHistory scanHistory = new ScanHistory();
			Integer entries = 0;
			List<ScanResult> scanResultList = new ArrayList<ScanResult>();
			
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.print(sCurrentLine + "\n");
				
				ScanResult scanResult = new ScanResult();
				scanResult.setName(name);
				scanResult.setTimestamp(sCurrentLine);
				List<ScanData> scanDataList = new ArrayList<ScanData>();
				
				while (!(sCurrentLine = br.readLine()).equals(Character.toString ((char) 31))) {
					ScanData scanData = new ScanData();
					
					scanData.setNetwork(sCurrentLine);
					scanData.setSSID(br.readLine());
					scanData.setBSSID(br.readLine());
					scanData.setFrequency(br.readLine());
					scanData.setIntensity(br.readLine());
					scanData.setCapabilities(br.readLine());
					
					scanDataList.add(scanData);
				}
				
				scanResult.setData(scanDataList);
				scanResultList.add(scanResult);
				entries++;
				
				//System.out.println(sCurrentLine);
			}
			
			scanHistory.setEntries(entries);
			scanHistory.setHistory(scanResultList);
			
			return scanHistory;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    	return null;
    }
}
