package org.cmfgi.witai.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cmfgi.witai.builder.pojo.EntityPojo;

public class AddEntityBuilder extends AbstractBuilder<AddEntityBuilder, String> {

  private static final String ENTITY_CONFIG_NAME = "entity";
  private static final Gson gson = new GsonBuilder().create();

  private EntityPojo pojo = new EntityPojo();

  public AddEntityBuilder(String id) {
    super(ENTITY_CONFIG_NAME, REQUEST_TYPE.POST);
    assert id != null;
    pojo.setId(id);
    addAdditionalHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE);
  }

  public AddEntityBuilder setDoc(String doc) {
    assert doc != null;
    pojo.setDoc(doc);
    return this;
  }

  @Override
  public AddEntityBuilder build() {
    super.preBuild();
    return this;
  }

  public String executeWithPojo(){
    return super.execute(String.class,gson.toJson(pojo));
  }

}
