package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractBuilder<T, K> {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  static final String JSON_CONTENT_TYPE = "application/json";
  static final String CONTENT_TYPE_HEADER = "Content-type";


  enum REQUEST_TYPE {
    GET, POST;
  }

  private static Client client;
  private MultivaluedMap<String, String> simpleParameters = new MultivaluedMapImpl();
  private Map<String, String> additionalHeader = new HashMap<>();
  WebResource.Builder builder;

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

  public abstract T build();

  public K execute(Class<K> returnType, Object entity) {
    if (type == REQUEST_TYPE.GET) {
      return this.builder.get(returnType);
    } else if (type == REQUEST_TYPE.POST) {
      return executePostInternal(returnType, entity);
    }
    return null;
  }

  private K executePostInternal(Class<K> returnType, Object entity) {
    if (entity == null) {
      return this.builder.post(returnType);
    }
    return this.builder.post(returnType, entity);
  }


  WebResource.Builder preBuild() {
    WebResource resource = getResource();
    simpleParameters.add("v", WitContext.getConfiguredVersion());
    WebResource.Builder builder = resource.queryParams(simpleParameters).header(AUTHORIZATION_HEADER, "Bearer " + WitContext.getToken());
    for (String k : additionalHeader.keySet()) {
      builder = builder.header(k, additionalHeader.get(k));
    }
    return builder;
  }

  void addAdditionalHeader(String k, String v) {
    additionalHeader.put(k, v);
  }

}
