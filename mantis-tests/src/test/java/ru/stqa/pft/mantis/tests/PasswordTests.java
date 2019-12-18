package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangePassword() throws InterruptedException, IOException {
    String newPassword = "password1";
    UserData userData = app.db().getAllusers().stream().filter(user -> user.getAccessLevel() != 90).findFirst().get();
    app.userHelper().signUp(app.getProperty("web.getAdminLogin"), app.getProperty("web.getAdminPassword"));
    app.userHelper().resetPasswordForUser(userData.getUsername());
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, userData.getEmail());
    app.userHelper().changePassword(confirmationLink, newPassword);
    assertTrue(app.newSession().login(userData.getUsername(), newPassword));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
