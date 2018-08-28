package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.WebResource;
import org.cmfgi.witai.WitAi;

public class GetEntityBuilder extends AbstractBuilder<GetEntityBuilder,String>{

  private static final String ENTITY_CONFIG_NAME = "entity";
  private String id;
  public GetEntityBuilder(String id){
    super(ENTITY_CONFIG_NAME,REQUEST_TYPE.GET);
    addAdditionalPath(id);
  }

  public GetEntityBuilder build(){
    super.preBuild();
    return this;
  }

}
