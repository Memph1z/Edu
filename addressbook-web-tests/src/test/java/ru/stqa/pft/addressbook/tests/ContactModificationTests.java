package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      Groups groups = app.db().groups();
      if (groups.size() == 0) {
        app.group().create(new GroupData().withName("test 2").withFooter("footer 1"));
      }
      app.contact().create(new ContactData()
              .withFirstname("Господин").withLastname("Никакущий").withAddress("улица Провинциалов")
              .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
              .withEmail("nikak@test.org").inGroup(groups.iterator().next()), true);
    }
  }

  @Test
  public void testsContactModification() {
    Contacts before = app.db().contacts();
    app.goTo().returnToHomePage();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Никакущинский").withLastname("Господин").withAddress("улица Провинциалок").withHomePhone("+79999999999").withEmail("nikak@test.org");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }
}
