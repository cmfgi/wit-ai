package org.cmfgi.witai.builder;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;
import org.cmfgi.witai.exception.EndpointNotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

/**
 * Class which is capable of accessing the properties provided in resources/wit-ai/wit-ai.properties.
 * WitContext is also providing convenient methods for access.
 */
final class WitContext {
  private static final String COMMA_REGEX = ",";
  private static Logger LOGGER = LoggerFactory.getLogger(WitContext.class);
  private static final String CONFIGURATION_RESOURCE_LOCATION = "wit-ai/wit-ai.properties";
  private static final String VERSION = "version";
  private static final String ACCESS_TOKEN = "accessToken";
  private static final String SERVER_ACCESS_TOKEN = "serverAccessToken";
  private static final String NEEDS_SERVER_ACCESS = "needsServerToken";

  private static Object MONITOR = new Object();
  private Properties properties = new Properties();
  private static WitContext context;

  private static WitContext _instance() {
    synchronized (MONITOR) {
      if (context == null) {
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

  public static String getConfiguredVersion() {
    String version = _instance().properties.getProperty(VERSION);
    if (StringUtils.isBlank(version)) {
      throw new UnsupportedOperationException("version is null");
    }
    return version;
  }

  public static String getToken(String builderName) {
    String token = null;
    if (needsServerAuthToken(builderName)) {
      token = _instance().properties.getProperty(SERVER_ACCESS_TOKEN);
    } else {
      token = _instance().properties.getProperty(ACCESS_TOKEN);
    }

    assert token != null;
    return token;
  }

  static boolean needsServerAuthToken(String builderName) {
    String list = _instance().properties.getProperty(NEEDS_SERVER_ACCESS);
    String[] chunks = list.split(COMMA_REGEX);
    if (chunks == null) {
      return false;
    }

    Optional opt = Arrays.stream(chunks)
            .filter(str -> StringUtils.isNotBlank(str))
            .filter(str -> builderName.equals(str)).findAny();
    return opt.isPresent();
  }

  /**
   * Returns the endpoint configuration by name.
   *
   * @param name, Name of the endpoint.
   * @return The unparsed url of the endpoint.
   * @throws EndpointNotImplementedException in case no valid config was found.
   */
  public static String getEndpointForName(String name) {
    String endpoint = _instance().properties.getProperty(name);
    if (StringUtils.isBlank(name)) {
      throw new EndpointNotImplementedException(name);
    }
    return endpoint;
  }
}
