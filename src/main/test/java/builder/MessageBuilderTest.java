package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.WebResource;
import org.cmfgi.witai.WitAi;
import org.junit.Test;

import java.io.InputStream;

public class MessageBuilderTest {

  @Test
  public void testSpeechBuilder(){
    SpeechBuilder builder = WitAi.getInstance().getSpeechBuilder(SpeechBuilder.CONTENT_TYPE.WAV);
    builder.setFile(getClass().getClassLoader().getResourceAsStream("wit-ai/record.wav"));
    String returnValue = builder.build().execute(String.class,getClass().getClassLoader().getResourceAsStream("wit-ai/record.wav"));
    assert returnValue!=null;
  }

  @Test
  public void testMessageBuilder(){
    MessageBuilder messageBuilder = WitAi.getInstance().getMessageBuilder("whatever");
    String returnValue = messageBuilder.build().execute(String.class,null);
    assert returnValue!=null;
  }

}
