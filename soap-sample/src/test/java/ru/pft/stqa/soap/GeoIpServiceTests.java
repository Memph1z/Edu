package ru.pft.stqa.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("46.242.98.18");
    assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>48</State></GeoIP>");
  }
}
