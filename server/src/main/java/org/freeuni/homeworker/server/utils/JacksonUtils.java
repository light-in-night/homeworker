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

    public static void addStatus(ObjectNode objectNode, String status) {
        objectNode.put("STATUS", status);
    }

    public static void addStatusNotFound(ObjectNode objectNode, String toString) {
        objectNode.put("STATUS", "NOT_FOUND");
        objectNode.put("ERROR_MESSAGE", toString);
    }

    public static void addStatusIllegalRequest(ObjectNode objectNode, String toString) {
        objectNode.put("STATUS", "ILLEGAL_REQUEST");
        objectNode.put("ERROR_MESSAGE", toString);
    }
}
