import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.List;
import java.io.*; 
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.WebClient;
import java.sql.*;
import java.text.SimpleDateFormat;  
//import java.util.time;  
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Main {
    public static void main( String[] args ) throws SQLException {
	


        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);
	try{
		HtmlPage page = webClient.getPage("https://oag.ca.gov/privacy/databreach/list/");

            //webClient.getCurrentWindow().getJobManager().removeAllJobs();
	    HtmlTableBody table = (HtmlTableBody) page.getFirstByXPath("/html/body/div[4]/div/section/div/div/div/div/div[3]/div/table/tbody");
	   try{
                Connection connection = DriverManager.getConnection(
    "jdbc:mariadb://localhost:3306/Breaches",
    "skalnor92", "Nate1992"
);


                Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Breaches", "skalnor92", "Nate1992");
        
	    System.out.println(table);
	    
	    for ( HtmlTableRow row : table.getRows()) {
    			System.out.println("Found row");
	
		
		int cellIter = 0;
    		String name = null;
		String date = null;
		for (HtmlTableCell cell : row.getCells()) {
			//System.out.println("   Found cell: " + cell.asNormalizedText());
			if (cellIter != 1){
			
				//System.out.println("   Found cell: " + cell.asNormalizedText());
				if (cellIter == 0){
				name = cell.asNormalizedText();
				}
				if (cellIter == 2){
				
				date = cell.asNormalizedText();
				
				//System.out.println("This si the date " +date);
				date = format(date);
				
				
				}
		
		
		
		
		
			}
		cellIter++;
    	     	}
		
    //System.out.println("The name is " + name + " and the date is " + date);


		PreparedStatement breach = connection.prepareStatement("INSERT INTO Company_and_Date(Name, date_reported) VALUES (?, ?)");
		breach.setString(1, name);
		breach.setDate(2, Date.valueOf(date));
		int rowsInserted = breach.executeUpdate();    
          
	    }
	 }catch(SQLException e)
        {
                System.out.println(e);
        }     
	  webClient.close();
            String title = page.getTitleText();
            System.out.println("Page Title: " + title);

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        
    }


public static String format(String date){
	System.out.println("Pre formatting"+date);
	String[] splitter = date.split("/");

	date =  (splitter[2] + "-" + splitter[0] +"-" + splitter[1]);
	System.out.println(date);
	return date;
}
}
