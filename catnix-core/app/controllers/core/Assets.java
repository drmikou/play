package controllers.core;

import com.google.inject.Inject;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;

/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class Assets {

    @Inject controllers.Assets assets;

    public Action<AnyContent> versioned(String path, controllers.Assets.Asset file) {
        return assets.versioned(path, file);
    }

}
