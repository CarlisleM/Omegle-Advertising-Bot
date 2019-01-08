import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class Main {

	public static void main(String[] args) {

		//---------------------------------------------------------------------------------------------------------------//
		// 												Initialise bot 													 //
		//---------------------------------------------------------------------------------------------------------------//
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/Carlisle/Downloads/chromedriver_win32/chromedriverclone.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://omegle.com");
		
		//---------------------------------------------------------------------------------------------------------------//
		// 						Set up interests to match with and connect to the first stranger						 //
		//---------------------------------------------------------------------------------------------------------------//		
		
		String[] interestsArray = {"Add", "your", "interests", "here"};
	
		System.out.println("Entering interests");
		driver.findElement(By.className("topicplaceholder")).click();	// Enter interests text box
		WebElement textbox = driver.findElement(By.className("newtopicinput"));	// Start a new entry
		
		for (int i = 0; i < interestsArray.length; i++) {
			driver.findElement(By.className("newtopicinput")).sendKeys(interestsArray[i]);	
			textbox.sendKeys(Keys.ENTER);
		}

		System.out.println("Entered: " + Arrays.toString(interestsArray).replaceAll("\\[","").replaceAll("\\]",""));

		try {
			Thread.sleep(500);
			driver.findElement(By.id("textbtn")).click();
		} catch (Exception er) {
			// System.out.println(er);
		}
		
		//---------------------------------------------------------------------------------------------------------------//
		// 												Main loop														 //
		//---------------------------------------------------------------------------------------------------------------//

		int chatCounter = 1;
		
		while (true) { // Loop forever
			int fixTimer = 1;
			chatCounter++; // Increment chat counter			
			boolean connection = false;
			boolean currentlyChatting = false; 
			
			// Check if chat has connected to the server
			while (connection == false) {
				List<WebElement> listElementLog = driver.findElements(By.className("statuslog"));
				List<String> strings = new ArrayList<String>();
				for(WebElement e : listElementLog){
					try {
						strings.add(e.getText());
						if(!e.getText().contains("You both like")) {
					    	connection = true;
							currentlyChatting = true; 
					    }
					} catch (Exception er) {
						// System.out.println(er);
					}			    
				}
			}
			
			int chatTimer = 0;
			while (connection == true) {	// While chat connected
				chatTimer++;
				if (currentlyChatting == true) {
					// Initial message
					try {
						Thread.sleep(2000);
						currentlyChatting = false;
						driver.findElement(By.className("chatmsg")).sendKeys("Hi there! <3");
						driver.findElement(By.className("sendbtn")).click();
					} catch (Exception e) {
			//			System.out.println(e);
					}

					// Double check initial message sent
					List<WebElement> listElement = driver.findElements(By.className("youmsg"));
					for(int i = 0; i < listElement.size(); i++) {
					 if(!listElement.get(i).getText().contains("Hi there! <3")) {
						 System.out.println("Message didnt actually send just said it did lol");
						}
					}
					
					// Check if the user has responded
					Boolean isPresent = driver.findElements(By.className("strangermsg")).size() > 0;
					int loopCounter = 0;
					
					// Stay in this loop until the user responds
					while (isPresent == false) {
						loopCounter++;
						chatTimer++;
						isPresent = driver.findElements(By.className("strangermsg")).size() > 0;
						
						// Check if chat has disconnected
						List<WebElement> listElementLog = driver.findElements(By.className("statuslog"));
						List<String> strings = new ArrayList<String>();
						for(WebElement e : listElementLog){
							try {
								strings.add(e.getText());
								if(e.getText().contains("You have disconnected.") || e.getText().contains("Stranger has disconnected.") || e.getText().contains("Looking for someone you can chat with...")) {
									connection = false;
									currentlyChatting = false;
									driver.findElement(By.className("disconnectbtn")).click();	// Reconnect
									isPresent = true;
							    }
							} catch (Exception er) {
								// System.out.println(er);
							}			    
						}

						// Break out of loop if the user is taking too long to respond
						if (loopCounter == 450) {
							isPresent = true;
							chatTimer = 1500;
						}
					}

					// Response to the strangers message
					try {
						Thread.sleep(1500);
						isPresent = driver.findElements(By.className("strangermsg")).size() > 0;
						WebElement testElement = driver.findElement(By.className("strangermsg"));
	
						// If statements
						if (Pattern.compile(Pattern.quote("m"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find()) {
							// Did match
							driver.findElement(By.className("sendbtn")).sendKeys("This is a bot!");
							driver.findElement(By.className("sendbtn")).click();
							Thread.sleep(500);
							driver.findElement(By.className("sendbtn")).sendKeys("This is just an experiment :)");
							driver.findElement(By.className("sendbtn")).click();
							Thread.sleep(500);
							
							// Check if chat has disconnected
							List<WebElement> listElementLog = driver.findElements(By.className("statuslog"));
							List<String> strings = new ArrayList<String>();
							for(WebElement e : listElementLog){
								try {
									strings.add(e.getText());
								} catch (Exception er) {
									// System.out.println(er);
								}			    
							}
							
							for(String str: strings) {
								if(str.contains("You have disconnected.") || str.contains("Stranger has disconnected.") || str.contains("Looking for someone you can chat with...") || str.contains("or turn on video")) {
									connection = false;
									currentlyChatting = false;
									driver.findElement(By.className("disconnectbtn")).click();	// Reconnect
							    } else {
							    	driver.findElement(By.className("disconnectbtn")).click();	// Initialise disconnect
									driver.findElement(By.className("disconnectbtn")).click();	// Disconnect
									driver.findElement(By.className("disconnectbtn")).click();
									currentlyChatting = false;
									connection = false;
							    }	
							}
							// End checking if chat has disconnected
						} else {
							// Did not match
							driver.findElement(By.className("sendbtn")).sendKeys("This is a bot!");
							driver.findElement(By.className("sendbtn")).click();
							Thread.sleep(500);
							driver.findElement(By.className("sendbtn")).sendKeys("This is just an experiment :)");
							driver.findElement(By.className("sendbtn")).click();
							Thread.sleep(500);
							
							// Check if chat has disconnected
							List<WebElement> listElementLog = driver.findElements(By.className("statuslog"));
							List<String> strings = new ArrayList<String>();
							for(WebElement e : listElementLog){
								try {
									strings.add(e.getText());
								} catch (Exception er) {
									// System.out.println(er);
								}			    
							}
							
							for(String str: strings) {
								if(str.contains("You have disconnected.") || str.contains("Stranger has disconnected.") || str.contains("Looking for someone you can chat with...") || str.contains("or turn on video")) {
									connection = false;
									currentlyChatting = false;
									driver.findElement(By.className("disconnectbtn")).click();	// Reconnect
							    } else {
							    	driver.findElement(By.className("disconnectbtn")).click();	// Initialise disconnect
									driver.findElement(By.className("disconnectbtn")).click();	// Disconnect
									driver.findElement(By.className("disconnectbtn")).click();
									currentlyChatting = false;
									connection = false;
							    }	
							}
							// End checking if chat has disconnected
						}
						
						if (testElement.getText().equals("Stranger: Hey") || 
							testElement.getText().equals("Stranger: Hi") ||
							Pattern.compile(Pattern.quote("hola"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true || 
							Pattern.compile(Pattern.quote("sup"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true || 
							Pattern.compile(Pattern.quote("whats up"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true) { 
						}
						
						if (Pattern.compile(Pattern.quote("real"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true ||
							Pattern.compile(Pattern.quote("fake"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true ||
							Pattern.compile(Pattern.quote("bot"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true) {
						}
						
						if (Pattern.compile(Pattern.quote("hey :)"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true ||
							Pattern.compile(Pattern.quote("hi!"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true ||
							Pattern.compile(Pattern.quote("heyy"), Pattern.CASE_INSENSITIVE).matcher(testElement.getText()).find() == true ||
							testElement.getText().equals("Stranger: hi") ||
							testElement.getText().equals("Stranger: hello")) {
							
							driver.findElement(By.className("disconnectbtn")).click();	// Initialise disconnect
							driver.findElement(By.className("disconnectbtn")).click();	// Disconnect
						}
					} catch (Exception er) {
						// System.out.println(er);
					}	
				}
				
				if (chatTimer == 1500) {
					driver.findElement(By.className("disconnectbtn")).click();	// Initialise disconnect
				}
				
				// Check if chat has disconnected
				List<WebElement> listElementLog = driver.findElements(By.className("statuslog"));
				List<String> strings = new ArrayList<String>();
				for(WebElement e : listElementLog){
					try {
						strings.add(e.getText());
					} catch (Exception er) {
						// System.out.println(er);
					}			    
				}
				
				for(String str: strings) {
					if(str.contains("You have disconnected.") || str.contains("Stranger has disconnected.") || str.contains("Looking for someone you can chat with...") || str.contains("or turn on video")) {
						connection = false;
						currentlyChatting = false;
						driver.findElement(By.className("disconnectbtn")).click();	// Reconnect
				    }
				}
				// End checking if chat has disconnected
				
				fixTimer++;
				
				if (fixTimer == 400) {
					currentlyChatting = false;
					connection = false;
					driver.findElement(By.className("disconnectbtn")).click();
				}
			} // end of connection = true while loop 
			System.out.println("Chat number " + chatCounter);
		}
		
		//---------------------------------------------------------------------------------------------------------------//
		// 											End of main loop													 //
		//---------------------------------------------------------------------------------------------------------------//
	}
}






