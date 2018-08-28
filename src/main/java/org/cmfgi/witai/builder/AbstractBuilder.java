package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for which implements convenient methods for creating a new Builder.
 */
abstract class AbstractBuilder<T, K> {

  static final String AUTHORIZATION_HEADER = "Authorization";
  static final String JSON_CONTENT_TYPE = "application/json";
  static final String CONTENT_TYPE_HEADER = "Content-type";

  /**
   * Type of the request to perform. Currently only GET and POST are used
   */
  enum REQUEST_TYPE {
    GET, POST,PUT,DELETE;
  }

  private static Client client;
  private MultivaluedMap<String, String> simpleParameters = new MultivaluedMapImpl();
  private Map<String, String> additionalHeader = new HashMap<>();
  private List<String> additionalPathElements = new LinkedList<>();

  private WebResource.Builder builder;

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
  /**
   * Performs a simple lookup for the base url to call identified by resourceName
   * @return The WebResource (jersey)
   */
  WebResource getResource() {
    return client.resource(WitContext.getEndpointForName(resourceName));
  }

  /**
   * Adds a query parameter to the to be executed request.
   * @param key String representing the key
   * @param value String representing the value
   */
  void addSimpleParameter(String key, String value) {
    assert key != null;
    assert value != null;
    simpleParameters.add(key, value);
  }

  /**
   * Abstract method which has to be implemented by the concrete Builder.
   * It simply gives the Builder the possibility to implement the building mechanism itself.
   * @return T Type which should be returned, normally a {@link WebResource.Builder} is expected
   */
  public abstract T build();


  /**
   * Execute the defined call which should be fully configured and build {@link #build()}.
   * @param returnType Class representing the expected return type
   * @param entity Entity to add as the body or data. Varies for the Builder implementations
   * @return K Type defined for the parameter returnType.
   */
  public K execute(Class<K> returnType, Object entity) {
    if (type == REQUEST_TYPE.GET) {
      return this.builder.get(returnType);
    } else if (type == REQUEST_TYPE.POST) {
      return executePostInternal(returnType, entity);
    }
    return null;
  }

  /**
   * Evaluate if a entity is null.
   */
  private K executePostInternal(Class<K> returnType, Object entity) {
    if (entity == null) {
      return this.builder.post(returnType);
    }
    return this.builder.post(returnType, entity);
  }

  /**
   * Helper method to preBuild the request. Additional Header, QueryParams and Path vars are added plus the version.
   * @return the generated {@link WebResource.Builder}
   */
  WebResource.Builder preBuild() {
    WebResource resource = getResource();
    simpleParameters.add("v", WitContext.getConfiguredVersion());

    for (String path : additionalPathElements) {
      resource = resource.path(path);
    }

    builder = resource.queryParams(simpleParameters).header(AUTHORIZATION_HEADER, "Bearer " + WitContext.getToken(resourceName));
    for (String k : additionalHeader.keySet()) {
      builder = builder.header(k, additionalHeader.get(k));
    }
    return builder;
  }

  void addAdditionalHeader(String k, String v) {
    additionalHeader.put(k, v);
  }

  void addAdditionalPath(String path){
    additionalPathElements.add(path);
  }

}
