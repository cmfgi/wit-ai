package org.cmfgi.witai.endpoints;

import org.cmfgi.witai.constants.WitRequestEndpoints;
import org.cmfgi.witai.exception.EndpointNotImplementedException;

import java.util.Map;

/**
 * Interface which is describing how an implemented endpoint should behave.
 */
public interface Endpoint {

  WitRequestEndpoints getEndpointType();

  default String getCompiledEndpoint() {
    throw new EndpointNotImplementedException(getEndpointType().name());
  }

  void setRequiredParameters(Map<String, String> parameters);
}
