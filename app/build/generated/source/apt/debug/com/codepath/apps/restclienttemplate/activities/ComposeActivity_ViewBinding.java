// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.restclienttemplate.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codepath.apps.restclienttemplate.R;
import com.wafflecopter.charcounttextview.CharCountTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ComposeActivity_ViewBinding<T extends ComposeActivity> implements Unbinder {
  protected T target;

  @UiThread
  public ComposeActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.newTweet = Utils.findRequiredViewAsType(source, R.id.newTweet, "field 'newTweet'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.submitTweet, "field 'submit'", Button.class);
    target.charCountTextView = Utils.findRequiredViewAsType(source, R.id.tvTextCounter, "field 'charCountTextView'", CharCountTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.newTweet = null;
    target.submit = null;
    target.charCountTextView = null;

    this.target = null;
  }
}
