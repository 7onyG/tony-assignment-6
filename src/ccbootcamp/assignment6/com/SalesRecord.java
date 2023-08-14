package ccbootcamp.assignment6.com;

public class SalesRecord {
	    private String  year;
	    private String  salesAmount;
	    
	    public SalesRecord(String  year,  String salesAmount) {
	        this.year = year;
	        this.salesAmount = salesAmount;
}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getSalesAmount() {
			return salesAmount;
		}

		public void setSalesAmount(String salesAmount) {
			this.salesAmount = salesAmount;
		}
}
	    
	  