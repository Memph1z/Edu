package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactInGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test 2").withFooter("footer 1"));
    }
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Господин").withLastname("Никакущий").withAddress("улица Провинциалов")
              .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
              .withEmail("nikak@test.org"), true);
    }
  }

  @Test
  public void testAddContact() {
    ContactData foundContact = null;
    GroupData foundGroup = null;
    for (ContactData contact : app.db().contacts()) {
      Groups contactGroups = contact.getGroups();
      for (GroupData group : app.db().groups()) {
        if (!contactGroups.contains(group)) {
          foundContact = contact;
          foundGroup = group;
          break;
        }
      }
      if (foundContact != null) {
        break;
      }
    }
    if (foundContact == null) {
      app.contact().create(new ContactData()
              .withFirstname("Господин").withLastname("Никакущий").withAddress("улица Провинциалов")
              .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
              .withEmail("nikak@test.org"), true);
      ContactData contact = app.db().selectContactWithMaxId();
      app.contact().selectContactById(contact.getId());
      GroupData firstGroup = app.db().groups().iterator().next();
      app.contact().selectGroup(firstGroup);
      app.contact().addInGroup();
      contact = app.db().getContactById(contact.getId());
      Assert.assertTrue(contact.getGroups().contains(firstGroup));
    } else {
      app.contact().selectContactById(foundContact.getId());
      app.contact().selectGroup(foundGroup);
      app.contact().addInGroup();

      foundContact = app.db().getContactById(foundContact.getId());
      Assert.assertTrue(foundContact.getGroups().contains(foundGroup));
    }
  }

  @Test
  public void testDeleteContact() {
    ContactData contact = app.db().contacts().stream().filter(contactList -> contactList.getGroups().size() > 0).findFirst().orElse(null);
    if (contact == null) {
      ContactData firstContact = app.db().contacts().iterator().next();
      GroupData firstGroup = app.db().groups().iterator().next();
      app.contact().selectContactById(firstContact.getId());
      app.contact().selectGroup(firstGroup);
      app.contact().addInGroup();
      app.contact().returnToHomePage();
      firstContact = app.db().getContactById(firstContact.getId());
      Assert.assertTrue(firstContact.getGroups().contains(firstGroup));
      app.contact().selectContactFromGroup(firstContact, firstGroup);
      app.contact().deleteFromGroup();
      firstContact = app.db().getContactById(firstContact.getId());
      Assert.assertFalse(firstContact.getGroups().contains(firstGroup));
    } else {
      GroupData firstGroup = contact.getGroups().iterator().next();
      app.contact().selectContactFromGroup(contact, firstGroup);
      app.contact().deleteFromGroup();
      contact = app.db().getContactById(contact.getId());
      Assert.assertFalse(contact.getGroups().contains(firstGroup));
    }
  }
}