package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;

@SuppressWarnings("deprecation")
public class TestBase {

  boolean isIssueOpen(int issueId) throws IOException {
    String issueStatus = "";
    String json = getExecutor()
            .execute(Request.Get("https://bugify.stqa.ru/api/issues/" + issueId + ".json")).returnContent().asString();
    JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();
    JsonElement issues = parsed.getAsJsonArray("issues").get(0);
    issueStatus = issues.getAsJsonObject().get("state_name").toString();
    System.out.println("Issue status = " + issueStatus);
    return !issueStatus.equals("Resolved");
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}