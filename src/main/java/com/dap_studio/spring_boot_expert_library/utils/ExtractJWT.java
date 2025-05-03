package com.dap_studio.spring_boot_expert_library.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String extraction) {

        token = token.replace("Bearer ", "");

        /* Split JWT in 3 pieces separated by '.' char
        * The 1st element is going to be a header,
        * The 2nd elements will be a payload,
        * The 3rd element will be the signature.
        * The resulting chunks are Base64 encoded */
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        /* Decode the chunk at position 1 */
        String payload = new String(decoder.decode((chunks[1])));

        /* Split all elements of the payload by commas */
        String[] entries = payload.split(",");
        /* Introduce a map with key-value pairs of the parsed payload */
        Map<String, String> map = new HashMap<>();

        /* Loop through all key-value pairs until we find a value of "sub" */
        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue[0].equals(extraction)) {
                // Extract and purify the value of e-mail by removing surrounding double quotes ("")
                // and a curly brace ('}') at the end of the keyValue[1]
                final int removeChars;
                if (keyValue[1].endsWith("\"}")) {
                    removeChars = 2;
                } else {
                    removeChars = 1;
                }
                keyValue[1] = keyValue[1].substring(1, keyValue[1].length() - removeChars);
            }
            if (keyValue.length > 1) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        /* If the map contains the "sub" key, then return the value which is the e-mail */
        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }
        /* Otherwise, return null */
        return null;
    }
}
