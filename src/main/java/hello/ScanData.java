package hello;

public class ScanData {

		public String Network;
		public String SSID;
		public String BSSID;
		public String Frequency;
		public String Intensity;
		public String Capabilities;
		
		public ScanData(String Network, String SSID, String BSSID, String Frequency, String Intensity, String Capabilities) {
			this.Network = Network;
			this.SSID = SSID;
			this.BSSID = BSSID;
			this.Frequency = Frequency;
			this.Intensity = Intensity;
			this.Capabilities = Capabilities;
		}
		
		public ScanData() {
			
		}

		public void setNetwork(String Network) {
			this.Network = Network;
		}
		
		public void setSSID(String SSID) {
			this.SSID = SSID;
		}
		
		public void setBSSID(String BSSID) {
			this.BSSID = BSSID;
		}
		
		public void setFrequency(String Frequency) {
			this.Frequency = Frequency;
		}
		
		public void setIntensity(String Intensity) {
			this.Intensity = Intensity;
		}
		
		public void setCapabilities(String Capabilities) {
			this.Capabilities = Capabilities;
		}
		
		public String getNetwork() {
			return Network;
		}

		public String getSSID() {
			return SSID;
		}

		public String getBSSID() {
			return BSSID;
		}

		public String getFrequency() {
			return Frequency;
		}

		public String getIntensity() {
			return Intensity;
		}

		public String getCapabilities() {
			return Capabilities;
		}
}

