package io.centipod.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Utility class for serialization of objects
 *
 * @author Christian Schuit, Centipod B.V., copyright 2016-2021
 */
public class SerializationUtil {

    /**
     * Constructor with limited visibility
     */
    private SerializationUtil() {}

    /**
     * Base64 encoder
     */
    private static final Encoder ENCODER = Base64.getEncoder();

    /**
     * Base64 decoder
     */
    private static final Decoder DECODER = Base64.getDecoder();

    /**
     * Serializes an object
     * @param session
     * @return
     * @throws WebException
     */
    public static <T> String serialize(T object) throws IllegalArgumentException {

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            GZIPOutputStream gout = new GZIPOutputStream(bytes);
            ObjectOutputStream out = new ObjectOutputStream(gout);
            out.writeObject(object);
            out.flush();
            out.close();

            // Encode in base64
            return ENCODER.encodeToString(bytes.toByteArray());
        } catch (Throwable t) {

            throw new IllegalArgumentException("Failed to serialize object.", t);
        }
    }

    /**
     * Deserializes a object
     * @param serializedSession
     * @throws WebException
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String serializedObject) throws IllegalArgumentException {

        try {

            // Decode from base64
            byte[] decoded = DECODER.decode(serializedObject);

            // Deserialize object
            ByteArrayInputStream bytes = new ByteArrayInputStream(decoded);
            GZIPInputStream gin = new GZIPInputStream(bytes);
            try (ObjectInputStream in = new ObjectInputStream(gin)) {

                return (T) in.readObject();
            }

        } catch (Throwable t) {

            throw new IllegalArgumentException("Failed to deserialize an object.", t);
        }
    }
}
