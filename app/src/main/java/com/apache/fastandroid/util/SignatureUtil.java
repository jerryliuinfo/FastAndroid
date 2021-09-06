package com.apache.fastandroid.util;

import android.net.Uri;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.Nullable;
import java8.util.stream.Collector;
import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

/**
 * Created by Jerry on 2021/8/25.
 */
public class SignatureUtil {
    private static final String HMAC_SHA_256 = "HmacSHA256";

    /**
     * Generate signature from string
     * @param source encoded string
     * @param key secret key
     * @return signature
     * @throws NoSuchAlgorithmException exception
     * @throws InvalidKeyException exception
     */
    public static String signature(String source, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA_256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256);
        mac.init(secretKeySpec);

        String base64String = Base64.encodeToString(mac.doFinal(source.getBytes()), Base64.NO_WRAP);
        return base64String.replace("+", "%2B");
    }

    public static String getSignedMediaUrl(@Nullable String mediaUrl, @Nullable String hmacSecret) {
        if (mediaUrl != null && mediaUrl.length() > 0 && hmacSecret != null && hmacSecret.length() > 0) {
            Uri uri = Uri.parse(mediaUrl);

            Map<String, String> sortedMap = getSortedParameters(uri, true);
            Uri.Builder builder = uri.buildUpon().clearQuery();

            fromMap(sortedMap).forEachOrdered(param ->
                    builder.appendQueryParameter(param.getKey(), param.getValue()));

            mediaUrl = Optional.ofNullable(stringFromParameters(fromMap(sortedMap), false))
                    .map(s -> getSignature(s, hmacSecret))
                    .map(sign -> builder.appendQueryParameter("sign", sign))
                    .orElse(builder)
                    .build().toString();
        }

        return mediaUrl;
    }

    /**
     * Creating String from parameters
     * @param parameters stream sorted map entries
     * @param includeDelimiters if need include delimiters to string
     * @return string value
     */
    public static String stringFromParameters(Stream<Map.Entry<String, String>> parameters,
                                              boolean includeDelimiters) {
        Collector<CharSequence, ?, String> collector = includeDelimiters
                ? Collectors.joining("&") : Collectors.joining();

        return parameters
                .map(parameter -> stringFromParameter(parameter, includeDelimiters))
                .collect(collector);
    }

    private static String stringFromParameter(Map.Entry<String, String> entry, boolean includeDelimiter) {
        return entry.getKey() + (includeDelimiter ? "=" : "") + Uri.encode(entry.getValue());
    }

    /**
     * Create stream from map
     * @param map map
     * @return stream
     */
    private static Stream<Map.Entry<String, String>> fromMap(@Nullable Map<String, String> map) {
        return map != null ? StreamSupport.stream(map.entrySet()) : RefStreams.empty();
    }

    @Nullable
    private static String getSignature(String source, String hmacSecret) {
        String signature = null;
        try {
            signature = signature(source, hmacSecret);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return signature;
    }

    private static Map<String, String> getSortedParameters(Uri uri,
                                                           boolean includeTimestamp) {
        Map<String, String> parameters = getQueryParameters(uri);
        if (includeTimestamp) {
//            parameters.put("timestamp", TimeUtils.getStringTimestampInSeconds());
//            parameters.put("timezone", TimeUtils.getTimeZone());
        }

        return StreamSupport.stream(parameters.entrySet())
                .sorted((c1, c2) -> c1.getKey().compareTo(c2.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        TreeMap::new));


    }


    private static Map<String, String> getQueryParameters(Uri uri) {

        return StreamSupport
                .stream(uri.getQueryParameterNames())
                .map(key -> new AbstractMap.SimpleEntry<>(key, uri.getQueryParameter(key)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        HashMap::new));
    }

    public static void main(String[] args) {
        String target = "https://api.toongoggles.com/loggingmediaurlpassthrough/a.m3u8?avod=0&deliveryProfileId=&connection=wifi&language=en&device_id=&device_height=720&device_manufacturer=&device_type=tablet&device_width=1280&event_type=video_star";
        String ourKey = "ETKbqkcBFt7dehJmuQ3mjkUccAUw4";
        System.out.println(getSignedMediaUrl(target, ourKey));
    }
}
