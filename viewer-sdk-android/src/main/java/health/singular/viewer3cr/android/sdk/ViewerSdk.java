package health.singular.viewer3cr.android.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public abstract class ViewerSdk extends UnityPlayerActivity {

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
}