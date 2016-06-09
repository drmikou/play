package utils.core.templateHelper

import play.twirl.api.Html

/**
 * Define a Hash variable for template 
 */
/*
 * Escpacially usefull when you need to build an uniqueId in Html and you have only a string which might contains spaces
 */
object defineHash {
  
    def apply(valueToHash: String, complementString: String)(f: (String) => Html) = {
      f(org.apache.commons.codec.digest.DigestUtils.md5Hex(valueToHash) + complementString)
    }
}
