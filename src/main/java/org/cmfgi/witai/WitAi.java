package org.cmfgi.witai;

import org.cmfgi.witai.builder.AddEntityBuilder;
import org.cmfgi.witai.builder.GetEntityBuilder;
import org.cmfgi.witai.builder.MessageBuilder;
import org.cmfgi.witai.builder.SpeechBuilder;

/**
 * Main entry class with the possibility to retrieve all
 * implemented wit.ai endpoints as a builder.
 */
public final class WitAi {

  private WitAi() {
    //nothing
  }

  public static WitAi getInstance() {
    return new WitAi();
  }

  /**
   * Returns a {@link MessageBuilder}, used to analyse the given message
   * @param query The message to analyse.
   * @return MessageBuilder the messageBuilder instance.
   */
  public MessageBuilder getMessageBuilder(String query) {
    return new MessageBuilder(query);
  }

  /**
   * Returns a {@link SpeechBuilder}, used to analyse the a given sound file.
   * @param type The type of the recorded audio
   * @return Speechbuilder the SpeechBuilder instance.
   */
  public SpeechBuilder getSpeechBuilder(SpeechBuilder.CONTENT_TYPE type) {
    return new SpeechBuilder(type);
  }

  /**
   * Adds a new entity to the list of entities which may be used to tag text.
   * @param id Name of the new entity
   * @return AddEntryBuilder, the {@link AddEntityBuilder} instance
   */
  public AddEntityBuilder getAddEntityBuilder(String id) {
    return new AddEntityBuilder(id);
  }

  /**
   * Returns all the expressions validated for an entity.
   * We currently limit to the first 1000 values (with the first 50 expressions)
   * @param id Name of the new entity
   * @return GetEntityBuilder, the {@link AddEntityBuilder} instance
   */
  public GetEntityBuilder getGetEntityBuilder(String id){
    return new GetEntityBuilder(id);
  }
}
