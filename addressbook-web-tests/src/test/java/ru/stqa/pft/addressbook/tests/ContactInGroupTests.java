package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInGroupTests extends TestBase {

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
  public void testAddContactInGroup() {

    Groups groupsBefore = app.db().groups();
    ContactData contact = app.db().contacts().iterator().next();
    GroupData group = app.db().groups().iterator().next();
    app.goTo().returnToHomePage();
    app.contact().addContactToGroup(contact.getId(), group.getId());
    Groups groupsAfter = app.db().groups().withAdded(group);
    assertThat(groupsAfter, equalTo(groupsBefore.withAdded(group)));
  }

  @Test
  public void testRemoveContactFromGroup() {
    Groups groupsBefore = app.db().groups();
    ContactData contact = app.db().contacts().iterator().next();
    GroupData group = app.db().groups().iterator().next();
    if (!contact.getGroups().contains(group)) {
      app.contact().addContactToGroup(contact.getId(), group.getId());
    }
    app.goTo().returnToHomePage();
    app.contact().deleteContactFromGroup(contact.getId(), group.getId());
    Groups groupsAfter = app.db().groups().without(group);
    assertThat(groupsAfter, equalTo(groupsBefore.without(group)));
  }

}
