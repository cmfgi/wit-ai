package org.cmfgi.witai.exception;

public class EndpointNotImplementedException extends RuntimeException{
  public static final String ERROR_MESSAGE="Endpoint %s is not implemented";

  public EndpointNotImplementedException(String endpointName){
    super(String.format(ERROR_MESSAGE,endpointName));
  }
}
