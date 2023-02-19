package io.github.vino42.base.http;

public interface AsyncCallback {
    void onFailure(String body, long duration);

    void OnComplete(Response response, long duration);
}
