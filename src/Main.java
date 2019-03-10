import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.awt.*;
import java.net.URL;
import java.io.File;


public class Main {
	
	public static void main(String[] args) throws AWTException, InterruptedException, Exception {

		int chatCounter = 0;
		String chatCounterString = "";
		
		//---------------------------------------------------------------------------------------------------------------//
		// 												Copy ChromeDriver												 //
		//---------------------------------------------------------------------------------------------------------------//

		System.out.println("Creating ChromeDriver");
		Class<?> cls = Class.forName("Main");		
		ClassLoader classLoader = cls.getClassLoader();
        URL resource = classLoader.getResource("chromedriver.exe");
        File f = new File("Driver");
        if (!f.exists()) {
            f.mkdirs();
        }
        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
        if (!chromeDriver.exists()) {
            chromeDriver.createNewFile();
            org.apache.commons.io.FileUtils.copyURLToFile(resource, chromeDriver);
        }

		//---------------------------------------------------------------------------------------------------------------//
		// 												Initialise bot 													 //
		//---------------------------------------------------------------------------------------------------------------//
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		WebDriver driver = null;
		
		try {
		    driver = new ChromeDriver();
		    driver.get("https://omegle.com");
		
			//---------------------------------------------------------------------------------------------------------------//
			//							 Set up interests to match with and connect to strangers							 //
			//---------------------------------------------------------------------------------------------------------------//		
	
			String interestsTextfield = GUI.userInterests.getText();
			String advertisementMessage = GUI.botMessage.getText();		
			String[] interestsArray = {interestsTextfield};
		
			System.out.println("Entering interests");
			driver.findElement(By.className("topicplaceholder")).click();	// Enter interests text box
			WebElement textbox = driver.findElement(By.className("newtopicinput"));	// Start a new entry
			
			for (int i = 0; i < interestsArray.length; i++) {
				driver.findElement(By.className("newtopicinput")).sendKeys(interestsArray[i]);	
				textbox.sendKeys(Keys.ENTER);
			}
	
			System.out.println("Entered: " + Arrays.toString(interestsArray).replaceAll("\\[","").replaceAll("\\]",""));
	
			try {
				Thread.sleep(200);
				driver.findElement(By.id("textbtn")).click();
			} catch (Exception er) {
				 System.out.println(er);
			}
			
			//---------------------------------------------------------------------------------------------------------------//
			// 												Main loop														 //
			//---------------------------------------------------------------------------------------------------------------//
	
			while (true) { // Loop forever			
				List<WebElement> listElementLog = driver.findElements(By.className("statuslog"));
				List<String> strings = new ArrayList<String>();
				for(WebElement e : listElementLog){
					try {
						strings.add(e.getText());
						if (e.getText().contains("Stranger has disconnected.") || e.getText().contains("You have disconnected.")) {	// If chat disconnected
							driver.findElement(By.className("disconnectbtn")).click();					
						} else {						
							if (e.getText().contains("You're now chatting with a random stranger. Say hi!")) {	// If connected to a new chat
								chatCounter++; // Increment chat counter
								Thread.sleep(500);
								driver.findElement(By.className("sendbtn")).sendKeys(advertisementMessage);
								driver.findElement(By.className("sendbtn")).click();
								Thread.sleep(500);
								if (driver.getPageSource().contains("You:")) {	// If your message sent successfully
									driver.findElement(By.className("disconnectbtn")).click();
									driver.findElement(By.className("disconnectbtn")).click();
								} else {
									System.out.println("Text is absent");
								}
							}						
						}
					} catch (Exception er) {
						 System.out.println(er);
					}			    
				}
			}
			
			//---------------------------------------------------------------------------------------------------------------//
			// 											End of main loop													 //
			//---------------------------------------------------------------------------------------------------------------//
		
		} catch (WebDriverException e) { //thrown after can't reach browser (browser closed)
		    //handle exception
		} finally {
		    if (driver != null)
		        driver.quit();
		    chatCounterString = String.valueOf(chatCounter);
			GUI.chatCounterLabel.setText("Total conversations: " + chatCounterString);
		}
	}
}

