package org.cmfgi.witai.endpoints;

import org.cmfgi.witai.WitContext;
import org.cmfgi.witai.constants.WitRequestEndpoints;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MessageEndpoint extends AbstractEndpoint {

  public MessageEndpoint(Map<String, String> parameters) {
    this.setRequiredParameters(parameters);
  }

  @Override
  public WitRequestEndpoints getEndpointType() {
    return WitRequestEndpoints.MESSAGE;
  }

  @Override
  public String getCompiledEndpoint() {
    try {
      return appendAllKnownParameters(WitContext.getEndpointForName(getEndpointType().endpointName()));
    } catch (UnsupportedEncodingException usex) {
      throw new RuntimeException("could not encode query");
    }
  }
}
