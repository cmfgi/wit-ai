package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractBuilder {

  private static final String AUTHORIZATION_HEADER = "Authorization";

  enum REQUEST_TYPE {
    GET, POST;
  }

  private static Client client;
  private MultivaluedMap<String, String> simpleParameters = new MultivaluedMapImpl();
  private Map<String, String> additionalHeader = new HashMap<>();

  static {
    ClientConfig cc = new DefaultClientConfig();
    client = Client.create(cc);
  }

  private REQUEST_TYPE type;
  private String resourceName;

  AbstractBuilder(String name, REQUEST_TYPE type) {
    this.resourceName = name;
    this.type = type;
  }

  private WebResource getResource() {
    return client.resource(WitContext.getEndpointForName(resourceName));
  }

  void addSimpleParameter(String key, String value) {
    assert key != null;
    assert value != null;
    simpleParameters.add(key, value);
  }

  protected WebResource.Builder build() {
    WebResource resource = getResource();
    simpleParameters.add("v", WitContext.getConfiguredVersion());
    WebResource.Builder builder = resource.queryParams(simpleParameters).header(AUTHORIZATION_HEADER, "Bearer " + WitContext.getToken());
    for (String k : additionalHeader.keySet()) {
      builder = builder.header(k, additionalHeader.get(k));
    }
    return builder;
  }

  protected void addAdditionalHeader(String k, String v) {
    additionalHeader.put(k, v);
  }

}
