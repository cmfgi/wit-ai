package org.cmfgi.witai;

import org.cmfgi.witai.builder.MessageBuilder;
import org.cmfgi.witai.builder.SpeechBuilder;

public final class WitAi {

  private WitAi(){
    //nothing
  }

  public static WitAi getInstance(){
    return new WitAi();
  }

  public MessageBuilder getMessageBuilder(String query){
    return new MessageBuilder(query);
  }

  public SpeechBuilder getSpeechBuilder(SpeechBuilder.CONTENT_TYPE type){
    return new SpeechBuilder(type);
  }

}
