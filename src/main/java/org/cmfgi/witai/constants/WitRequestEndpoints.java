package org.cmfgi.witai.constants;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.List;

public enum WitRequestEndpoints {

  MESSAGE("message",Arrays.asList("q","context","msg_id","thread_id","n","verbose"),0),
  SPEECH("speech",Arrays.asList("encoding","bits","rate","endian"),1);


  @VisibleForTesting
  private String endpointName;
  List<String> parameters;
  private int method;

  WitRequestEndpoints(String endpoint, List<String> parameters, int methodType){
    this.endpointName = endpoint;
    this.method = methodType;
    this.parameters = parameters;
  }

  public List<String> geKnownParameters(){
    return this.parameters;
  }

   public String endpointName(){
    return this.endpointName;
  }
}
