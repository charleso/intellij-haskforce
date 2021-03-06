package com.haskforce.parsing.jsonParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.haskforce.parsing.srcExtsDatatypes.*;

import java.lang.reflect.Type;

/**
 * Deserializes special constructors.
 */
public class SpecialConTopTypeDeserializer implements JsonDeserializer<SpecialConTopType> {
    @Override
    public SpecialConTopType deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject objType = jsonElement.getAsJsonObject();
        JsonArray stuff;
        if (objType.has("UnitCon")) {
            UnitCon unitCon = new UnitCon();
            unitCon.srcInfoSpan = jsonDeserializationContext.deserialize(objType.get("UnitCon"), SrcInfoSpan.class);
            return unitCon;
        } else if (objType.has("ListCon")) {
            ListCon listCon = new ListCon();
            listCon.srcInfoSpan = jsonDeserializationContext.deserialize(objType.get("ListCon"), SrcInfoSpan.class);
            return listCon;
        } else if (objType.has("FunCon")) {
            FunCon funCon = new FunCon();
            funCon.srcInfoSpan = jsonDeserializationContext.deserialize(objType.get("FunCon"), SrcInfoSpan.class);
            return funCon;
        } else if ((stuff = objType.getAsJsonArray("TupleCon")) != null) {
            TupleCon tupleCon = new TupleCon();
            Gson g = new Gson(); // TODO: Remove with 1.7.
            tupleCon.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            String parsed = jsonDeserializationContext.deserialize(stuff.get(1), String.class);
            tupleCon.boxed = parsed.equals("Boxed") ? new Boxed() : new Unboxed();
            tupleCon.i = g.fromJson(stuff.get(2), int.class);
            return tupleCon;
        } else if (objType.has("Cons")) {
            Cons cons = new Cons();
            cons.srcInfoSpan = jsonDeserializationContext.deserialize(objType.get("Cons"), SrcInfoSpan.class);
            return cons;
        } else if (objType.has("UnboxedSingleCon")) {
            UnboxedSingleCon unboxedSingleCon = new UnboxedSingleCon();
            unboxedSingleCon.srcInfoSpan = jsonDeserializationContext.deserialize(objType.get("UnboxedSingleCon"), SrcInfoSpan.class);
            return unboxedSingleCon;
        }
        throw new JsonParseException("Unexpected JSON object type: " + objType.toString());
    }
}
