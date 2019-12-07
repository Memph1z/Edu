package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getPhone());
    type(By.name("email"), contactData.getEmail());
    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }
  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }
  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void editContact(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToHomePage();
  }

  public void modify(int index, ContactData contact) {
    editContact(index);
    fillContactForm(contact,false);
    submitContactModification();
    returnToHomePage();
  }

  public void delete(List<ContactData> before) {
    selectContact(before.size() - 1);
    deleteSelectedContact();
    returnToHomePage();
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement element : elements) {
      String firstName = element.findElement(By.xpath("./td[2]")).getText();
      String lastName = element.findElement(By.xpath("./td[3]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData(id, lastName, firstName, null, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
