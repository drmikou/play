package utils.core.formHelper;

import play.api.data.Field
import play.twirl.api.Html
    
/**
 * Zip a fieldList with its' index  
 */
/*
 * This helper is meaned to be called from .scala.html views
 */
object repeatWithIndex {

    def apply(field: play.api.data.Field, min: Int = 1)(f: (Field,Int) => Html) = {
     (0 until math.max(if (field.indexes.isEmpty) 0 else field.indexes.max + 1, min)).map(i => f(field("[" + i + "]"),i))
    }
}
