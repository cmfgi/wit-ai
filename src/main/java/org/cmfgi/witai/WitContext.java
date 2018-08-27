package org.cmfgi.witai;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;
import org.cmfgi.witai.exception.EndpointNotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class WitContext {
  private static Logger LOGGER = LoggerFactory.getLogger(WitContext.class);
  private static final String CONFIGURATION_RESOURCE_LOCATION="wit-ai/wit-ai.properties";
  private static final String VERSION="version";

  private static Object MONITOR = new Object();
  private Properties properties = new Properties();
  private static WitContext context;

  private static WitContext _instance(){
    synchronized (MONITOR){
      if (context == null){
        context = new WitContext();
        URL url = Resources.getResource(CONFIGURATION_RESOURCE_LOCATION);

        final ByteSource byteSource = Resources.asByteSource(url);
        try (InputStream inputStream = byteSource.openBufferedStream()) {
          context.properties.load(inputStream);
        } catch (IOException e) {
          LOGGER.error("openBufferedStream failed!", e);
        }
      }
      return context;
    }
  }

  public static String getConfiguredVersion(){
    String version = _instance().properties.getProperty(VERSION);
    if (StringUtils.isBlank(version)){
      throw new UnsupportedOperationException("version is null");
    }
    return version;
  }

  /**
   * Returns the endpoint configuration by name.
   * @param name, Name of the endpoint.
   * @return The unparsed url of the endpoint.
   * @throws EndpointNotImplementedException in case no valid config was found.
   */
  public static String getEndpointForName(String name){
    String endpoint = _instance().properties.getProperty(name);
    if (StringUtils.isBlank(name)){
      throw new EndpointNotImplementedException(name);
    }
    return endpoint;
  }
}
