package health.singular.viewer3cr.android.sdk;

import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public abstract class ViewerSdkActivity extends UnityPlayerActivity {

    // Not used by set
    @SuppressWarnings({"unused", "RedundantModalityModifier"})
    public final void receiveMessageFromUnity(String text) throws Exception {
        var mapper = new ObjectMapper();
        var grant = mapper.readValue(text, FrontEndPayload.class);
        onPayload(grant);
    }

    @SuppressWarnings({"unused", "RedundantModalityModifier"})
    public final void executePayload(FrontEndPayload jsonPayload) throws Exception  {
        var mapper = new ObjectMapper();
        var jsonString = mapper.writeValueAsString(jsonPayload);
        UnityPlayer.UnitySendMessage("SendReceive", "Receive", jsonString);
    }

    @SuppressWarnings({"unused", "MemberVisibilityCanBePrivate"})
    public void onPayload(FrontEndPayload jsonPayload) {
        // Do nothing, this should be overridden
    }


    /**
     * <p>Adds a child view. If no layout parameters are already set on the child, the
     * default parameters for this ViewGroup are set on the child.</p>
     *
     *
     * @param child the child view to add
     *
     */
    public void addView(View child) {
        mUnityPlayer.getFrameLayout().addView(child);
    }

    /**
     * Adds a child view. If no layout parameters are already set on the child, the
     * default parameters for this ViewGroup are set on the child.
     *
     *
     * @param child the child view to add
     * @param index the position at which to add the child
     *
     */
    public void addView(View child, int index) {
        mUnityPlayer.getFrameLayout().addView(child, index);
    }

    /**
     * Adds a child view with this ViewGroup's default layout parameters and the
     * specified width and height.
     *
     *
     * @param child the child view to add
     */
    public void addView(View child, int width, int height) {
        mUnityPlayer.getFrameLayout().addView(child, width, height);
    }

    /**
     * Adds a child view with the specified layout parameters.
     *
     *
     * @param child the child view to add
     * @param params the layout parameters to set on the child
     */
    public void addView(View child, ViewGroup.LayoutParams params) {
        mUnityPlayer.getFrameLayout().addView(child, params);
    }

    /**
     * Adds a child view with the specified layout parameters.
     *
     *
     * @param child the child view to add
     * @param index the position at which to add the child or -1 to add last
     * @param params the layout parameters to set on the child
     */
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        mUnityPlayer.getFrameLayout().addView(child, index, params);
    }
}