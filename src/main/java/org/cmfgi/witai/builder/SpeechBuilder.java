package org.cmfgi.witai.builder;

import com.sun.jersey.api.client.RequestBuilder;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class SpeechBuilder extends AbstractBuilder<SpeechBuilder,String> {

  public enum CONTENT_TYPE {
    WAV("audio/wav"),
    MPEG("audio/mpeg3"),
    ULAW("audio/ulaw"),
    RAW("audio/raw");

    String contentType;

    CONTENT_TYPE(String type) {
      this.contentType = type;
    }
  }

  public enum ENCODING {
    INT("signed-integer"),
    UINT("unsigned-integer"),
    FP("floating-point"),
    MU("mu-law"),
    ALOW("a-law"),
    IMA("ima-adpcm"),
    MS("ms-adpcm"),
    GSM("gsm-full-rate");

    String encodingType;

    ENCODING(String type) {
      this.encodingType = type;
    }
  }

  private static final String SPEECH_CONFIG_NAME = "speech";
  private static final String ENC_PARAM = "encoding";
  private static final String CONTENT_TYPE_PARAM = "content-type";
  private static final String BITS_PARAM = "bits";
  private static final String RATE_PARAM = "rate";
  private static final String ENDIAN_PARAM = "endian";

  private InputStream stream;

  public SpeechBuilder(CONTENT_TYPE content_type) {
    super(SPEECH_CONFIG_NAME, REQUEST_TYPE.POST);
    addAdditionalHeader(CONTENT_TYPE_PARAM, content_type.contentType);
  }

  public SpeechBuilder setEncoding(ENCODING enc) {
    addSimpleParameter(ENC_PARAM, enc.encodingType);
    return this;
  }

  public SpeechBuilder setBits(int b) {
    assert b == 8 || b == 16 || b == 32;
    addSimpleParameter(BITS_PARAM, String.valueOf(b));
    return this;
  }

  public SpeechBuilder setRate(int r) {
    assert r >= 1000;
    addSimpleParameter(RATE_PARAM, String.valueOf(r));
    return this;
  }

  public SpeechBuilder setEndian(boolean isLittleEndian) {
    addSimpleParameter(ENDIAN_PARAM, isLittleEndian ? "little" : "big");
    return this;
  }

  public SpeechBuilder setFile(InputStream stream){
    this.stream = stream;
    return this;
  }

  public SpeechBuilder build() {
    builder = super.preBuild();
    return this;
  }

}
