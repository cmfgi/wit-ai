package org.cmfgi.witai.endpoints;

import org.cmfgi.witai.WitContext;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractEndpoint implements Endpoint {

  Map<String, String> parameters = null;
  InputStream stream = null;

  @Override
  public void setRequiredParameters(Map<String, String> parameters) {
    this.parameters = parameters != null ? parameters : Collections.emptyMap();
  }

  public void setMessageBody(InputStream stream) {
    this.stream = stream;
  }

  String appendAllKnownParameters(String url) throws UnsupportedEncodingException {
    StringBuffer buffer = new StringBuffer(String.format(url, WitContext.getConfiguredVersion()));
    getEndpointType()
            .geKnownParameters()
            .stream()
            .filter(param -> this.parameters.containsKey(param) && this.parameters.get(param) != null)
            .forEach(param -> buffer.append(param).append("=").append(URLEncoder.encode(this.parameters.get(param))));

    return buffer.toString();
  }
}
