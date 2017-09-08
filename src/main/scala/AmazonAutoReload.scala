import org.openqa.selenium.{By, Keys, WebDriver, WebElement}
import org.openqa.selenium.chrome.ChromeDriver

import scala.concurrent.Await

object AmazonAutoReload {

  def main(args: Array[String]): Unit = {

    val driver: WebDriver = new ChromeDriver()

    val n = 1 //Number of reloads
    val amountPerReload: String = "0.5" //In USD
    val email = "email" //Amazon email/phone number
    val password = "password" //Amazon password.


    val url = "https://www.amazon.com/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&switch_account="
    val email_field = "email"
    val password_field = "password"
    val submitBtn_field = "signInSubmit"

    login(driver, url, email_field, password_field, submitBtn_field, email, password)
    reload(driver, n, amountPerReload)

  }

  def login(driver: WebDriver, url: String, email_field: String, password_field: String, submitBtn_field: String, email: String, password: String): Int = {

    driver.get(url)

    val username_field_ele: WebElement = driver.findElement(By.name(email_field))
    username_field_ele.sendKeys(email)

    val password_field_ele = driver.findElement(By.name(password_field))
    password_field_ele.sendKeys(password)


    val submitBtn = driver.findElement(By.id(submitBtn_field))
    submitBtn.submit()
    return 0

  }

  def reload(driver: WebDriver, n: Int, amountPerReload: String): Int = {

    for (i <- 1 to n) {
      reloadOnce(driver, amountPerReload)
      Thread.sleep(10000)
    }

    return 0
  }

  def reloadOnce(driver: WebDriver, amountPerReload: String): Int = {

    driver.get("https://www.amazon.com/asv/reload/order?ref=asv_re_th_ftr_d")

    val manualReloadAmt = driver.findElement(By.id("asv-manual-reload-amount"))
    manualReloadAmt.sendKeys(amountPerReload)
    Thread.sleep(2000)
    manualReloadAmt.sendKeys((Keys.TAB))
    Thread.sleep(2000)

    val confirmPayment = driver.findElements(By.className("a-alert-content"))
    if (confirmPayment.size() > 0)
      confirmPaymentDetails(driver, "xyz")


    Thread.sleep(2000)

    driver.findElement(By.id("form-submit-button")).submit()

    return 0
  }

  def confirmPaymentDetails(driver: WebDriver, card: String): Int = {
    driver.findElement(By.id("asv-payment-edit-link")).click()

    driver.findElement(By.id("pmts-id-2")).click()

    driver.findElement(By.id("pmts-id-31")).sendKeys(card)
    Thread.sleep(2000)

    driver.findElement(By.id("pmts-id-33-announce")).submit()
    Thread.sleep(2000)
    driver.findElement(By.id("asv-form-submit")).submit()

  }


}
