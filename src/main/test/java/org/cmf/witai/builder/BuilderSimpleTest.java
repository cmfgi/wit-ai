package org.cmf.witai.builder;

import org.cmfgi.witai.WitAi;
import org.cmfgi.witai.builder.AddEntityBuilder;
import org.cmfgi.witai.builder.GetEntityBuilder;
import org.cmfgi.witai.builder.MessageBuilder;
import org.cmfgi.witai.builder.SpeechBuilder;
import org.junit.Test;

public class BuilderSimpleTest {

  @Test
  public void testSpeechBuilder() {
    SpeechBuilder builder = WitAi.getInstance().getSpeechBuilder(SpeechBuilder.CONTENT_TYPE.WAV);
    builder.setFile(getClass().getClassLoader().getResourceAsStream("wit-ai/record.wav"));
    String returnValue = builder.build().execute(String.class, getClass().getClassLoader().getResourceAsStream("wit-ai/record.wav"));
    assert returnValue != null;
  }

  @Test
  public void testMessageBuilder() {
    MessageBuilder messageBuilder = WitAi.getInstance().getMessageBuilder("whatever");
    String returnValue = messageBuilder.build().execute(String.class, null);
    assert returnValue != null;
  }

  /*@Test
  public void testAddEntityBuilder() {
    AddEntityBuilder builder = new AddEntityBuilder("test");
    String pojo = builder.build().executeWithPojo();
    assert pojo != null;
  }*/

  @Test
  public void testGetEntityBuilder() {
    GetEntityBuilder builder = new GetEntityBuilder("person_weight");
    String response = builder.build().execute(String.class, null);
    String a = "b";
  }

}
