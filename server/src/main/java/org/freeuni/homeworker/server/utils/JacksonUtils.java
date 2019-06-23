package org.freeuni.homeworker.server.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class JacksonUtils {

    public static void addStatusError(ObjectNode objectNode, String s) {
        objectNode.put("STATUS", "ERROR");
        objectNode.put("ERROR_MESSAGE", s);
    }

    public static void addStatusOk(ObjectNode objectNode) {
        objectNode.put("STATUS", "OK");
    }

}
