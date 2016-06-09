package utils.core.formHelper;

import play.api.data.Field
import play.twirl.api.Html

/**
 * Zip a fieldList with another Object List (probably a model)  
 */
/*
 * This helper is meaned to be called from .scala.html views
 */
object repeatAndZip {
  
    def apply[T](field: play.api.data.Field, itemList: java.util.List[T], min: Int = 1)(f: (Field,T) => Html) = {
     (0 until math.max(if (field.indexes.isEmpty) 0 else field.indexes.max + 1, min)).map(i => f(field("[" + i + "]"), itemList.get(i)))
    }
}
