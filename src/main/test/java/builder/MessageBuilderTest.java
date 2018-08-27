package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.WebResource;
import org.cmfgi.witai.WitAi;
import org.junit.Test;

import java.io.InputStream;

public class MessageBuilderTest {

  @Test
  public void testBuilderBuilder(){
    SpeechBuilder builder = WitAi.getInstance().getSpeechBuilder(SpeechBuilder.CONTENT_TYPE.WAV);
    builder.setFile(getClass().getClassLoader().getResourceAsStream("wit-ai/record.wav"));
    WebResource.Builder b= builder.build();
    String returnValue = b.post(String.class,getClass().getClassLoader().getResourceAsStream("wit-ai/record.wav"));
    String a="b";
  }

}
