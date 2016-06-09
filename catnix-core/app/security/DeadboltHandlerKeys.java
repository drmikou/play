package security;

import be.objectify.deadbolt.java.ConfigKeys;

/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public enum DeadboltHandlerKeys {
    DEFAULT(ConfigKeys.DEFAULT_HANDLER_KEY);

    public final String key;

    private DeadboltHandlerKeys(final String key)
    {
        this.key = key;
    }
}
