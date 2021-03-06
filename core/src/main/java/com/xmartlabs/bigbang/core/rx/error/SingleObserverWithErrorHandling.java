package com.xmartlabs.bigbang.core.rx.error;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/**
 * Implementation of {@link SingleObserver} that calls a {@link Consumer} function {@code onError}.
 * To be used as a SingleObserver hook with RxJavaPlugins.
 */
@RequiredArgsConstructor
public final class SingleObserverWithErrorHandling<T> implements SingleObserver<T> {
  private final SingleObserver<T> source;
  private final Consumer<? super Throwable> onErrorCallback;

  @Override
  public void onSubscribe(Disposable d) {
    source.onSubscribe(d);
  }

  @Override
  public void onSuccess(T t) {
    source.onSuccess(t);
  }

  @Override
  public void onError(Throwable e) {
    try {
      onErrorCallback.accept(e);
    } catch (Exception exception) {
      Timber.e(exception);
    }
    source.onError(e);
  }
}
