import org.openqa.selenium.{WebDriver, WebElement}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.By

object AmazonAutoReload {

  def main(args:Array[String]): Unit ={

    val driver: WebDriver = new ChromeDriver()

    driver.get("http://www.google.com")

    var element: WebElement = driver.findElement(By.name("q"))

    element.sendKeys("Elegance of Functional Programming!")

    element.submit()

    driver.quit()
  }

}
