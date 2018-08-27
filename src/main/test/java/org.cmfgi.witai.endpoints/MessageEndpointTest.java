package org.cmfgi.witai.endpoints;

import org.cmfgi.witai.WitContext;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MessageEndpointTest {

  @Test
  public void testMessageEndpointGetUrl(){
    Map<String,String> parameters = new HashMap<>();
    parameters.put("q","test query");
    MessageEndpoint messageEndpoint = new MessageEndpoint(parameters);
    String url = WitContext.getEndpointForName(messageEndpoint.getEndpointType().endpointName());
    assert url != null;
    String compiledUrl = messageEndpoint.getCompiledEndpoint();
    assert compiledUrl.equals("https://api.wit.ai/message?v=q=test+query");
  }

}
