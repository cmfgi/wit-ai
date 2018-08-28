package org.cmfgi.witai.builder;

public class MessageBuilder extends AbstractBuilder<MessageBuilder, String> {

  private static final String MESSAGE_CONFIG_NAME = "message";
  private static final String CONTEXT_PARAM = "context";
  private static final String MSG_ID_PARAM = "msg_id";
  private static final String THREAD_ID_PARAM = "thread_id";
  private static final String N_PARAM = "n";
  private static final String VERBOSE_PARAM = "verbose";
  private static final String Q_PARAM = "q";

  public MessageBuilder(String query) {
    super(MESSAGE_CONFIG_NAME, REQUEST_TYPE.GET);
    assert query != null;
    addSimpleParameter(Q_PARAM, query);
  }

  public MessageBuilder setContext(String context) {
    assert context != null;
    addSimpleParameter(CONTEXT_PARAM, context);
    return this;
  }

  public MessageBuilder setMsg_id(String msg_id) {
    assert msg_id != null;
    addSimpleParameter(MSG_ID_PARAM, msg_id);
    return this;
  }

  public MessageBuilder setThread_id(String thread_id) {
    assert thread_id != null;
    addSimpleParameter(THREAD_ID_PARAM, thread_id);
    return this;
  }

  public MessageBuilder setN(int n) {
    assert n > 0 && n < 9;
    addSimpleParameter(N_PARAM, String.valueOf(n));
    return this;
  }

  public MessageBuilder setVerbose(boolean verbose) {
    assert verbose;
    addSimpleParameter(VERBOSE_PARAM, verbose ? "true" : "false");
    return this;
  }

  public MessageBuilder build() {
    super.preBuild();
    return this;
  }
}
